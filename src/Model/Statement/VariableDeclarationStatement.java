package Model.Statement;

import Model.ProgramState;
import Model.Exception.*;
import Model.Value.*;
import Model.Type.*;
import Utils.Dictionary.*;

public class VariableDeclarationStatement implements IStatement {
    public String variableName;
    public IType variableType;

    public VariableDeclarationStatement(String variableName, IType variableType) {
        this.variableName = variableName;
        this.variableType = variableType;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        IMyDictionary<String, IValue> symbolTable = state.getSymbolTable();

        if(symbolTable.isDefined(variableName)) {
            throw new GenericException("VariableDeclarationStatement error: variable " + variableName + " already exists.");
        } else {
            symbolTable.add(variableName, variableType.defaultValue());
        }
        
        return state;
    }
    
    @Override
    public String toString() {
        return this.variableType + " " + this.variableName;
    }
}
