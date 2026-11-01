package orders.storage.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Внутрішній потокобезпечний індекс. Пакет не експортується з модуля order.storage,
 * тому інші модулі не можуть використати цей клас напряму (контроль доступу модулів).
 */
public class StorageIndex<V> {

    private final Map<Integer, V> data = new ConcurrentHashMap<>();

    public void put(int key, V value) {
        data.put(key, value);
    }

    public V get(int key) {
        return data.get(key);
    }

    public List<V> values() {
        return new ArrayList<>(data.values());
    }

    public int size() {
        return data.size();
    }
}
