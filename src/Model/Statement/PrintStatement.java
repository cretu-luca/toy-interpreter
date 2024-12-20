package Model.Statement;

import Model.Exception.*;
import Model.Value.*;
import Model.Expression.*;
import Model.State.*;
import Model.State.ProgramState;

public class PrintStatement implements IStatement {
    IExpression expressionToPrint;
    
    public PrintStatement(IExpression newExpressionToPrint) {
        this.expressionToPrint = newExpressionToPrint;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        IOutput output = state.getOutput();
        ISymbolTable symbolTable = state.getSymbolTable();

        IValue value = expressionToPrint.evaluate(symbolTable);
        output.add(value);

        return state;
    }

    @Override
    public String toString() {
        return "print(" + expressionToPrint.toString() + ")"; 
    }
}