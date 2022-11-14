package ru.otus.homework05.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework05.repository.IoCDictionary;
import ru.otus.homework05.repository.ScopesDictionary;
import ru.otus.homework05.repository.impl.ScopesDictionaryImpl;
import ru.otus.homework05.service.Command;

import java.util.function.Function;

@RequiredArgsConstructor
public class RegisterCommand implements Command {
    private final Object[] args;

    @Override
    public void execute() {
        ScopesDictionary<IoCDictionary> scopesDictionary = ScopesDictionaryImpl.getInstance();
        IoCDictionary handler = scopesDictionary.getCurrentScope();
        handler.add(String.valueOf(args[0])
                  , (Function<Object[], Object>) args[1]);
    }
}
