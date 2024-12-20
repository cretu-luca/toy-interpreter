package Controller;

import Repository.IRepository;
import Model.Exception.GenericException;
import Model.Statement.IStatement;
import Model.State.*;

public class Controller {
    private IRepository repository;

    public Controller(IRepository repository) {
        this.repository = repository;
    }

    public ProgramState oneStep(ProgramState state) throws GenericException{
        IExecutionStack stack = state.getExecutionStack();
        if(stack.isEmpty()) {
            throw new GenericException("oneStep: ExecutionStack is empty!");
        }

        IStatement currentStatement = stack.pop();
        return currentStatement.execute(state);
    }

    public void allSteps() {
        try {
            ProgramState state = repository.getProgramState();
            if (state == null) {
                System.out.println("Error: Null program state");
                return;
            }

            System.out.println(state);
            repository.logProgramState();

            while (!state.getExecutionStack().isEmpty()) {
                state = oneStep(state);
                
                System.out.println(state);
                repository.logProgramState();
            }
        } catch (Exception e) {
            System.out.println("Controller error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
