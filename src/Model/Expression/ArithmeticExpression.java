package Model.Expression;

import Model.Value.*;
import Model.Exception.GenericException;
import Model.State.SymbolTable;
import Model.Type.*;

public class ArithmeticExpression implements IExpression {
    IExpression firstExpression;
    IExpression secondExpression;

    String operator;

    public ArithmeticExpression(IExpression newFirstExpression, IExpression newSecondExpression, String newOperator) {
        this.firstExpression = newFirstExpression;
        this.secondExpression = newSecondExpression;
        this.operator = newOperator;
    }

    @Override
    public IValue evaluate(ISymbolTable symbolTable) {
        IValue firstValue = firstExpression.evaluate(symbolTable);
        if(firstValue.getType().equals(new IntType())) {
            IValue secondValue = secondExpression.evaluate(symbolTable);
            if(secondValue.getType().equals(new IntType())) {
                Integer firstInteger = ((IntValue) firstValue).getValue();
                Integer secondInteger =  ((IntValue) secondValue).getValue();

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
