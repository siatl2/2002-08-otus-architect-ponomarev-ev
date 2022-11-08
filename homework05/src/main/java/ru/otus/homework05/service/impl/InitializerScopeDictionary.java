package ru.otus.homework05.service.impl;

import ru.otus.homework05.repository.IoCDictionary;
import ru.otus.homework05.repository.ScopesDictionary;
import ru.otus.homework05.service.Initializer;

public class InitializerScopeDictionary implements Initializer<ScopesDictionary<IoCDictionary>> {
    @Override
    public void initialize(ScopesDictionary<IoCDictionary> scopesDictionary) {
        scopesDictionary.createScope("default");
    }
}
