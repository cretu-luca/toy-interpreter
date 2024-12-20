package Model.Expression;

import Model.State.*;
import Model.Value.*;

public class VariableExpression implements IExpression {
    private StringValue variableName;

    public VariableExpression(StringValue newVariableName) {
        this.variableName = newVariableName;
    }

    @Override
    public IValue evaluate(ISymbolTable symbolTable) {
        return symbolTable.get(variableName);
    }

    @Override
    public String toString() {
        return variableName.toString();
    }
}
