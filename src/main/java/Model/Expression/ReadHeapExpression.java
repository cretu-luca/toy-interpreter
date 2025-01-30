package Model.Expression;

import Model.Exception.GenericException;
import Model.Exception.ReadHeapException;
import Model.State.*;
import Model.Type.*;
import Model.Value.*;
import Utils.Dictionary.IMyDictionary;

public class ReadHeapExpression implements IExpression {
    private final IExpression expression; 

    public ReadHeapExpression(IExpression newExpression) {
        this.expression = newExpression;
    }

    @Override
    public IValue evaluate(ISymbolTable symbolTable, IHeapTable heapTable) {
        IValue expressionValue = this.expression.evaluate(symbolTable, heapTable);
        if(!(expressionValue instanceof ReferenceValue)) {
            throw new ReadHeapException("ReadHeapExpression error: " + this.expression + " is not a reference value.");
        }

        ReferenceValue expressionReferenceValue = (ReferenceValue) expressionValue;
        Integer address = expressionReferenceValue.getAddress();
        
        if(!heapTable.isAddressDefined(address)) {
            throw new ReadHeapException("ReadHeapExpression error: " + this.expression + " was not allocated on the heap.");
        }

        return heapTable.get(address);
    }   

    @Override
    public String toString() {
        return "readHeap(" + expression.toString() + ")";
    }

    @Override
    public IType typeCheck(IMyDictionary<String, IType> typeEnv) throws GenericException {
        IType type = expression.typeCheck(typeEnv);
        if (type instanceof ReferenceType) {
            ReferenceType referenceType =(ReferenceType) type;
            return referenceType.getType(); 
        } else throw new ReadHeapException("the readHeap argument is not a reference value");
    }
}
