package org.yaml.snakeyaml.constructor;

import java.util.LinkedHashSet;

/**
 * Somewhat recursion-proof linked hash set
 */
public class RPLinkedHashSet<T> extends LinkedHashSet<T> {

    protected RecursiveGuard guard = new RecursiveGuard();

    public RPLinkedHashSet(int initSize) {
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
