package Model.Expression;

import Model.Exception.GenericException;
import Model.Exception.RelationalExpressionException;
import Model.State.IHeapTable;
import Model.State.ISymbolTable;
import Model.Type.BooleanType;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.BooleanValue;
import Model.Value.IValue;
import Model.Value.IntValue;
import Utils.Dictionary.IMyDictionary;

public class RelationalExpression implements IExpression {
    private final IExpression firstExpression;
    private final IExpression secondExpression;
    private final String operator;

    public RelationalExpression(IExpression newFirstExpression, IExpression newSecondExpression, String newOperator) {
        this.firstExpression = newFirstExpression;
        this.secondExpression = newSecondExpression;
        this.operator = newOperator;
    }

    @Override
    public IValue evaluate(ISymbolTable symbolTable, IHeapTable heapTable) {
        IValue firstValue = this.firstExpression.evaluate(symbolTable, heapTable);
        if(firstValue.getType().equals(new IntType())) {
            IValue secondValue = this.secondExpression.evaluate(symbolTable, heapTable);
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
                    default: throw new RelationalExpressionException("RelationalExpression: Invalid operator.");
                }
            } else throw new RelationalExpressionException("Second operand is not integer.");
        } else throw new RelationalExpressionException("First operand is not integer.");
    }

    @Override
    public String toString() {
        return this.firstExpression + " " + operator + " " + this.secondExpression;
    }

     @Override
    public IType typeCheck(IMyDictionary<String, IType> typeEnv) throws GenericException {
        IType type1 = firstExpression.typeCheck(typeEnv);
        IType type2 = secondExpression.typeCheck(typeEnv);
        
        if (type1.equals(new IntType()) && type2.equals(new IntType())) {
            return new BooleanType();
        } else {
            throw new RelationalExpressionException("RelationalExpression: operands must be integers");
        }
    }
}
