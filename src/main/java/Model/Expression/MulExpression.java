package Model.Expression;

import Model.Exception.ArithmeticExpressionException;
import Model.Exception.GenericException;
import Model.State.IHeapTable;
import Model.State.ISymbolTable;
import Model.Type.IType;
import Model.Type.IntType;
import Model.Value.IValue;
import Model.Value.IntValue;
import Utils.Dictionary.IMyDictionary;

public class MulExpression implements IExpression {
    private final IExpression first;
    private final IExpression second;

    public MulExpression(IExpression first, IExpression second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public IValue evaluate(ISymbolTable symbolTable, IHeapTable heapTable) {
        IValue firstEval =  first.evaluate(symbolTable, heapTable);
        IValue secondEval = second.evaluate(symbolTable, heapTable);

        Integer firstValue = ((IntValue) firstEval).getValue();
        Integer secondValue = ((IntValue) secondEval).getValue();

        return new IntValue(firstValue * secondValue - (firstValue + secondValue));
    }

    @Override
    public IType typeCheck(IMyDictionary<String, IType> typeEnv) throws GenericException {
        IType firstType, secondType;
        firstType = first.typeCheck(typeEnv);
        secondType = second.typeCheck(typeEnv);
        if(firstType.equals(new IntType())) {
            if(secondType.equals(new IntType())) {
                return new IntType();
            } else throw new ArithmeticExpressionException("Second operand is not an integer.");
        } else throw new ArithmeticExpressionException("First operand is not an integer.");
    }
}
