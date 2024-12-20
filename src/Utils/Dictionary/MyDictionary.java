package Utils.Dictionary;

import java.util.HashMap;
import java.util.Map;

import Model.Exception.GenericException;

public class MyDictionary<K, V> implements IMyDictionary<K, V> {
    private HashMap<K,V> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<K,V>();
    }

    @Override
    public void add(K key, V value) {
        this.dictionary.put(key, value);
    }

    @Override
    public void update(K key, V value) {
        if (!isDefined(key)) {
            throw new GenericException("AssignmentStatement Update: key not found in dictionary: " + key);
        }
        this.dictionary.put(key, value);
    }

    @Override
    public V get(K key) {
        return this.dictionary.get(key);
    }

    @Override
    public Boolean isDefined(K key) {
        return this.dictionary.containsKey(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<K,V> entry : dictionary.entrySet()) {
            sb.append(entry.getKey())
                .append(" --> ")
                .append(entry.getValue())
                .append("\n");
        }
        if (sb.length() == 0) return "Empty symbol table";
        return sb.toString();
    }

    @Override
    public void remove(K key) {
        if (!isDefined(key)) {
            throw new GenericException("Remove: key not found in dictionary: " + key);
        }
        this.dictionary.remove(key);
    }
}
