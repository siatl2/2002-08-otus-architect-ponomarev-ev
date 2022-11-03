package ru.otus.homework03.repository.impl;

import ru.otus.homework03.domain.RecordCommandException;
import ru.otus.homework03.repository.ExceptionHandler;
import ru.otus.homework03.service.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ExceptionHandlerImpl implements ExceptionHandler {
    private static ExceptionHandlerImpl exceptionHandler;
    private Map<RecordCommandException, BiConsumer<Command, Exception>> map;

    private ExceptionHandlerImpl(){
        map = new HashMap<>();
    }

    public static ExceptionHandlerImpl getInstance(){
        if (exceptionHandler == null){
            exceptionHandler = new ExceptionHandlerImpl();
        }
        return exceptionHandler;
    }

    @Override
    public void add(Command command, Exception exception, BiConsumer<Command, Exception> executeBlock) {
        RecordCommandException record = new RecordCommandException(
                                            command.getClass().getSimpleName(),
                                            exception.getClass().getSimpleName());
        map.put(record, executeBlock);
    }

    @Override
    public void handle(Command command, Exception exception) {
        RecordCommandException record = new RecordCommandException(
                                                command.getClass().getSimpleName(),
                                                exception.getClass().getSimpleName());
        BiConsumer<Command, Exception> commandToExecute = map.get(record);
        if (commandToExecute != null){
            map.get(record).accept(command, exception);
        }
    }
}
