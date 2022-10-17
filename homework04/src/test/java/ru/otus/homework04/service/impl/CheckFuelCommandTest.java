package ru.otus.homework04.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework04.exception.CommandException;
import ru.otus.homework04.service.Command;
import ru.otus.homework04.service.ExpendFuel;
import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CheckFuelCommandTest {
    private static final String MESSAGE_FUEL_ENOUGHT = "Топлива достаточно";
    @Mock
    private ExpendFuel expendFuel;

    private Command command;

    @BeforeEach
    void setUoTest(){
        command = new CheckFuelCommand(expendFuel);
    }

    @Test
    @DisplayName("Проверка достаточности топлива - негативная")
    public void testExecuteWhenFuelNotEnoughThenCommandException() {
        when(expendFuel.getRemainder()).thenReturn(1);
        when(expendFuel.getExpend()).thenReturn(2);

        assertThrows(CommandException.class, () -> command.execute());
    }

    @Test
    @DisplayName("Проверка достаточности топлива - позитивная")
    public void testExecuteWhenFuelEnoughThenNothingReaction() {
        when(expendFuel.getRemainder()).thenReturn(10);
        when(expendFuel.getExpend()).thenReturn(2);

        TestLogger logger = TestLoggerFactory.getTestLogger(CheckFuelCommand.class);

        command.execute();

        List<LoggingEvent> logMessages = logger.getLoggingEvents().asList();

        if (logMessages.size() > 0){
            assertEquals(MESSAGE_FUEL_ENOUGHT, logMessages.get(0).getMessage());
        } else {
            assertTrue(Boolean.FALSE);
        }

        TestLoggerFactory.clearAll();
    }
}
