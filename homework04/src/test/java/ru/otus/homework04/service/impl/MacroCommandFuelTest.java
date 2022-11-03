package ru.otus.homework04.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.otus.homework04.domain.Vector;
import ru.otus.homework04.exception.CommandException;
import ru.otus.homework04.service.Command;
import ru.otus.homework04.service.ExpendFuel;
import ru.otus.homework04.service.Movable;
import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MacroCommandFuelTest {
    private static final String MESSAGE_FUEL_ENOUGHT = "Топлива достаточно";
    private final String MESSAGE_MOVE_COMPLETED = "Движение завершено";
    private static final String MESSAGE_EXPEND_FUEL = "Расход топлива";

    private Command macroCommand;
    @Mock
    private Movable movable;
    @Mock
    private ExpendFuel expendFuel;

    private final TestLogger loggerCheckFuel = TestLoggerFactory.getTestLogger(CheckFuelCommand.class);
    private final TestLogger loggerMove = TestLoggerFactory.getTestLogger(MoveCommand.class);
    private final TestLogger loggerBurnFuel = TestLoggerFactory.getTestLogger(BurnFuelCommand.class);

    @BeforeEach
    protected void setUpTest() {
        macroCommand = new MacroCommand(
                            new Command[]{
                                    new CheckFuelCommand(expendFuel),
                                    new MoveCommand(movable),
                                    new BurnFuelCommand(expendFuel)});

        when(movable.getPosition()).thenReturn(new Vector(0, 0));
        when(movable.getVelocity()).thenReturn(new Vector(1, 0));
        doNothing().when(movable).setPosition(any());

        when(expendFuel.getExpend()).thenReturn(1);
        doNothing().when(expendFuel).setRemainder(anyInt());

    }
    @Test
    @DisplayName("Проверка выполнения макрокоманды, топлива достаточно")
    public void testExecuteWhenFuelEnoughtThenRunThreeCommand() {
        when(expendFuel.getRemainder()).thenReturn(10);

        macroCommand.execute();

        List<LoggingEvent> logMessagesCheckFuel = loggerCheckFuel.getLoggingEvents().asList();
        List<LoggingEvent> logMessagesMove = loggerMove.getLoggingEvents().asList();
        List<LoggingEvent> logMessagesBurnFuel = loggerBurnFuel.getLoggingEvents().asList();

        if ((logMessagesCheckFuel.size() > 0) &&
                (logMessagesMove.size() > 0) &&
                (logMessagesBurnFuel.size() > 0)) {
            assertAll(
                    () -> assertEquals(MESSAGE_FUEL_ENOUGHT, logMessagesCheckFuel.get(0).getMessage()),
                    () -> assertEquals(MESSAGE_MOVE_COMPLETED, logMessagesMove.get(0).getMessage()),
                    () -> assertEquals(MESSAGE_EXPEND_FUEL, logMessagesBurnFuel.get(0).getMessage())
            );
        } else {
            assertTrue(Boolean.FALSE);
        }
    }

    @Test
    @DisplayName("Проверка выполнения макрокоманды, топлива НЕ достаточно")
    public void testExecuteWhenFuelNotEnoughtThenException() {
        when(expendFuel.getRemainder()).thenReturn(0);

        assertThrows(CommandException.class, macroCommand::execute);
    }

    @AfterEach
    void clearLoggers(){
        TestLoggerFactory.clearAll();
    }
}
