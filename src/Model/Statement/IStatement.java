package Model.Statement;

import Model.ProgramState;
import Model.Exception.GenericException;

public interface IStatement {
    ProgramState execute(ProgramState state) throws GenericException;
    String toString();
}
