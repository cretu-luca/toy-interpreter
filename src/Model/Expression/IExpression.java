package Model.Expression;

import Model.Exception.GenericException;
import Model.State.IHeapTable;
import Model.State.ISymbolTable;
import Model.Type.IType;
import Model.Value.IValue;
import Utils.Dictionary.IMyDictionary;

public interface IExpression {
    IValue evaluate(ISymbolTable symbolTable, IHeapTable heapTable);
    IType typeCheck(IMyDictionary<String, IType> typeEnv) throws GenericException;
}
