package Model.Statement;

import Model.Exception.CloseFileException;
import Model.Exception.GenericException;
import Model.Expression.IExpression;
import Model.State.IFileTable;
import Model.State.IHeapTable;
import Model.State.ISymbolTable;
import Model.State.ProgramState;
import Model.Type.IType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;
import Utils.Dictionary.IMyDictionary;

public class CloseFileStatement implements IStatement {
    public final IExpression fileName;

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
            throw new CloseFileException("OpenFileStatement error: Error evaluating expression: " + e.getMessage());
        }

        StringValue fileNameStringValue;
        try {
            fileNameStringValue = (StringValue) fileNameValue;
        } catch (GenericException e) {
            throw new CloseFileException("OpenFileStatement error: expression " + this.fileName + " does not evalute to a string.");
        }

        IFileTable fileTable = state.getFileTable();
        if(!fileTable.isFileAlreadyOpen(fileNameStringValue)) {
            throw new CloseFileException("CloseFileStatement error: file " + fileNameStringValue + " is not open.");
        }

        try {
            fileTable.closeFile(fileNameStringValue);
        } catch (GenericException e) {
            throw new CloseFileException("CloseFileStatement error: " + e.getMessage());
        }

        return null;
    }

    @Override
    public String toString() {
        return "closeFile(" + fileName + ")";
    }

    @Override
    public IMyDictionary<String, IType> typeCheck(IMyDictionary<String, IType> typeEnv) throws GenericException {
        IType expressionType = fileName.typeCheck(typeEnv);
        if (expressionType.equals(new StringType())) {
            return typeEnv;
        } else throw new CloseFileException("CloseFileStatement error: filename must be a string.");
    }
}
