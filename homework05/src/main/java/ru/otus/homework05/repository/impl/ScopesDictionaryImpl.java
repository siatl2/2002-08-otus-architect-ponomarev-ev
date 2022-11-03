package ru.otus.homework05.repository.impl;

import ru.otus.homework05.repository.CommandHandler;
import ru.otus.homework05.repository.ScopesDictionary;
import ru.otus.homework05.service.Initializer;
import ru.otus.homework05.service.impl.InitializerCommandHandler;

import java.util.HashMap;
import java.util.Map;

public class ScopesDictionaryImpl implements ScopesDictionary<CommandHandler> {

    private static final String SCOPE_NOT_EXIST = "Область видимости не существует";
    private static ScopesDictionaryImpl scopesDictionary;
    private Map<String, CommandHandler> mapHandler;
    private Map<String, Thread> mapThead;
    private static CommandHandler currentScope;

    private ScopesDictionaryImpl(){
        mapHandler = new HashMap<>();
        mapThead = new HashMap<>();
        currentScope = null;
    }

    public static ScopesDictionaryImpl getInstance(){
        if (scopesDictionary == null) {
            scopesDictionary = new ScopesDictionaryImpl();
        }
        return scopesDictionary;
    }

    @Override
    public CommandHandler getCurrentScope() {
        return currentScope;
    }

    @Override
    public void createScope(String scopeName) {
        CommandHandler commandHandler = new CommandHandlerImpl();

        Thread thread = new Thread(commandHandler);
        thread.start();

        mapHandler.put(scopeName, commandHandler);
        mapThead.put(scopeName, thread);

        Initializer initializerCommandHandler = new InitializerCommandHandler();
        initializerCommandHandler.initialize(commandHandler);

        currentScope = commandHandler;
    }

    @Override
    public void changeScope(String toScopeName) {
        if (!mapHandler.containsKey(toScopeName)){
            new RuntimeException(SCOPE_NOT_EXIST);
        }

        currentScope = mapHandler.get(toScopeName);
    }
}
