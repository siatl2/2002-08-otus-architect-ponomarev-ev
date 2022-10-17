package ru.otus.homework04.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework04.domain.Vector;
import ru.otus.homework04.service.ChangableVelocity;
import ru.otus.homework04.service.Command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChangeVelocityCommandTest {
    @Mock
    private ChangableVelocity changableVelocity;
    @Captor
    ArgumentCaptor<Vector> interceptNewVelocity;

    private Command command;


    @BeforeEach
    protected void setUoTest(){
        command = new ChangeVelocityCommand(changableVelocity);
    }

    @Test
    @DisplayName("Тестирование изменения скорости, при повороте на 90 градусов, " +
                 "объект со скоростью (0, 100) меняет значения вертикальной/горизонтальной скорости")
    void testExecuteWhenAngleNinetyDegreesThenChangeValueVectorVelocity() {
        when(changableVelocity.getVelocity()).thenReturn(new Vector(100, 0));
        when(changableVelocity.getDirection()).thenReturn(1);
        when(changableVelocity.getDirectionNumbers()).thenReturn(4);
        doNothing().when(changableVelocity).setVelocity(interceptNewVelocity.capture());

        command.execute();

        Vector expectedVepocity = new Vector(0, 100);
        Vector actualVelocity = interceptNewVelocity.getValue();

        assertEquals(expectedVepocity, actualVelocity);
    }
}
