package Model.State;

import Model.Value.*;
import Utils.List.*;

public class Output implements IOutput {
    private IMyList<IValue> list;

    @Override
    public void add(IValue variableToPrint) {
        this.list.add(variableToPrint);
    }
}
