package Model.Expression;

import Model.Exception.GenericException;
import Model.State.*;
import Model.Type.IType;
import Model.Value.*;
import Utils.Dictionary.*;

public class VariableExpression implements IExpression {
    private final String variableName;

    public VariableExpression(String newVariableName) {
        this.variableName = newVariableName;
    }

    @Override
    public IValue evaluate(ISymbolTable symbolTable, IHeapTable heapTable) {
        return symbolTable.get(variableName);
    }

    @Override
    public String toString() {
        return variableName.toString();
    }

    @Override
    public IType typeCheck(IMyDictionary<String, IType> typeEnv) throws GenericException {
        return typeEnv.get(variableName);
    }
}   
