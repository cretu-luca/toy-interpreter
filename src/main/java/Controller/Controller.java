package Controller;

import Model.Exception.ControllerException;
import Repository.IRepository;
import Utils.Dictionary.IMyDictionary;
import Utils.Dictionary.MyDictionary;
import Model.Exception.GenericException;
import Model.State.ProgramState;
import Model.Type.IType;
import Model.Value.IValue;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repository;
    private ExecutorService executor;
    private GarbageCollector garbageCollector;

    public Controller(IRepository repository) {
        try {
            ProgramState programState = repository.getProgramsList().get(0);
            
            IMyDictionary<String, IType> typeEnv = new MyDictionary<>();
            programState.getOriginalProgram().typeCheck(typeEnv);
            
            this.repository = repository;
            this.executor = Executors.newFixedThreadPool(2);
            this.garbageCollector = new GarbageCollector();
            
        } catch (ControllerException e) {
            throw new ControllerException("Type checking failed: " + e.getMessage());
        }
    }

    public void oneStepForAllPrograms(List<ProgramState> programsList) throws InterruptedException {
        programsList.forEach(prg -> repository.logProgramState(prg));

        List<Callable<ProgramState>> callList = programsList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(() -> p.oneStep()))
                .collect(Collectors.toList());

        List<ProgramState> newProgramsList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        programsList.addAll(newProgramsList);

        programsList.forEach(prg -> {
            List<Integer> addresses = garbageCollector.getAddrressesFromSymbolTable(
                prg.getSymbolTable().getContent().values()
            );
            IMyDictionary<Integer, IValue> newHeap = garbageCollector.unsafeGarbageCollector(
                addresses,
                prg.getHeapTable().getContent()
            );
            prg.getHeapTable().setContent(newHeap);
        });

        programsList.forEach(prg -> repository.logProgramState(prg));
        repository.setProgramList(programsList);
    }

    public void allSteps() throws InterruptedException {
        executor = Executors.newFixedThreadPool(5);
        List<ProgramState> programsList = removeCompletedPrograms(repository.getProgramsList());
    
        while(programsList.size() > 0) {
            oneStepForAllPrograms(programsList);
            programsList = removeCompletedPrograms(repository.getProgramsList());
        }
        
        executor.shutdownNow();
        repository.setProgramList(programsList);
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgramList) {
        return inProgramList.stream()
                .filter(p -> !p.isComplete())
                .collect(Collectors.toList());
    }

    public IRepository getRepository() {
        return repository;
    }
}