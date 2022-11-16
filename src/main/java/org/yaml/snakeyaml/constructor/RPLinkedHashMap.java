package org.yaml.snakeyaml.constructor;

import java.util.LinkedHashMap;

/**
 * Somewhat recursion-proof linked hash map
 */
public class RPLinkedHashMap<K,V> extends LinkedHashMap<K,V> {

    protected RecursiveGuard guard = new RecursiveGuard();

    public RPLinkedHashMap(int initSize) {
        super(initSize);
    }

    public int hashCode() {

        if (guard.get()) { return size(); }
        guard.set(true);
        try {
            return super.hashCode();
        } finally {
            guard.set(false);
        }

    }

}
