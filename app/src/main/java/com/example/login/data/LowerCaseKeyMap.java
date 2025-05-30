package com.example.login.data;

import java.util.HashMap;

public class LowerCaseKeyMap<K, V> extends HashMap<String, V> {
    private static final long serialVersionUID = 2342957047231777032L;

    public V put(String key, V value) {
        return super.put(key.toLowerCase(), value);
    }

    public V putIfAbsent(String key, V value) {
        return super.putIfAbsent(key.toLowerCase(), value);
    }
}
