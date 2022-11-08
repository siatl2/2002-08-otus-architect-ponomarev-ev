package ru.otus.homework05.service.impl;

import ru.otus.homework05.repository.IoCDictionary;
import ru.otus.homework05.repository.impl.ScopesDictionaryImpl;

public class IoC {
    public static <T> T resolve(String key, Object[] args){
        ScopesDictionaryImpl scopesDictionary = ScopesDictionaryImpl.getInstance();
        IoCDictionary<T> handler = scopesDictionary.getCurrentScope();
        return handler.get(key, args);
    }
}
