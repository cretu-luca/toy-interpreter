package Utils.Stack;

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
        if(stack.isEmpty()) {
            return "";
        }
    
        StringBuilder sb = new StringBuilder();
        
        T nextStatement = stack.pop();
        sb.append(nextStatement.toString()).append("\n").append(this.toString());
        this.stack.push(nextStatement);

        return sb.toString();
    }
}
