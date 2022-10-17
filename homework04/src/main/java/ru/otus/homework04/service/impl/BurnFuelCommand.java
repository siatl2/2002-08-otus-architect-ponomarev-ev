package ru.otus.homework04.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.homework04.service.Command;
import ru.otus.homework04.service.ExpendFuel;

@RequiredArgsConstructor
@Slf4j
public class BurnFuelCommand implements Command {
    private static final String MESSAGE_EXPEND_FUEL = "Расход топлива";
    private final ExpendFuel expendFuel;
    @Override
    public void execute() {
        expendFuel.setRemainder(expendFuel.getRemainder() - expendFuel.getExpend());
        log.info(MESSAGE_EXPEND_FUEL);
    }
}
