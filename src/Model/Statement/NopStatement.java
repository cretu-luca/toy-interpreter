package Model.Statement;

import Model.State.ProgramState;

public class NopStatement implements IStatement {
    @Override
    public ProgramState execute(ProgramState state) {
        return state;
    }
}
