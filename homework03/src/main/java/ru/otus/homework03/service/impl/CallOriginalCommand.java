package ru.otus.homework03.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework03.service.Command;

@RequiredArgsConstructor
public class CallOriginalCommand implements Command {
    private final Command command;

    @Override
    public void execute() {
        command.execute();
    }
}
