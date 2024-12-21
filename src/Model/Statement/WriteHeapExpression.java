package Model.Statement;

import Model.Exception.GenericException;
import Model.State.IHeapTable;
import Model.State.ISymbolTable;
import Model.State.ProgramState;
import Model.Value.IValue;
import Model.Value.ReferenceValue;
import Model.Expression.*;

public class WriteHeapExpression implements IStatement {
    private final String variableName;
    private final IExpression expression;
    
    public WriteHeapExpression(String newVariableName, IExpression newExpression) {
        this.variableName = newVariableName;
        this.expression = newExpression;
    }
    
    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        ISymbolTable symbolTable = state.getSymbolTable();
        IHeapTable heapTable = state.getHeapTable();
        
        if(!symbolTable.isDefined(variableName)) {
            throw new GenericException("WriteHeapExpression error: " + this.variableName + " has not been defined.");
        }

        IValue variableValue = symbolTable.get(variableName);
        if(!(variableValue instanceof ReferenceValue)) { 
            throw new GenericException("WriteHeapExpression error: " + this.variableName + " is not a reference value.");
        }

        ReferenceValue referenceVariableValue = (ReferenceValue) variableValue;
        Integer address = referenceVariableValue.getAddress();

        if(!(heapTable.isAddressDefined(address))) {
            throw new GenericException("WriteHeapExpression error: " + this.variableName + " has not been allocated on the heap.");
        }

        IValue expressionValue = this.expression.evaluate(symbolTable, heapTable);
        if(!(expressionValue.getType().equals(((ReferenceValue) variableValue).getReferencedType()))) {
            throw new GenericException("WriteHeapExpression error: type mismatch");
        }

        heapTable.update(address, expressionValue);

        return state;
    }

    @Override
    public String toString() {
        return "writeHeap(" + this.variableName + ", " + this.expression + ")"; 
    }
}