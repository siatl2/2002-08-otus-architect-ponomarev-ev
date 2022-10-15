package ru.otus.homework03.service.impl;

import ru.otus.homework03.repository.QueueOfCommand;
import ru.otus.homework03.service.Initializer;

public class InitializeQueueOfCommands implements Initializer<QueueOfCommand> {
    @Override
    public void initialize(QueueOfCommand queue){
        queue.push(new SomeCommand());
    }
}
