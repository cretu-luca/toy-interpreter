package Model.Expression;

import Model.Exception.GenericException;
import Model.State.IHeapTable;
import Model.State.ISymbolTable;
import Model.Type.BooleanType;
import Model.Type.IType;
import Model.Value.BooleanValue;
import Model.Value.IValue;
import Utils.Dictionary.IMyDictionary;

public class NotExpression implements IExpression {
    private final IExpression expression;

    public NotExpression(IExpression expression) {
        this.expression = expression;
    }

    @Override
    public IValue evaluate(ISymbolTable symbolTable, IHeapTable heapTable) {
        IValue value = expression.evaluate(symbolTable, heapTable);
        if (value instanceof BooleanValue) {
            BooleanValue boolValue = (BooleanValue) value;
            return new BooleanValue(!boolValue.getValue());
        }
        throw new GenericException("Expression is not a boolean");
    }

    @Override
    public IType typeCheck(IMyDictionary<String, IType> typeEnv) throws GenericException {
        IType type = expression.typeCheck(typeEnv);
        if (type.equals(new BooleanType())) {
            return new BooleanType();
        }
        throw new GenericException("Expression is not a boolean");
    }

    @Override
    public String toString() {
        return "!(" + expression.toString() + ")";
    }
}