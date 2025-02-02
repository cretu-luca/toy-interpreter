package Utils.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class MyTuple implements IMyTuple {
    Integer first;
    List<Integer> second;
    Integer third;
    ReentrantLock lock;

    public MyTuple(Integer first, List<Integer> second, Integer third) {
        this.first = first;
        this.second = second;
        this.third = third;
        lock = new ReentrantLock();
    }

    @Override
    public Integer getFirst() {
        return this.first;
    }

    @Override
    public void setFirst(Integer value) {
        lock.lock();
        try {
            this.first = value;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<Integer> getSecond() {
        lock.lock();
        try {
            return this.second;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void setSecond(List<Integer> value) {
        lock.lock();
        try {
            this.second = value;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public Integer getThird() {
        return this.third;
    }

    @Override
    public void setThird(Integer value) {
        lock.lock();
        try {
            this.third = value;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return "(" + first + "," + second.toString() + "," + third + ")";
    }
}
