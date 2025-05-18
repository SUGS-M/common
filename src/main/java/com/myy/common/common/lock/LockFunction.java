package com.myy.common.common.lock;

@FunctionalInterface
public interface LockFunction<T, R> {
    void execute();

    default R apply(T t) {
        return null;
    }
}
