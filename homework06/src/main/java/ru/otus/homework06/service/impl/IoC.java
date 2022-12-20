package ru.otus.homework06.service.impl;

import ru.otus.homework06.repository.IoCDictionary;
import ru.otus.homework06.repository.impl.ScopesDictionaryImpl;

public class IoC {
    public static <T> T resolve(String key, Object[] args) {
        ScopesDictionaryImpl scopesDictionary = ScopesDictionaryImpl.getInstance();
        IoCDictionary<T> handler = scopesDictionary.getCurrentScope();
        return handler.get(key, args);
    }
}
