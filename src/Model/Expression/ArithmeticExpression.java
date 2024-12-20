package Model.Expression;

import Model.Value.*;
import Model.Exception.GenericException;
import Model.Type.*;
import Utils.Dictionary.IMyDictionary;

public class ArithmeticExpression implements IExpression {
    IExpression firstExpression;
    IExpression secondExpression;

    String operator;

    public ArithmeticExpression(IExpression first, IExpression second, String operator) {
        this.firstExpression = first;
        this.secondExpression = second;
        this.operator = operator;
    }

    @Override
    public IValue evaluate(IMyDictionary<String, IValue> symbolTable) {
        IValue firstValue = firstExpression.evaluate(symbolTable);
        if(firstValue.getType().equals(new IntType())) {
            IValue secondValue = secondExpression.evaluate(symbolTable);
            if(secondValue.getType().equals(new IntType())) {
                int firstInteger = ((IntValue) firstValue).getValue();
                int secondInteger =  ((IntValue) secondValue).getValue();

                switch(operator) {
                    case "+": return new IntValue(firstInteger + secondInteger);
                    case "-": return new IntValue(firstInteger - secondInteger);
                    case "*": return new IntValue(firstInteger * secondInteger);
                    case "/": 
                        if(secondInteger == 0) 
                            throw new GenericException("Division by zero!");
                        return new IntValue(firstInteger / secondInteger);
                    default: 
                        throw new GenericException("Invalid operator!");
                }
            } else throw new GenericException("Second operand is not an integer");
        } else throw new GenericException("First operand is not an integer");
    }
    
    @Override
    public String toString() {
        return this.firstExpression + " " + operator + " " + this.secondExpression;
    }
}
