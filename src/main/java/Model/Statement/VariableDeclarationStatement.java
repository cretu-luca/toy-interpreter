package Model.Statement;

import Model.Exception.*;
import Model.State.*;
import Model.Type.*;
import Utils.Dictionary.IMyDictionary;

public class VariableDeclarationStatement implements IStatement {    
    private final String variableName;
    private final IType variableType;

    public VariableDeclarationStatement(String newVariableName, IType newVariableType) {
        this.variableName = newVariableName;
        this.variableType = newVariableType;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        ISymbolTable symbolTable = state.getSymbolTable();

        if(symbolTable.isDefined(variableName)) {
            throw new VariableDeclarationStatementException("VariableDeclarationStatement error: variable " + variableName + " already exists.");
        } else {
            symbolTable.add(variableName, variableType.defaultValue());
        }
        
        return null;
    }
    
    @Override
    public String toString() {
        return this.variableType + " " + this.variableName;
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnv) throws GenericException {
        typeEnv.add(variableName, variableType);
        return typeEnv;
    }
}
