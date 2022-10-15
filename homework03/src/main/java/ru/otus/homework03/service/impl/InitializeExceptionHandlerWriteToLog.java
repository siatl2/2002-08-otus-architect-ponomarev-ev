package ru.otus.homework03.service.impl;

import ru.otus.homework03.repository.ExceptionHandler;
import ru.otus.homework03.repository.QueueOfCommand;
import ru.otus.homework03.repository.impl.QueueOfCommandImpl;
import ru.otus.homework03.service.Initializer;

public class InitializeExceptionHandlerWriteToLog implements Initializer<ExceptionHandler> {

    public void initialize(ExceptionHandler handler){
        handler.add(
                new SomeCommand(),
                new IllegalArgumentException(),
                (command, exception) -> {
                    QueueOfCommand queue = QueueOfCommandImpl.getInstance();
                    queue.push(new WriteToLogCommand(command, exception));
                    }
                );

        handler.add(
                new SomeCommand(),
                new ArithmeticException(),
                (command, exception) -> {
                    QueueOfCommand queue = QueueOfCommandImpl.getInstance();
                    queue.push(new WriteToLogCommand(command, exception));
                }
        );
    }
}
