package ru.otus.homework03.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.homework03.service.Command;

@RequiredArgsConstructor
@Slf4j
public class WriteToLogCommand implements Command {
    private final Command command;
    private final Exception exception;

    @Override
    public void execute() {
        String message = String.format("В команде %s возникло исключение %s",
                command.getClass().getSimpleName(),
                exception.getClass().getSimpleName());

        log.info(message);
    }
}
