package Model.State;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import Model.Exception.GenericException;
import Model.Value.StringValue;
import Utils.Dictionary.IMyDictionary;

public class FileTable implements IFileTable {
    private IMyDictionary<String, BufferedReader> fileDictionary;

    @Override
    public boolean isFileAlreadyOpen(StringValue fileName) {
        return fileDictionary.isDefined(fileName.getValue());
    }

    @Override
    public BufferedReader openFile(StringValue fileName) throws GenericException {
        try {
            return new BufferedReader(new FileReader(fileName.getValue()));
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
}
