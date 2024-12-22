package Model.Statement;

import Model.Exception.GenericException;
import Model.Expression.*;
import Model.State.*;
import Model.Value.*;
import Utils.Dictionary.IMyDictionary;
import Model.Type.*;

public class HeapAllocationStatement implements IStatement {
    private final String variableName; 
    private final IExpression expression;

    public HeapAllocationStatement(String newVariableName, IExpression newExpression) {
        this.variableName = newVariableName;
        this.expression = newExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        ISymbolTable symbolTable = state.getSymbolTable();
        IHeapTable heapTable = state.getHeapTable();

        if(!symbolTable.isDefined(variableName)) {
            throw new GenericException("HeapAllocationStatement error: " + this.variableName + " is not defined.");
        }

        if(!(symbolTable.get(variableName).getType() instanceof ReferenceType)) {
            throw new GenericException("HeapAllocationStatement error: " + this.variableName + " is not a reference variable.");
        }

        IValue value = expression.evaluate(symbolTable, heapTable);
        IType referenceValueType = ((ReferenceValue) symbolTable.get(variableName)).getReferencedType();
        if(!(value.getType().equals(referenceValueType))) {
            throw new GenericException("HeapAllocationStatement error: " + variableName + " and " + value + " do not have the same reference type.");
        }

        Integer newPosition = state.getHeapTable().allocate(value);
        IValue newReference = new ReferenceValue(newPosition, referenceValueType);
        symbolTable.update(variableName, newReference);

        return null;
    }

    @Override
    public String toString() {
        return "new(" + this.variableName + ", " + this.expression + ")";
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnv) throws GenericException {
        IType variableType = typeEnv.get(variableName);
        IType expressionType = expression.typeCheck(typeEnv);
        
        if (variableType.equals(new ReferenceType(expressionType))) {
            return typeEnv;
        } else 
            throw new GenericException("HeapAllocationStatement error: right hand side and left hand side have different types.");
    }
}
