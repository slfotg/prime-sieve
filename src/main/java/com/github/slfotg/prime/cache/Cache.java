package com.github.slfotg.prime.cache;

import java.util.HashMap;
import java.util.function.Supplier;

public class Cache<T> extends HashMap<Object, T> {

    private static final long serialVersionUID = 1L;

    public T getOrUpdate(Object key, Supplier<T> supplier) {
        if (!containsKey(key)) {
            put(key, supplier.get());
        }
        return get(key);
    }
}
