package ru.otus.homework05.service.impl;

import ru.otus.homework05.repository.CommandHandler;
import ru.otus.homework05.service.Initializer;

import java.util.List;

public class InitializerCommandHandler implements Initializer<CommandHandler> {
    @Override
    public void initialize(CommandHandler handler) {
        handler.add("IoC.Register",
                (args) ->{
                    return new RegisterCommand((List)args);
                });

        handler.add("Scopes.New",
                (args) ->{
                    return new CreateScopeCommand((List)args);
                });

        handler.add("Scopes.Current",
                (args) ->{
                    return new ChangeScopeCommand((List)args);
                });

    }
}
