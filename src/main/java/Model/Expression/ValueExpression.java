package Model.Expression;

import Model.Exception.GenericException;
import Model.State.*;
import Model.Type.IType;
import Model.Value.*;
import Utils.Dictionary.IMyDictionary;

public class ValueExpression implements IExpression {
    private final IValue value;

    public ValueExpression(IValue newValue) {
        this.value = newValue;
    }

    @Override
    public IValue evaluate(ISymbolTable symbolTable, IHeapTable heapTable) {
        return value;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public IType typeCheck(IMyDictionary<String, IType> typeEnv) throws GenericException {
        return value.getType();
    }
}
