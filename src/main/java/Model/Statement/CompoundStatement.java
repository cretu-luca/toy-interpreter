package Model.Statement;

import Model.Type.IType;
import Utils.Dictionary.IMyDictionary;
import Model.Exception.GenericException;
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

        return null;
    }

    @Override
    public String toString() {
        return "( " + firstStatement.toString() + "; " + secondStatement.toString() + ")";
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnv) throws GenericException {
        return secondStatement.typeCheck(firstStatement.typeCheck(typeEnv));
    }
}
