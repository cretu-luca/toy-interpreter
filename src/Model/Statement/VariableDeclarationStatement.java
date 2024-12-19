package Model.Statement;

import com.sun.jdi.Value;

import Model.ProgramState;
import Model.Exception.GenericException;
import Utils.Dictionary.IMyDictionary;

public class VariableDeclarationStatement implements IStatement {
    public String variableName;
    public String variableType;

    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        IMyDictionary<String, Value> symbolTable = state.getSymbolTable();

        if(symbolTable.isDefined(variableName)) {
            throw new GenericException("VariableDeclarationStatement error: variable " + variableName + " already exists.");
        } else {
            symbolTable.add()
        }
        
        return state;
    }
    
}
