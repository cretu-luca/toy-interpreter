package Model.Expression;

import Model.Value.IValue;
import Utils.Dictionary.IMyDictionary;

public interface IExpression {
    IValue evaluate(IMyDictionary<String, IValue> symbolTable);
}
