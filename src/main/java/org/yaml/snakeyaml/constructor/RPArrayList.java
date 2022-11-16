package org.yaml.snakeyaml.constructor;

import java.util.ArrayList;

/**
 * Somewhat recursion-proof array list
 */
public class RPArrayList<T> extends ArrayList<T> {

    protected RecursiveGuard guard = new RecursiveGuard();

    public RPArrayList(int initSize) {
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
