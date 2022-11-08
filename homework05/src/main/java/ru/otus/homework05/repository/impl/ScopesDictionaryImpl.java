package ru.otus.homework05.repository.impl;

import ru.otus.homework05.repository.IoCDictionary;
import ru.otus.homework05.repository.ScopesDictionary;
import ru.otus.homework05.service.Initializer;
import ru.otus.homework05.service.impl.InitializerCommandHandler;

import java.util.HashMap;
import java.util.Map;

public class ScopesDictionaryImpl implements ScopesDictionary<IoCDictionary> {

    private static final String SCOPE_NOT_EXIST = "Область видимости не существует";
    private static ScopesDictionaryImpl scopesDictionary;
    private final Map<String, IoCDictionary> mapHandler;
    private final Map<String, Thread> mapThead;
    private static IoCDictionary currentScope;

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
    public IoCDictionary getCurrentScope() {
        return currentScope;
    }

    @Override
    public void createScope(String scopeName) {
        IoCDictionary ioCDictionary = new IoCDictionaryImpl();

        Thread thread = new Thread(ioCDictionary);
        thread.start();

        mapHandler.put(scopeName, ioCDictionary);
        mapThead.put(scopeName, thread);

        initializeCommandHandlerInScope(ioCDictionary);

        currentScope = ioCDictionary;
    }

    @Override
    public void changeScope(String toScopeName) {
        if (!mapHandler.containsKey(toScopeName)){
            new RuntimeException(SCOPE_NOT_EXIST);
        }

        currentScope = mapHandler.get(toScopeName);
    }

    private void initializeCommandHandlerInScope(IoCDictionary ioCDictionary){
        Initializer initializerCommandHandler = new InitializerCommandHandler();
        initializerCommandHandler.initialize(ioCDictionary);
    }
}
