package Model.Statement;

import Model.Exception.*;
import Model.Type.*;
import Model.Value.*;
import Model.Expression.*;
import Model.State.ProgramState;
import Model.State.ISymbolTable;
import Utils.Dictionary.*;

public class AssignmentStatement implements IStatement {
    private final StringValue variableName;
    private final IExpression expression;

    public AssignmentStatement(StringValue newVariableName, IExpression newExpression) {
        this.variableName = newVariableName;
        this.expression = newExpression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        ISymbolTable symbolTable = state.getSymbolTable();

        if(symbolTable.isDefined(variableName)) {
            IValue value = expression.evaluate(symbolTable);
            IType type = symbolTable.get(variableName).getType();

            if(value.getType().equals(type)) {
                symbolTable.update(variableName, value);
            } else throw new GenericException("AssignmentStatement execute: declared type of variable " + this.variableName + " and the type of the assigned expression do not match.");
        } else throw new GenericException("AssignmentStatement execute: the used variable was not delcared before.");
        
        return state;
    }

    @Override
    public String toString() {
        return this.variableName + " = " + expression.toString();
    }
    
}
