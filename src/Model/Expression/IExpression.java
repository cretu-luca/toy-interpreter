package Model.Expression;

import Model.State.IHeapTable;
import Model.State.ISymbolTable;
import Model.Value.IValue;

public interface IExpression {
    IValue evaluate(ISymbolTable symbolTable, IHeapTable heapTable);
}
