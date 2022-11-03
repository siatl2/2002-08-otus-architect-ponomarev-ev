package ru.otus.homework05.service.impl;

import ru.otus.homework05.repository.ScopesDictionary;
import ru.otus.homework05.service.Initializer;

public class InitializerScopeDictionary implements Initializer<ScopesDictionary> {
    @Override
    public void initialize(ScopesDictionary scopesDictionary) {
        scopesDictionary.createScope("default");
    }
}
