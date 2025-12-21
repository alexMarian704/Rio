package org.rio.store;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KeyValueStore {

    private final ConcurrentHashMap<String, String> store = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Deque<String>> listsStore = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Object> locks = new ConcurrentHashMap<>();

    private Object lockFor(String key) {

        return locks.computeIfAbsent(key, k -> new Object());
    }

    public void insert(String key, String value) throws WrongTypeException {

        checkStoreIsString(key);

        store.put(key, value);
    }

    public String get(String key) throws WrongTypeException {

        checkStoreIsString(key);

        return store.get(key);
    }

    public Object remove(String key) {

        if (store.containsKey(key)) {
            return store.remove(key);
        }

        return listsStore.remove(key);
    }

    public boolean exists(String key) {

        return store.containsKey(key) || listsStore.containsKey(key);
    }

    public void clear() {

        store.clear();
        listsStore.clear();
    }

    public void insertAll(Map<String, String> other) {

        store.putAll(other);
    }

    public void addFirst(String key, String value) throws WrongTypeException {

        checkStoreIsList(key);

        if (!listsStore.containsKey(key)) {
            listsStore.put(key, new ArrayDeque<>());
        }

        listsStore.get(key).addFirst(value);
    }

    public void addLast(String key, String value) throws WrongTypeException {

        checkStoreIsList(key);

        if (!listsStore.containsKey(key)) {
            listsStore.put(key, new ArrayDeque<>());
        }

        listsStore.get(key).addLast(value);
    }

    public String pollFirst(String key) throws WrongTypeException {

        checkStoreIsList(key);

        if (listsStore.containsKey(key)) {
            Deque<String> list = listsStore.get(key);
            String value = list.pollFirst();
            if (list.isEmpty()) {
                listsStore.remove(key);
            }

            return value;
        }

        return null;
    }

    public String pollLast(String key) throws WrongTypeException {

        checkStoreIsList(key);

        if (listsStore.containsKey(key)) {
            Deque<String> list = listsStore.get(key);
            String value = list.pollLast();
            if (list.isEmpty()) {
                listsStore.remove(key);
            }

            return value;
        }

        return null;
    }

    public String getFirst(String key) throws WrongTypeException {

        checkStoreIsList(key);

        if (listsStore.containsKey(key)) {
            return listsStore.get(key).peekFirst();
        }

        return null;
    }

    public String getLast(String key) throws WrongTypeException {

        checkStoreIsList(key);

        if (listsStore.containsKey(key)) {
            return listsStore.get(key).peekLast();
        }

        return null;
    }

    public Deque<String> getFullList(String key) throws WrongTypeException {

        checkStoreIsList(key);

        return listsStore.get(key);
    }

    private void checkStoreIsList(String key) throws WrongTypeException {

        if (store.containsKey(key)) {
            throw new WrongTypeException("Type is a string");
        }
    }

    private void checkStoreIsString(String key) throws WrongTypeException {

        if (listsStore.containsKey(key)) {
            throw new WrongTypeException("Type is a list");
        }
    }
}
