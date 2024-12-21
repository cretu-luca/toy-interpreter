package Model.Statement;

import Model.Exception.*;
import Model.Value.*;
import Model.Expression.*;
import Model.State.*;

public class PrintStatement implements IStatement {
    private final IExpression expressionToPrint;
    
    public PrintStatement(IExpression newExpressionToPrint) {
        this.expressionToPrint = newExpressionToPrint;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        IOutput output = state.getOutput();
        ISymbolTable symbolTable = state.getSymbolTable();
        IHeapTable heapTable = state.getHeapTable();

        IValue value = expressionToPrint.evaluate(symbolTable, heapTable);
        output.add(value);

        return state;
    }

    @Override
    public String toString() {
        return "print(" + expressionToPrint.toString() + ")"; 
    }
}