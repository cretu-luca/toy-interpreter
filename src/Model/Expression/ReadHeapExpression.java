package Model.Expression;

import Model.Exception.GenericException;
import Model.State.*;
import Model.Value.*;

public class ReadHeapExpression implements IExpression {
    private final IExpression expression; 

    public ReadHeapExpression(IExpression newExpression) {
        this.expression = newExpression;
    }

    @Override
    public IValue evaluate(ISymbolTable symbolTable, IHeapTable heapTable) {
        IValue expressionValue = this.expression.evaluate(symbolTable, heapTable);
        if(!(expressionValue instanceof ReferenceValue)) {
            throw new GenericException("ReadHeapExpression error: " + this.expression + " is not a reference value.");
        }

        ReferenceValue expressionReferenceValue = (ReferenceValue) expressionValue;
        Integer address = expressionReferenceValue.getAddress();
        
        if(!heapTable.isAddressDefined(address)) {
            throw new GenericException("ReadHeapExpression error: " + this.expression + " was not allocated on the heap.");
        }

        return heapTable.get(address);
    }   

    @Override
    public String toString() {
        return "readHeap(" + expression.toString() + ")";
    }
}
