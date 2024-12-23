package Model.State;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Model.Exception.GenericException;
import Model.Value.StringValue;
import Utils.Dictionary.IMyDictionary;
import Utils.Dictionary.MyDictionary;

public class FileTable implements IFileTable {
    private IMyDictionary<String, BufferedReader> fileDictionary;

    public FileTable() {
        this.fileDictionary = new MyDictionary<String, BufferedReader>();
    }

    @Override
    public boolean isFileAlreadyOpen(StringValue fileName) {
        return fileDictionary.isDefined(fileName.getValue());
    }

    @Override
    public BufferedReader openFile(StringValue fileName) throws GenericException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName.getValue()));
            addOpenedFile(fileName, reader);
            return reader;
        } catch (FileNotFoundException e) {
            throw new GenericException("File " + fileName + " does not exist.");
        }
    }

    @Override
    public void addOpenedFile(StringValue fileName, BufferedReader reader) {
        this.fileDictionary.add(fileName.getValue(), reader);
    }

    @Override
    public BufferedReader getFileReader(StringValue fileName) {
        return this.fileDictionary.get(fileName.getValue());    
    }

    @Override
    public void closeFile(StringValue fileName) throws GenericException {
        String fileNameStr = fileName.getValue();
        
        if (!fileDictionary.isDefined(fileNameStr)) {
            throw new GenericException("File " + fileName + " is not open.");
        }

        BufferedReader reader = fileDictionary.get(fileNameStr);
        try {
            reader.close();
            fileDictionary.remove(fileNameStr);
        } catch (IOException e) {
            throw new GenericException("Error closing file: " + fileName);
        }
    }

    @Override
    public String toString() {
        return this.fileDictionary.toString();
    }
}
