package ru.otus.homework05.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework05.domain.AnyParameter;
import ru.otus.homework05.repository.ScopesDictionary;
import ru.otus.homework05.repository.impl.ScopesDictionaryImpl;
import ru.otus.homework05.service.Command;

import java.util.List;

@RequiredArgsConstructor
public class ChangeScopeCommand implements Command {

    private final List<AnyParameter> args;

    @Override
    public void execute() {
        ScopesDictionary scopesDictionary = ScopesDictionaryImpl.getInstance();
        scopesDictionary.changeScope(String.valueOf(args.get(0).getObject()));
    }
}
