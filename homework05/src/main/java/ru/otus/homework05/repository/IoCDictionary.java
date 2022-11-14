package ru.otus.homework05.repository;

import java.util.function.Function;

public interface IoCDictionary<T> extends Runnable {
    void add(String commandName, Function<Object[],T> returnObject);
    T get(String commandName, Object[] args);
}
