package Repository;

import Model.ProgramState;

public interface IRepository {
    ProgramState getProgramState();
    void logProgramState();
}