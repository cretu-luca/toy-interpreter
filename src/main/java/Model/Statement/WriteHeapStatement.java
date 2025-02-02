package Model.Statement;

import Model.Exception.GenericException;
import Model.Exception.WriteHeapExpressionException;
import Model.State.IHeapTable;
import Model.State.ISymbolTable;
import Model.State.ProgramState;
import Model.Type.IType;
import Model.Type.ReferenceType;
import Model.Value.IValue;
import Model.Value.ReferenceValue;
import Utils.Dictionary.IMyDictionary;
import Model.Expression.*;

public class WriteHeapStatement implements IStatement {
    private final String variableName;
    private final IExpression expression;
    
    public WriteHeapStatement(String newVariableName, IExpression newExpression) {
        this.variableName = newVariableName;
        this.expression = newExpression;
    }
    
    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        ISymbolTable symbolTable = state.getSymbolTable();
        IHeapTable heapTable = state.getHeapTable();
        
        if(!symbolTable.isDefined(variableName)) {
            throw new WriteHeapExpressionException("WriteHeapExpression error: " + this.variableName + " has not been defined.");
        }

        IValue variableValue = symbolTable.get(variableName);
        if(!(variableValue instanceof ReferenceValue)) { 
            throw new WriteHeapExpressionException("WriteHeapExpression error: " + this.variableName + " is not a reference value.");
        }

        ReferenceValue referenceVariableValue = (ReferenceValue) variableValue;
        Integer address = referenceVariableValue.getAddress();

        if(!(heapTable.isAddressDefined(address))) {
            throw new WriteHeapExpressionException("WriteHeapExpression error: " + this.variableName + " has not been allocated on the heap.");
        }

        IValue expressionValue = this.expression.evaluate(symbolTable, heapTable);
        if(!(expressionValue.getType().equals(((ReferenceValue) variableValue).getReferencedType()))) {
            throw new WriteHeapExpressionException("WriteHeapExpression error: type mismatch");
        }

        heapTable.update(address, expressionValue);

        return null;
    }

    @Override
    public String toString() {
        return "writeHeap(" + this.variableName + ", " + this.expression + ")"; 
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnv) throws GenericException {
        IType variableType = typeEnv.get(variableName);
    
        if (!(variableType instanceof ReferenceType)) {
            throw new WriteHeapExpressionException("WriteHeapExpression error: " + variableName + " is not of reference type");
        }
        
        ReferenceType refType = (ReferenceType) variableType;
        IType referencedType = refType.getType();
        
        IType expressionType = expression.typeCheck(typeEnv);
        
        if (expressionType.equals(referencedType)) {
            return typeEnv;
        } else {
            throw new WriteHeapExpressionException("WriteHeapExpression error: type mismatch between " + referencedType + " and " + expressionType);
        }
    }
}