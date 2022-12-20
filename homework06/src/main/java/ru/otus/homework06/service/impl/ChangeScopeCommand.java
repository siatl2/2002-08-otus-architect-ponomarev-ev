package ru.otus.homework06.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework06.repository.ScopesDictionary;
import ru.otus.homework06.repository.impl.ScopesDictionaryImpl;
import ru.otus.homework06.service.Command;

@RequiredArgsConstructor
public class ChangeScopeCommand implements Command {

    private final Object[] args;

    @Override
    public void execute() {
        ScopesDictionary scopesDictionary = ScopesDictionaryImpl.getInstance();
        scopesDictionary.changeScope(String.valueOf(args[0]));
    }
}
