package Utils.Dictionary;

import java.util.Dictionary;
import java.util.Map;

public class MyDictionary<K, V> implements IMyDictionary<K, V> {
    Map<K, V> dictionary;

    public MyDictionary(Dictionary<K, V> dictionary) {

    }

    @Override
    public void add(K key, V value) {
        this.dictionary.put(key, value);
    }

    @Override
    public V get(K key) {
        return this.dictionary.get(key);
    }
}
