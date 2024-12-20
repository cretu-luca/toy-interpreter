package Model.State;

import Model.Value.*;
import Utils.List.*;

public class Output implements IOutput {
    private IMyList<IValue> list;

    public Output() {
        this.list = new MyList<IValue>();
    }

    @Override
    public void add(IValue variableToPrint) {
        this.list.add(variableToPrint);
    }

    @Override
    public String toString() {
        return this.list.toString();
    }
}
