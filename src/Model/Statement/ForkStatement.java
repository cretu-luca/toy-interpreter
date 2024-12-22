package Model.Statement;

import Model.State.*;
import Model.Type.IType;
import Utils.Dictionary.IMyDictionary;
import Model.Exception.*;

public class ForkStatement implements IStatement {
    private IStatement statement;

    public ForkStatement(IStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        IExecutionStack newStack = new ExecutionStack();

        return new ProgramState(
            newStack,
            state.getSymbolTable().deepCopy(),
            state.getOutput(),
            state.getFileTable(),
            state.getHeapTable(),
            statement
        );
    }

    @Override
    public String toString() { 
        return "fork" + this.statement.toString() + "";
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnv) throws GenericException {
        statement.typeCheck(typeEnv);
        return typeEnv;
    }
}