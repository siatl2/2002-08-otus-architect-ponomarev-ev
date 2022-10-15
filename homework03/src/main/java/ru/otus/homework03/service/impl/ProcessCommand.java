package ru.otus.homework03.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework03.repository.ExceptionHandler;
import ru.otus.homework03.repository.QueueOfCommand;
import ru.otus.homework03.service.Command;
import ru.otus.homework03.service.Processor;

@RequiredArgsConstructor
public class ProcessCommand implements Processor {
    private final QueueOfCommand queue;
    private final ExceptionHandler handler;

    @Override
    public void process(){
        while (!queue.isEmpty()){
            Command command = queue.pull();
            try{
                command.execute();
            } catch (Exception e){
                handler.handle(command, e);
            }
        }
    }
}
