package Model.Expression;

import Model.State.*;
import Model.Value.*;

public class VariableExpression implements IExpression {
    private String variableName;

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
}
