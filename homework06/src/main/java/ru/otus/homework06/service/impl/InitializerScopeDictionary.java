package ru.otus.homework06.service.impl;

import ru.otus.homework06.repository.IoCDictionary;
import ru.otus.homework06.repository.ScopesDictionary;
import ru.otus.homework06.service.Initializer;

public class InitializerScopeDictionary implements Initializer<ScopesDictionary<IoCDictionary>> {
    @Override
    public void initialize(ScopesDictionary<IoCDictionary> scopesDictionary) {
        scopesDictionary.createScope("default");
    }
}
