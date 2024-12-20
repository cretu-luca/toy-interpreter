package Controller;

import Repository.IRepository;
import Model.Exception.GenericException;
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
        IExecutionStack stack = state.getExecutionStack();
        if(stack.isEmpty()) {
            throw new GenericException("oneStep: ExecutionStack is empty!");
        }

        IStatement currentStatement = stack.pop();
        this.repository.logProgramState();

        return currentStatement.execute(state);
    }

    public void allSteps() {
    try {
        ProgramState state = repository.getProgramState();
        if (state == null) {
            System.out.println("Error: Null program state");
            return;
        }

        while (!state.getExecutionStack().isEmpty()) {
            try {
                if (displayFlag) {
                    System.out.println(state);
                }
                
                // Add null checks
                if (state.getExecutionStack() == null) {
                    System.out.println("Error: Null execution stack");
                    break;
                }
                
                state = oneStep(state);
                
                if (state == null) {
                    System.out.println("Error: oneStep returned null state");
                    break;
                }
            } catch (GenericException e) {
                System.out.println("Execution error: " + e.getMessage());
                e.printStackTrace(); // This will print the full stack trace
                break;
            }
        }
        
        if (displayFlag) {
            System.out.println(state);
        }
    } catch (Exception e) {
        System.out.println("Controller error: " + e.getMessage());
        e.printStackTrace(); // This will print the full stack trace
    }
}
}
