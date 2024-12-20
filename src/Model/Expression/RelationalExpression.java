package Model.Expression;

import Model.Exception.GenericException;
import Model.State.ISymbolTable;
import Model.Type.IntType;
import Model.Value.BooleanValue;
import Model.Value.IValue;
import Model.Value.IntValue;

public class RelationalExpression implements IExpression {
    public final IExpression firstExpression;
    public final IExpression secondExpression;
    public final String operator;

    public RelationalExpression(IExpression newFirstExpression, IExpression newSecondExpression, String newOperator) {
        this.firstExpression = newFirstExpression;
        this.secondExpression = newSecondExpression;
        this.operator = newOperator;
    }

    @Override
    public IValue evaluate(ISymbolTable symbolTable) {
        IValue firstValue = this.firstExpression.evaluate(symbolTable);
        if(firstValue.getType().equals(new IntType())) {
            IValue secondValue = this.secondExpression.evaluate(symbolTable);
            if(secondValue.getType().equals(new IntType())) {
                Integer firstInteger = ((IntValue) firstValue).getValue();
                Integer secondInteger = ((IntValue) secondValue).getValue();

                switch(operator) {
                    case "<": return new BooleanValue(firstInteger <secondInteger);
                    case "<=": return new BooleanValue(firstInteger <= secondInteger);
                    case ">": return new BooleanValue(firstInteger > secondInteger);
                    case ">=": return new BooleanValue(firstInteger >= secondInteger);
                    case "==": return new BooleanValue(firstInteger == secondInteger);
                    case "!=": return new BooleanValue(firstInteger != secondInteger);
                    default: throw new GenericException("RelationalExpression: Invalid operator.");
                }
            } else throw new GenericException("Second operand is not integer.");
        } else throw new GenericException("First operand is not integer.");
    }    
}
