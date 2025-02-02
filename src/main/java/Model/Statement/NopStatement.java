package Model.Statement;

import Model.Exception.GenericException;
import Model.State.ProgramState;
import Model.Type.IType;
import Utils.Dictionary.IMyDictionary;

public class NopStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) {
        return state;
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnv) throws GenericException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "()";
    }
}
