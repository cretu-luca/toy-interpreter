package Model.Statement;

import Model.Exception.GenericException;
import Model.State.ProgramState;

public interface IStatement {
    ProgramState execute(ProgramState state) throws GenericException;
    String toString();
}
