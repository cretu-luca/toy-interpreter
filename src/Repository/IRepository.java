package Repository;

import Model.State.ProgramState;

public interface IRepository {
    ProgramState getProgramState();
    void logProgramState();
}