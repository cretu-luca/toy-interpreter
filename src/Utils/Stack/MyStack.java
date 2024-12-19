package Utils.Stack;

import java.util.Collection;
import java.util.Stack;

public class MyStack<T> implements IMyStack<T> {
    private Stack<T> stack;

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T t) {
        this.stack.push(t);
    }
}
