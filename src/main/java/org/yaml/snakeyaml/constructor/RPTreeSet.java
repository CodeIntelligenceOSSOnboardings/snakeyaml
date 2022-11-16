package org.yaml.snakeyaml.constructor;

import java.util.TreeSet;

/**
 * Somewhat recursion-proof tree set
 */
public class RPTreeSet<T> extends TreeSet<T> {

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
