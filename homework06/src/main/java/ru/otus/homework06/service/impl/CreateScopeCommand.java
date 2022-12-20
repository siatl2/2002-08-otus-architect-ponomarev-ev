package ru.otus.homework06.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework06.repository.ScopesDictionary;
import ru.otus.homework06.repository.impl.ScopesDictionaryImpl;
import ru.otus.homework06.service.Command;

@RequiredArgsConstructor
public class CreateScopeCommand implements Command {
    private final Object[] args;

    @Override
    public void execute() {
        ScopesDictionary scopesDictionary = ScopesDictionaryImpl.getInstance();
        scopesDictionary.createScope(String.valueOf(args[0]));
    }
}
