package Repository;

import Model.ProgramState;
import java.util.List;

public class Repository implements IRepository {
    List<ProgramState> ProgramStates;
    
    @Override
    public ProgramState getProgramState() {
        return this.ProgramStates.get(0);
    }
}
