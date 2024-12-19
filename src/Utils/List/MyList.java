package Utils.List;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IMyList<T> {
    private List<T> list;

    public MyList() {
        this.list = new ArrayList<>();
    }

    @Override
    public void add(T element) {
        this.list.add(element);
    }

    @Override
    public T get(int index) {
        return this.list.get(index);
    }
}
