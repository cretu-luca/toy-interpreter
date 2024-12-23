package Model.Value;

import Model.Type.*;

public interface IValue {
    IType getType();
    IValue deepCopy();
}
