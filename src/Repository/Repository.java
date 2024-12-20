package Repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

import Model.ProgramState;

public class Repository implements IRepository {
    ProgramState programState;
    String logFilePath;
    
    public Repository(ProgramState programState) {
        this.programState = programState;
    }

    @Override
    public ProgramState getProgramState() {
        return this.programState;
    }

    @Override
    public void logProgramState() {
        logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
    }
}
