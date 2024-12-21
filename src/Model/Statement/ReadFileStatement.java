package Model.Statement;

import java.io.BufferedReader;
import java.io.IOException;

import Model.Exception.GenericException;
import Model.Expression.*;
import Model.Type.IType;
import Model.Type.IntType;
import Model.State.*;
import Model.Value.*;

public class ReadFileStatement implements IStatement {
    private final IExpression fileName;
    private final String variableName;

    public ReadFileStatement(IExpression newFileName, String newVariableName) {
        this.fileName = newFileName;
        this.variableName = newVariableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws GenericException {
        ISymbolTable symbolTable = state.getSymbolTable();
        IHeapTable heapTable = state.getHeapTable();

        if(!symbolTable.isDefined(variableName)) {
            throw new GenericException("ReadFileStatement error: variable " + this.variableName + " was not declared.");
        }

        IType variableNameType;
        try {
            variableNameType = symbolTable.get(variableName).getType();
        } catch (GenericException e) {
            throw new GenericException("ReadFileStatement error: " + e.getMessage());
        }

        if(!variableNameType.equals(new IntType())) {
            throw new GenericException("ReadFileStatement error: variable" + this.variableName + " is not integer.");
        }

        IFileTable fileTable = state.getFileTable();
        IValue fileNameValue = this.fileName.evaluate(symbolTable, heapTable);
        StringValue fileNameStringValue;

        try {
            fileNameStringValue = (StringValue) fileNameValue;
        } catch (ClassCastException e) {
            throw new GenericException("ReadFileStatement error: file name is not a string.");
        }

        BufferedReader fileBufferedReader;
        try {
            fileBufferedReader = fileTable.getFileReader(fileNameStringValue);
        } catch (GenericException e) {
            throw new GenericException("ReadFileStatement error: cannot read file: " + e.getMessage());
        }

        try {
            String line = fileBufferedReader.readLine();
            Integer value;
            
            if (line == null) {
                value = 0;
            } else {
                value = Integer.parseInt(line);
            }
            
            symbolTable.update(variableName, new IntValue(value));
        } catch (IOException e) {
            throw new GenericException("ReadFileStatement error: error reading from file.");
        } catch (NumberFormatException e) {
            throw new GenericException("ReadFileStatement error: invalid number format in file.");
        }
    
        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + fileName + ", " + variableName + ")";
    }   
}
