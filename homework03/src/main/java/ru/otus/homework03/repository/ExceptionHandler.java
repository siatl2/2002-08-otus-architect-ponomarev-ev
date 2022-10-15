package ru.otus.homework03.repository;

import ru.otus.homework03.service.Command;

import java.util.function.BiConsumer;

public interface ExceptionHandler {
    void add(Command command, Exception exception, BiConsumer<Command, Exception> executeBlock);
    void handle(Command command, Exception exception);
}
