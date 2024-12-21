package Controller;

import Repository.IRepository;
import Utils.Dictionary.IMyDictionary;

import java.util.List;

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

    public ProgramState oneStep(ProgramState state) throws GenericException{
        IExecutionStack stack = state.getExecutionStack();
        if(stack.isEmpty()) {
            throw new GenericException("oneStep: ExecutionStack is empty!");
        }

        IStatement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    public void allSteps() throws GenericException {
        ProgramState program = repository.getProgramState();
        repository.logProgramState();    
        System.out.println(program);

        while(!program.getExecutionStack().isEmpty()) {
            program = oneStep(program);
            
            List<Integer> symbolTableAddresses = garbageCollector.getAddrressesFromSymbolTable(
                program.getSymbolTable().getValues()
            );
            
            IMyDictionary<Integer, IValue> newHeap = garbageCollector.unsafeGarbageCollector(
                symbolTableAddresses,
                program.getHeapTable().getContent()
            );
            
            program.getHeapTable().setContent(newHeap);
          
            System.out.println(program);
            repository.logProgramState();
        }
    }
}
