package Model.Statement;

import java.beans.Expression;

import Model.ProgramState;
import Model.Exception.GenericException;
import Utils.Dictionary.IMyDictionary;
import Utils.List.IMyList;

public class PrintStatement implements IStatement {
    Expression expressionToPrint;
    
    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        IMyList<Value> output = state.getOutput();
        IMyDictionary<String, Value> symbolTable = state.getSymbolTable();

        Value value = expressionToPrint.evaluate(symbolTable);
        output.add(value);

        return state;
    }

    @Override
    public String toString() {
        return "print(" + expressionToPrint.toString() + ")"; 
    }
}