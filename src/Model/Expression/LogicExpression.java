package Model.Expression;

import Model.Value.*;
import Model.Type.*;
import Model.Exception.*;
import Utils.Dictionary.*;

public class LogicExpression implements IExpression {
    private final IExpression firstExpression;
    private final IExpression secondExpression;

    private final String operator;

    LogicExpression(IExpression firstExpression, IExpression secondExpression, String operator) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(IMyDictionary<String, IValue> symbolTable) {
        IValue firstValue = firstExpression.evaluate(symbolTable);
        if(firstValue.getType().equals(new BooleanType())) {
            IValue secondValue = secondExpression.evaluate(symbolTable);
            if(secondValue.getType().equals(new BooleanType())) {
                boolean firstBoolean = ((BooleanValue) firstValue).getValue();
                boolean secondBoolean = ((BooleanValue) secondValue).getValue();

                switch(operator) {
                    case "&": return new BooleanValue(firstBoolean & secondBoolean);
                    case "|": return new BooleanValue(firstBoolean | secondBoolean);
                    default: throw new GenericException("Invalid operator.");
                }
            } else throw new GenericException("Second operand is not boolean value.");
        } else throw new GenericException("First operand is not boolean value.");
    }
    
}
