package Model.Statement;

import Model.Exception.GenericException;
import Model.Expression.*;
import Model.State.*;
import Model.Value.*;
import Model.Type.*;

public class HeapAllocationStatement implements IStatement {
    public final String variableName; 
    IExpression expression;

    public HeapAllocationStatement(String newVariableName, IExpression newExpression) {
        this.variableName = newVariableName;
        this.expression = newExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        ISymbolTable symbolTable = state.getSymbolTable();
        if(!symbolTable.isDefined(variableName)) {
            throw new GenericException("HeapAllocationStatement error: " + this.variableName + " is not defined.");
        }

        if(!(symbolTable.get(variableName).getType() instanceof ReferenceType)) {
            throw new GenericException("HeapAllocationStatement error: " + this.variableName + " is not a reference variable.");
        }

        IValue value = expression.evaluate(symbolTable);
        ReferenceType referenceValueType = (ReferenceType) symbolTable.get(variableName).getType();
        if(!(value.getType().equals(referenceValueType))) {
            throw new GenericException("HeapAllocationStatement error: " + variableName + " and " + value + " do not have the same reference type.");
        }

        // THIS IS WHERE YOU START

        return state;
    }
    
}
