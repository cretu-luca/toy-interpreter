package Model.Statement;

import Model.Exception.GenericException;
import Model.State.ProgramState;
import Model.Type.IType;
import Utils.Dictionary.IMyDictionary;

public interface IStatement {
    ProgramState execute(ProgramState state) throws GenericException;
    String toString();
    IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnv) throws GenericException;
}
