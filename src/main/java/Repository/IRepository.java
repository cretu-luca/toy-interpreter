package Repository;

import java.util.List;

import Model.State.ProgramState;

public interface IRepository {
    List<ProgramState> getProgramsList();
    void setProgramList(List<ProgramState> newProgramStates);
    void logProgramState(ProgramState programState);
    ProgramState getProgramById(int id);
}