package Model.Expression;

import Model.State.*;
import Model.Value.*;

public class ValueExpression implements IExpression {
    IValue value;

    public ValueExpression(IValue newValue) {
        this.value = newValue;
    }

    @Override
    public IValue evaluate(ISymbolTable symbolTable) {
        return value;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }
}
