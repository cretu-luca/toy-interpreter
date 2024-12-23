package Model.Statement;

import java.io.BufferedReader;

import Model.Exception.*;
import Model.Expression.*;
import Model.Value.IValue;
import Model.Value.StringValue;
import Utils.Dictionary.IMyDictionary;
import Model.State.*;
import Model.Type.IType;
import Model.Type.StringType;

public class OpenFileStatement implements IStatement {
    private final IExpression fileName;

    public OpenFileStatement(IExpression newFileName) {
        this.fileName = newFileName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        ISymbolTable symbolTable = state.getSymbolTable();
        IHeapTable heapTable = state.getHeapTable();
        
        IValue fileNameValue;
        try {
            fileNameValue = this.fileName.evaluate(symbolTable, heapTable);
        } catch (GenericException e) {
            throw new GenericException("OpenFileStatement error: Error evaluating expression: " + e.getMessage());
        }

        StringValue fileNameStringValue;
        try {
            fileNameStringValue = (StringValue) fileNameValue;
        } catch (GenericException e) {
            throw new GenericException("OpenFileStatement error: expression " + this.fileName + " does not evalute to a string.");
        }

        IFileTable fileTable = state.getFileTable();
        BufferedReader fileBufferedReader = fileTable.openFile(fileNameStringValue);
        fileTable.addOpenedFile(fileNameStringValue, fileBufferedReader);

        return null;
    }

    @Override
    public String toString() {
        return "open(" + this.fileName.toString() + ")";
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnv) throws GenericException {
        IType expressionType = fileName.typeCheck(typeEnv);
        if (expressionType.equals(new StringType())) {
            return typeEnv;
        } else throw new GenericException("OpenFileStatement error: filename must be a string");
    }
}
