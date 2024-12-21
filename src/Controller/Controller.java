package Controller;

import Repository.IRepository;
import Utils.Dictionary.IMyDictionary;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import Model.Exception.GenericException;
import Model.Statement.IStatement;
import Model.Value.IValue;
import Model.State.*;

public class Controller {
    private IRepository repository;
    private GarbageCollector garbageCollector;

    public Controller(IRepository repository) {
        this.repository = repository;
        this.garbageCollector = new GarbageCollector();
    }
    
    public void oneStepForAllPrograms(List<ProgramState> programsList) {
        List<Callable<ProgramState>> callList = programsList.stream()
                                                   .map((ProgramState p) -> (Callable<ProgramState>)(() -> { return p.oneStep(); }))
                                                   .collect(Collectors.toList());

        List<PrgState> newProgramsList = executor.invokeAll(callList).stream().map(future -> { try { return future.get();}
        
        
        catch(GenericException e) ) {

        }).filter(p -> p != null).collect(Collectors.toList())

        programsList.addAll(newProgramsList);
        repository.setProgramList(programsList);
    }

    public void allSteps() throws GenericException {
        ProgramState program = repository.getProgramState();
        repository.logProgramState(program);    
        System.out.println(program);

        while(!program.getExecutionStack().isEmpty()) {
            program.oneStep();
            
            List<Integer> symbolTableAddresses = garbageCollector.getAddrressesFromSymbolTable(
                program.getSymbolTable().getValues()
            );
            
            IMyDictionary<Integer, IValue> newHeap = garbageCollector.unsafeGarbageCollector(
                symbolTableAddresses,
                program.getHeapTable().getContent()
            );
            
            program.getHeapTable().setContent(newHeap);
          
            System.out.println(program);
            repository.logProgramState(program);
        }
    }
}
