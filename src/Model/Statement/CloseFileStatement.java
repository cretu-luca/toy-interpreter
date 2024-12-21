package Model.Statement;

import Model.Exception.GenericException;
import Model.Expression.IExpression;
import Model.State.IFileTable;
import Model.State.IHeapTable;
import Model.State.ISymbolTable;
import Model.State.ProgramState;
import Model.Value.IValue;
import Model.Value.StringValue;

public class CloseFileStatement implements IStatement {
    public IExpression fileName;

    public CloseFileStatement(IExpression newFileName) {
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
        if(!fileTable.isFileAlreadyOpen(fileNameStringValue)) {
            throw new GenericException("CloseFileStatement error: file " + fileNameStringValue + " is not open.");
        }

        try {
            fileTable.closeFile(fileNameStringValue);
        } catch (GenericException e) {
            throw new GenericException("CloseFileStatement error: " + e.getMessage());
        }

        return state;
    }

    @Override
    public String toString() {
        return "closeFile(" + fileName + ")";
    }
}
