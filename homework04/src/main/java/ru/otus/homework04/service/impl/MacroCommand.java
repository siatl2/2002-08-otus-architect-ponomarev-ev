package ru.otus.homework04.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework04.service.Command;

import java.util.Arrays;

@RequiredArgsConstructor
public class MacroCommand implements Command {
    private final Command[] commands;

    @Override
    public void execute() {
        Arrays.stream(commands).forEach(Command::execute);
    }
}
