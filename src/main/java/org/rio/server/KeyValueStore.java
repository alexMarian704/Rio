package org.rio.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KeyValueStore {

    private final Map<String, String> store = new ConcurrentHashMap<>();

    public void insert(String key, String value) {

        store.put(key, value);
    }

    public String get(String key) {

        return store.get(key);
    }

    public String remove(String key) {

        return store.remove(key);
    }

    public boolean exists(String key) {

        return store.containsKey(key);
    }

    public void clear() {

        store.clear();
    }
}
