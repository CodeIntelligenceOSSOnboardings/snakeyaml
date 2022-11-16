package org.yaml.snakeyaml.constructor;

import java.util.TreeMap;

/**
 * Somewhat recursion-proof tree map
 */
public class RPTreeMap<K, V> extends TreeMap<K, V> {

    protected RecursiveGuard guard = new RecursiveGuard();

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
