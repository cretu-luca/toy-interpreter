package Model.Expression;

import Model.Exception.ArithmeticExpressionException;
import Model.Value.*;
import Utils.Dictionary.IMyDictionary;
import Model.Exception.GenericException;
import Model.State.*;
import Model.Type.*;

public class ArithmeticExpression implements IExpression {
    private final IExpression firstExpression;
    private final IExpression secondExpression;

    String operator;

    public ArithmeticExpression(IExpression newFirstExpression, IExpression newSecondExpression, String newOperator) {
        this.firstExpression = newFirstExpression;
        this.secondExpression = newSecondExpression;
        this.operator = newOperator;
    }

    @Override
    public IValue evaluate(ISymbolTable symbolTable, IHeapTable heapTable) {
        IValue firstValue = firstExpression.evaluate(symbolTable, heapTable);
        if(firstValue.getType().equals(new IntType())) {
            IValue secondValue = secondExpression.evaluate(symbolTable, heapTable);
            if(secondValue.getType().equals(new IntType())) {
                Integer firstInteger = ((IntValue) firstValue).getValue();
                Integer secondInteger =  ((IntValue) secondValue).getValue();

                switch(operator) {
                    case "+": return new IntValue(firstInteger + secondInteger);
                    case "-": return new IntValue(firstInteger - secondInteger);
                    case "*": return new IntValue(firstInteger * secondInteger);
                    case "/": 
                        if(secondInteger == 0) 
                            throw new ArithmeticExpressionException("Division by zero!");
                        return new IntValue(firstInteger / secondInteger);
                    default: 
                        throw new ArithmeticExpressionException("Invalid operator!");
                }
            } else throw new ArithmeticExpressionException("Second operand is not an integer");
        } else throw new ArithmeticExpressionException("First operand is not an integer");
    }
    
    @Override
    public String toString() {
        return this.firstExpression + " " + operator + " " + this.secondExpression;
    }

    @Override
    public IType typeCheck(IMyDictionary<String, IType> typeEnv) throws GenericException {
        IType firstType, secondType;
        firstType = firstExpression.typeCheck(typeEnv);
        secondType = secondExpression.typeCheck(typeEnv);
        if(firstType.equals(new IntType())) {
            if(secondType.equals(new IntType())) {
                return new IntType();
            } else throw new ArithmeticExpressionException("Second operand is not an integer.");
        } else throw new ArithmeticExpressionException("First operand is not an integer.");
    }
}
