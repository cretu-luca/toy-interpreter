package Model.Expression;

import Model.Value.IValue;
import Utils.Dictionary.IMyDictionary;

public class VariableExpression implements IExpression {
    String variableName;

    public VariableExpression(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public IValue evaluate(IMyDictionary<String, IValue> symbolTable) {
        return symbolTable.get(variableName);
    }
}
