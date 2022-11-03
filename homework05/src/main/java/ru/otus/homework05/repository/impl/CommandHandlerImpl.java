package ru.otus.homework05.repository.impl;

import ru.otus.homework05.domain.AnyParameter;
import ru.otus.homework05.repository.CommandHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class CommandHandlerImpl<T> implements CommandHandler<T> {
    private ThreadLocal<Map<String, Function<List<AnyParameter>,T>>> map = new ThreadLocal<>();

    public CommandHandlerImpl(){
        map.set(new HashMap<>());
    }

    @Override
    public void run() { }

    @Override
    public void add(String commandName, Function<List<AnyParameter>,T> returnObject) {
        Map<String, Function<List<AnyParameter>,T>> localMap = map.get();
        localMap.put(commandName, returnObject);
        map.set(localMap);
    }

    @Override
    public T handle(String commandName, List<AnyParameter> args) {
        return map.get().get(commandName).apply(args);
    }
}
