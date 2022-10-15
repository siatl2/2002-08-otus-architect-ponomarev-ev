package ru.otus.homework03.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.otus.homework03.service.Command;

import java.util.Calendar;
import java.util.GregorianCalendar;

@Slf4j
public class SomeCommand implements Command {
    @Override
    public void execute() {
        log.info("Вызов SomeCommand()");

        Calendar calendar = new GregorianCalendar();

        if (calendar.get(Calendar.MINUTE) % 2 == 0) {
            throw new IllegalArgumentException();
        } else {
            throw new ArithmeticException();
        }
    }
}
