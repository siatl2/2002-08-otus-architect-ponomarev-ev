package ru.otus.homework03.repository;

import ru.otus.homework03.service.Command;

public interface QueueOfCommand {
    void push(Command command);
    Command pull();
    boolean isEmpty();
}
