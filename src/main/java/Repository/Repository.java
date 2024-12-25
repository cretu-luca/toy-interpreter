package Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import Model.Exception.GenericException;
import Model.State.ProgramState;

public class Repository implements IRepository {
    private List<ProgramState> programStates;
    private String logFilePath;
    
    public Repository(ProgramState newProgramState) {
        this.programStates = new ArrayList<>();
        this.programStates.add(newProgramState);
    }

    public Repository(ProgramState newProgramState, String logFilePath) {
        this.programStates = new ArrayList<>();
        this.programStates.add(newProgramState);
        this.logFilePath = logFilePath;
    }

    @Override
    public List<ProgramState> getProgramsList() {
        return this.programStates;
    }

    @Override
    public void setProgramList(List<ProgramState> newProgramStates) {
        this.programStates = newProgramStates;
    }

    @Override
    public void logProgramState(ProgramState programState) throws GenericException {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            ProgramState currentState = programState;        
            logFile.println(currentState);
        } catch (IOException e) {
            throw new GenericException("Error logging program state: " + e.getMessage());
        }
    }

    @Override
    public ProgramState getProgramById(int id) {
        return programStates.stream().filter(p -> p.getID() == id).findFirst().orElse(null);
    }
}
