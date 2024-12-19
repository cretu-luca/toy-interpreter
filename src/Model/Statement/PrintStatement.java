package Model.Statement;

import Model.ProgramState;
import Model.Exception.*;
import Model.Value.*;
import Model.Expression.*;
import Utils.Dictionary.*;
import Utils.List.*;

public class PrintStatement implements IStatement {
    IExpression expressionToPrint;
    
    public PrintStatement(IExpression expressionToPrint) {
        this.expressionToPrint = expressionToPrint;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        IMyList<IValue> output = state.getOutput();
        IMyDictionary<String, IValue> symbolTable = state.getSymbolTable();

        IValue value = expressionToPrint.evaluate(symbolTable);
        output.add(value);

        return state;
    }

    @Override
    public String toString() {
        return "print(" + expressionToPrint.toString() + ")"; 
    }
}