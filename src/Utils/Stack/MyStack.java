package Utils.Stack;

import java.util.Collection;
import java.util.Stack;

public class MyStack<T> implements IMyStack<T> {
    private Stack<T> stack;

    public MyStack() {
        this.stack = new Stack<T>();
    }

    @Override
    public T pop() {
        return stack.pop();
    }

    @Override
    public void push(T t) {
        this.stack.push(t);
    }
    
    @Override
    public Boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T elem : stack) {
            sb.append(elem.toString()).append("\n");
        }
        if (sb.length() == 0) return "Empty stack";
        return sb.toString();
    }
}
