package Model.Expression;

import Model.Value.*;
import Utils.Dictionary.IMyDictionary;
import Model.Type.*;
import Model.Exception.*;
import Model.State.*;

public class LogicExpression implements IExpression {
    private final IExpression firstExpression;
    private final IExpression secondExpression;

    private final String operator;

    LogicExpression(IExpression newFirstExpression, IExpression newSecondExpression, String newOperator) {
        this.firstExpression = newFirstExpression;
        this.secondExpression = newSecondExpression;
        this.operator = newOperator;
    }

    @Override
    public IValue evaluate(ISymbolTable symbolTable, IHeapTable heapTable) {
        IValue firstValue = firstExpression.evaluate(symbolTable, heapTable);
        if(firstValue.getType().equals(new BooleanType())) {
            IValue secondValue = secondExpression.evaluate(symbolTable, heapTable);
            if(secondValue.getType().equals(new BooleanType())) {
                Boolean firstBoolean = ((BooleanValue) firstValue).getValue();
                Boolean secondBoolean = ((BooleanValue) secondValue).getValue();

                switch(operator) {
                    case "&": return new BooleanValue(firstBoolean & secondBoolean);
                    case "|": return new BooleanValue(firstBoolean | secondBoolean);
                    default: throw new LogicExpressionException("Invalid operator.");
                }
            } else throw new LogicExpressionException("Second operand is not boolean value.");
        } else throw new LogicExpressionException("First operand is not boolean value.");
    }

    @Override
    public IType typeCheck(IMyDictionary<String, IType> typeEnv) throws GenericException {
        IType firstType, secondType;
        firstType = firstExpression.typeCheck(typeEnv);
        secondType = secondExpression.typeCheck(typeEnv);
        if(firstType.equals(new BooleanType())) {
            if(secondType.equals(new BooleanType())) {
                return new BooleanType();
            } else throw new LogicExpressionException("Second operand is not a boolean value.");
        } else throw new LogicExpressionException("First operand is not an a boolean value.");
    }
}
