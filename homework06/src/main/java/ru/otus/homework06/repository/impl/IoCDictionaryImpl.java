package ru.otus.homework06.repository.impl;

import ru.otus.homework06.repository.IoCDictionary;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class IoCDictionaryImpl<T> implements IoCDictionary<T> {
    private ThreadLocal<Map<String, Function<Object[], T>>> map = new ThreadLocal<>();

    public IoCDictionaryImpl() {
        map.set(new HashMap<>());
    }

    @Override
    public void run() {
    }

    @Override
    public void add(String commandName, Function<Object[], T> returnObject) {
        Map<String, Function<Object[], T>> localMap = map.get();
        localMap.put(commandName, returnObject);
        map.set(localMap);
    }

    @Override
    public T get(String commandName, Object[] args) {
        var command = map.get().get(commandName);
        if (command == null) {
            return null;
        } else {
            return map.get().get(commandName).apply(args);
        }
    }
}
