package ru.otus.homework02.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework02.service.Rotable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RotateTest {
    private Rotate rotate;
    @Mock
    private Rotable rotable;

    @Captor
    private ArgumentCaptor<Integer> interceptNewDirection;

    private int startDirection;
    private int angularVelocity;
    private int directionNumbers;

    @BeforeEach
    public void setUpTest() {
        rotate = new Rotate(rotable);

        startDirection = 0;
        angularVelocity = (int) (360 * 1.5);
        directionNumbers = 360;
    }

    @Test
    @DisplayName("Для положения в 0 градусов, полуторный поворот вокруг оси приводит к положению в 180 градусов. " +
            "Иметь возможность изменять положение с точностью до градуса.")
    public void executeWhenGivenParametersThenReturnNewDirection() {
        int expectedEndDirection = 180;

        when(rotable.getDirection()).thenReturn(startDirection);
        when(rotable.getAngularVelocity()).thenReturn(angularVelocity);
        when(rotable.getDirectionNumbers()).thenReturn(directionNumbers);
        doNothing().when(rotable).setDirection(interceptNewDirection.capture());

        rotate.execute();

        int actualEndDirection = interceptNewDirection.getValue();

        assertEquals(expectedEndDirection, actualEndDirection);
    }

    @Test
    @DisplayName("При невозможности получения положения выдача исключения")
    public void executeWhenExceptionInGetDirectinThenException() {
        when(rotable.getDirection()).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> rotate.execute());
    }

    @Test
    @DisplayName("При невозможности получения угловой скорости выдача исключения")
    public void executeWhenExceptionInAngularVelocityThenException() {
        when(rotable.getDirection()).thenReturn(startDirection);
        when(rotable.getAngularVelocity()).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> rotate.execute());
    }

    @Test
    @DisplayName("При невозможности получения точности поворота выдача исключения")
    public void executeWhenExceptionInDirectionNumbersThenException() {
        when(rotable.getDirection()).thenReturn(startDirection);
        when(rotable.getAngularVelocity()).thenReturn(angularVelocity);
        when(rotable.getDirectionNumbers()).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> rotate.execute());
    }

    @Test
    @DisplayName("При невозможности задания положения выдача исключения")
    public void executeWhenExceptionInSetDirectinThenException() {
        when(rotable.getDirection()).thenReturn(startDirection);
        when(rotable.getAngularVelocity()).thenReturn(angularVelocity);
        when(rotable.getDirectionNumbers()).thenReturn(directionNumbers);
        doThrow(IllegalArgumentException.class).when(rotable).setDirection(interceptNewDirection.capture());

        assertThrows(IllegalArgumentException.class, () -> rotate.execute());
    }
}
