package ru.otus.homework05.service.impl;

import ru.otus.homework05.repository.IoCDictionary;
import ru.otus.homework05.service.Command;
import ru.otus.homework05.service.Initializer;

public class InitializerCommandHandler implements Initializer<IoCDictionary<Command>> {
    @Override
    public void initialize(IoCDictionary<Command> handler) {
        handler.add("IoC.Register",
                RegisterCommand::new);

        handler.add("Scopes.New",
                CreateScopeCommand::new);

        handler.add("Scopes.Current",
                ChangeScopeCommand::new);

    }
}
