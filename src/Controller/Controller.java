package Controller;

import Repository.IRepository;
import Model.Exception.GenericException;
import Model.State.ProgramState;
import Model.Statement.IStatement;
import Model.State.*;

public class Controller {
    private IRepository repository;
    private Boolean displayFlag;

    public Controller(IRepository repository) {
        this.repository = repository;
        this.displayFlag = true;
    }

    public ProgramState oneStep(ProgramState state) throws GenericException{
        ExecutionStack stack = state.getExecutionStack();
        if(stack.isEmpty()) {
            throw new GenericException("oneStep: ExecutionStack is empty!");
        }

        IStatement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    public void allSteps() {
        try {
            ProgramState state = repository.getProgramState();
            
            while(!state.getExecutionStack().isEmpty()) {
                try {
                    if(displayFlag) {
                        System.out.println(state);
                    }

                    oneStep(state);
                } catch (GenericException e) {
                    System.out.println("Execution error: " + e.getMessage());
                    break;
                }
            }

            if(displayFlag) {
                System.out.println(state);
            }
        } catch (Exception e) {
            System.out.println("Controller error: " + e.getMessage());
        }
    }
}
