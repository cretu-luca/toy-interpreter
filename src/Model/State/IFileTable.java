package Model.State;

import java.io.BufferedReader;

import Model.Exception.GenericException;
import Model.Value.StringValue;

public interface IFileTable {
    boolean isFileAlreadyOpen(StringValue fileName);
    BufferedReader openFile(StringValue fileName) throws GenericException;
    void addOpenedFile(StringValue fileName, BufferedReader reader);
    BufferedReader getFileReader(StringValue fileName);
    void closeFile(StringValue fileName) throws GenericException;
}
