package Model.State;

import java.io.BufferedReader;

import Model.Exception.GenericException;
import Model.Value.StringValue;

public interface IFileTable {
    boolean isFileAlreadyOpen(StringValue fileName);
    void addOpenedFile(StringValue fileName, BufferedReader reader);
    void closeFile(StringValue fileName) throws GenericException;
    BufferedReader openFile(StringValue fileName) throws GenericException;
    BufferedReader getFileReader(StringValue fileName);
}
