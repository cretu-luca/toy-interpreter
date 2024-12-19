package Repository;

import Model.ProgramState;

public class Repository implements IRepository {
    ProgramState programState;
    
    public Repository(ProgramState programState) {
        this.programState = programState;
    }

    @Override
    public ProgramState getProgramState() {
        return this.programState;
    }
}
