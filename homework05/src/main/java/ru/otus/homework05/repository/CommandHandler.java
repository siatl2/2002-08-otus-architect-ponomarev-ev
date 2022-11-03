package ru.otus.homework05.repository;

import ru.otus.homework05.domain.AnyParameter;

import java.util.List;
import java.util.function.Function;

public interface CommandHandler<T> extends Runnable {
    void add(String commandName, Function<List<AnyParameter>,T> returnObject);
    T handle(String commandName, List<AnyParameter> args);
}
