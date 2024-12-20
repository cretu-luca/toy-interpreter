package Model.Statement;

import Model.State.ProgramState;
import Model.State.*; 

public class CompoundStatement implements IStatement {
    private final IStatement firstStatement;
    private final IStatement secondStatement;

    public CompoundStatement(IStatement newFirstStatement, IStatement newSecondStatement) {
        this.firstStatement = newFirstStatement;
        this.secondStatement = newSecondStatement;
    }

    public ProgramState execute(ProgramState state) {
        IExecutionStack stack = state.getExecutionStack();
        stack.push(secondStatement);
        stack.push(firstStatement);

        return state;
    }

    @Override
    public String toString() {
        return "( " + firstStatement.toString() + "; " + secondStatement.toString() + ")";
    }
}
