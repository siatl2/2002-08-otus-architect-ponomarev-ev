package ru.otus.homework04.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import ru.otus.homework04.domain.Vector;
import ru.otus.homework04.service.ChangableVelocity;
import ru.otus.homework04.service.Command;
import ru.otus.homework04.service.Rotable;
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
class MacroCommandRotateTest {
    private final String MESSAGE_ROTATE_COMPLETED = "Поворот завершен";
    private final String MESSAGE_CHANGE_VELOCITY_COMPLETED = "Изменение скорости завершено";
    private Command macroCommand;
    @Mock
    private Rotable rotable;
    @Mock
    private ChangableVelocity changableVelocity;

    @BeforeEach
    protected void setUpTest() {
        macroCommand = new MacroCommand(
                            new Command[]{
                                new RotateCommand(rotable),
                                new ChangeVelocityCommand(changableVelocity)
                            });
    }
    @Test
    @DisplayName("Проверка выполнения макрокоманды, поворот и изменение скорости")
    public void testExecuteWhenFuelEnoughtThenRunThreeCommand() {
        when(rotable.getDirection()).thenReturn(0);
        when(rotable.getDirectionNumbers()).thenReturn(360);
        when(rotable.getAngularVelocity()).thenReturn(90);
        doNothing().when(rotable).setDirection(anyInt());

        when(changableVelocity.getDirection()).thenReturn(0);
        when(changableVelocity.getDirectionNumbers()).thenReturn(360);
        when(changableVelocity.getVelocity()).thenReturn(new Vector(100, 0));
        doNothing().when(changableVelocity).setVelocity(any());

        TestLogger loggerRotate = TestLoggerFactory.getTestLogger(RotateCommand.class);
        TestLogger loggerChangeVelocity = TestLoggerFactory.getTestLogger(ChangeVelocityCommand.class);

        macroCommand.execute();

        List<LoggingEvent> logMessagesRotate = loggerRotate.getLoggingEvents().asList();
        List<LoggingEvent> logMessagesChangeVelocity = loggerChangeVelocity.getLoggingEvents().asList();

        if ((logMessagesRotate.size() > 0) &&
                (logMessagesChangeVelocity.size() > 0)) {
            assertAll(
                    () -> assertEquals(MESSAGE_ROTATE_COMPLETED, logMessagesRotate.get(0).getMessage()),
                    () -> assertEquals(MESSAGE_CHANGE_VELOCITY_COMPLETED, logMessagesChangeVelocity.get(0).getMessage())
            );
        } else {
            assertTrue(Boolean.FALSE);
        }

        TestLoggerFactory.clearAll();
    }
}
