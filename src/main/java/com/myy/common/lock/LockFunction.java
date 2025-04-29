package com.myy.common.lock;

@FunctionalInterface
public interface LockFunction<T, R> {
    void execute();

    default R apply(T t) {
        return null;
    }
}
