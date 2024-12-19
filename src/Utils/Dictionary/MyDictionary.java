package Utils.Dictionary;

import java.util.Dictionary;
import java.util.Map;

import Model.Exception.GenericException;

public class MyDictionary<K, V> implements IMyDictionary<K, V> {
    Map<K, V> dictionary;

    public MyDictionary(Dictionary<K, V> dictionary) {

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
}
