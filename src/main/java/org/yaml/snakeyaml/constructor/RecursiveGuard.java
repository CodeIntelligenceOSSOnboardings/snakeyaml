package org.yaml.snakeyaml.constructor;

public class RecursiveGuard extends ThreadLocal<Boolean> {

    @Override
    protected Boolean initialValue() {
        return Boolean.FALSE;
    }

}
