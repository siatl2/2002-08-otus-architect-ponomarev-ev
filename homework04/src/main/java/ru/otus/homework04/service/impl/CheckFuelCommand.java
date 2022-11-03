package ru.otus.homework04.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.homework04.exception.CommandException;
import ru.otus.homework04.service.Command;
import ru.otus.homework04.service.ExpendFuel;

@RequiredArgsConstructor
@Slf4j
public class CheckFuelCommand implements Command {
    private static final String MESSAGE_FUEL_ENOUGHT = "Топлива достаточно";
    private final ExpendFuel expendFuel;
    @Override
    public void execute() {
        if (expendFuel.getRemainder() < expendFuel.getExpend()){
            throw new CommandException("Топлива не хватит на перемещение");
        }
        log.info(MESSAGE_FUEL_ENOUGHT);
    }
}
