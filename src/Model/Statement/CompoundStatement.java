package Model.Statement;

import Model.ProgramState;
import Utils.Stack.IMyStack;

public class CompoundStatement implements IStatement {
    private final IStatement firstStatement;
    private final IStatement secondStatement;

    public CompoundStatement(IStatement first, IStatement second) {
        this.firstStatement = first;
        this.secondStatement = second;
    }

    public ProgramState execute(ProgramState state) {
        IMyStack<IStatement> stack = state.getExecutionStack();
        stack.push(secondStatement);
        stack.push(firstStatement);

        return state;
    }

    @Override
    public String toString() {
        return "( " + firstStatement.toString() + "; " + secondStatement.toString() + ")";
    }
}
