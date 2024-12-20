package Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Model.Exception.GenericException;
import Model.State.ProgramState;

public class Repository implements IRepository {
    ProgramState programState;
    String logFilePath;
    
    public Repository(ProgramState newProgramState) {
        this.programState = newProgramState;
    }

    public Repository(ProgramState newProgramState, String logFilePath) {
        this.programState = newProgramState;
        this.logFilePath = logFilePath;
    }

    @Override
    public ProgramState getProgramState() {
        return this.programState;
    }

    @Override
    public void logProgramState() throws GenericException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            ProgramState currentState = programState;        
            logFile.println(currentState.toString());
            logFile.println("\n------------------------\n");
        } catch (IOException e) {
            throw new GenericException("Error logging program state: " + e.getMessage());
        }
    }
}
