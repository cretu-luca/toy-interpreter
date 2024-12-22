package Model.Statement;

import Model.Exception.*;
import Model.Value.*;
import Utils.Dictionary.IMyDictionary;
import Model.Expression.*;
import Model.State.*;
import Model.Type.IType;

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

        return null;
    }

    @Override
    public String toString() {
        return "print(" + expressionToPrint.toString() + ")"; 
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnv) throws GenericException {
        expressionToPrint.typeCheck(typeEnv);
        return typeEnv;
    }
}