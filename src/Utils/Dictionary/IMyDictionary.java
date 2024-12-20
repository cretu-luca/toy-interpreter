package Utils.Dictionary;

public interface IMyDictionary<K, V> {
    void add(K key, V value);
    void update(K key, V value);
    void remove(K key);
    V get(K key);
    Boolean isDefined(K key);
}
