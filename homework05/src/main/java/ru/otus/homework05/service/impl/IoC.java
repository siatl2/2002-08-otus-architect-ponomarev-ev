package ru.otus.homework05.service.impl;

import ru.otus.homework05.domain.AnyParameter;
import ru.otus.homework05.repository.CommandHandler;
import ru.otus.homework05.repository.ScopesDictionary;
import ru.otus.homework05.repository.impl.ScopesDictionaryImpl;

import java.util.List;

public class IoC<T> {
    public static <T> T resolve(String key, List<AnyParameter> args){
        ScopesDictionary scopesDictionary = ScopesDictionaryImpl.getInstance();
        CommandHandler<T> handler = (CommandHandler<T>) scopesDictionary.getCurrentScope();
        return (T)handler.handle(key, args);
    }
}
