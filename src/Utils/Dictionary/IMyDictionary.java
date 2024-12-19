package Utils.Dictionary;

public interface IMyDictionary<K, V> {
    void add(K key, V value);
    V get(K key);
}
