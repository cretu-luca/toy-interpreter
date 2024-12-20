package Model.Expression;

import Model.Value.IValue;
import Utils.Dictionary.IMyDictionary;

public class ValueExpression implements IExpression {
    IValue value;

    public ValueExpression(IValue value) {
        this.value = value;
    }

    @Override
    public IValue evaluate(IMyDictionary<String, IValue> symbolTable) {
        return value;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
