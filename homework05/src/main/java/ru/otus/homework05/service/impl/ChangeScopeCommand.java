package ru.otus.homework05.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework05.repository.ScopesDictionary;
import ru.otus.homework05.repository.impl.ScopesDictionaryImpl;
import ru.otus.homework05.service.Command;

@RequiredArgsConstructor
public class ChangeScopeCommand implements Command {

    private final Object[] args;

    @Override
    public void execute() {
        ScopesDictionary scopesDictionary = ScopesDictionaryImpl.getInstance();
        scopesDictionary.changeScope(String.valueOf(args[0]));
    }
}
