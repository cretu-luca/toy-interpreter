package Model.Statement;

import Model.State.ProgramState;
import Model.Exception.*;
import Model.State.*;
import Model.Type.*;

public class VariableDeclarationStatement implements IStatement {
    public String variableName;
    public IType variableType;

    public VariableDeclarationStatement(String newVariableName, IType newVariableType) {
        this.variableName = newVariableName;
        this.variableType = newVariableType;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        ISymbolTable symbolTable = state.getSymbolTable();

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
