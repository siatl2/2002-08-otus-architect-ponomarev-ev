package ru.otus.homework03.repository.impl;

import ru.otus.homework03.repository.QueueOfCommand;
import ru.otus.homework03.service.Command;

import java.util.LinkedList;
import java.util.Queue;

public class QueueOfCommandImpl implements QueueOfCommand {
    private static QueueOfCommandImpl queueOfCommandImpl;
    private Queue<Command> queue;

    private QueueOfCommandImpl(){
        queue = new LinkedList<>();
    }

    public static QueueOfCommandImpl getInstance(){
        if (queueOfCommandImpl == null){
            queueOfCommandImpl = new QueueOfCommandImpl();
        }
        return queueOfCommandImpl;
    }

    @Override
    public void push(Command command){
        queue.add(command);
    }

    @Override
    public Command pull(){
        return queue.poll();
    }

    @Override
    public boolean isEmpty(){
        return queue.peek() == null;
    }
}
