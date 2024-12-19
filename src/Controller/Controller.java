package Controller;

import Repository.IRepository;
import Utils.Stack.IMyStack;
import Model.ProgramState;
import Model.Exception.GenericException;
import Model.Statement.IStatement;

public class Controller {
    private IRepository repository;
    private Boolean displayFlag;

    public Controller() {
        this.displayFlag = true;
    }

    ProgramState oneStep(ProgramState state) throws GenericException{
        IMyStack<IStatement> stack = state.getExecutionStack();
        if(stack.isEmpty()) {
            throw new GenericException("oneStep: ExecutionStack is empty!");
        }

        IStatement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    void allSteps() {
        ProgramState state = repository.getProgramState();
        
        while(!state.getExecutionStack().isEmpty()) {
            oneStep(state);

            if(displayFlag) {
                System.out.println(state);
            }
        }
    }
}
