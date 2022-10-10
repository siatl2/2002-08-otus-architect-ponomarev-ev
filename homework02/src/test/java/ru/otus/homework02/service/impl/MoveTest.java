package ru.otus.homework02.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework02.domain.Vector;
import ru.otus.homework02.service.Movable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MoveTest {
    private Move move;
    @Mock
    private Movable movable;

    @Captor
    private ArgumentCaptor<Vector> interceptNewPosition;

    private Vector startPosition;
    Vector velocity;

    @BeforeEach
    public void setUpTest() {
        move = new Move(movable);

        startPosition = new Vector(12, 5);
        velocity = new Vector(-7, 3);
    }

    @Test
    @DisplayName("Для объекта, находящегося в точке (12, 5) и движущегося со скоростью (-7, 3) движение меняет положение объекта на (5, 8)")
    public void execute_whenGivenPositionAndVelocityThenCorrectNewPosition() {
        Vector expectedNewPosition = new Vector(5, 8);

        when(movable.getPosition()).thenReturn(startPosition);
        when(movable.getVelocity()).thenReturn(velocity);
        doNothing().when(movable).setPosition(interceptNewPosition.capture());

        move.execute();

        Vector actualNewPosition = interceptNewPosition.getValue();

        assertEquals(expectedNewPosition, actualNewPosition);
    }

    @Test
    @DisplayName("Попытка сдвинуть объект, у которого невозможно прочитать положение в пространстве, приводит к ошибке")
    public void execute_whenExceptionGetPositionThenException() {
        when(movable.getPosition()).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> move.execute());
    }

    @Test
    @DisplayName("Попытка сдвинуть объект, у которого невозможно прочитать значение мгновенной скорости, приводит к ошибке")
    public void execute_whenExceptionMoveThenException() {
        when(movable.getPosition()).thenReturn(startPosition);
        when(movable.getVelocity()).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> move.execute());
    }

    @Test
    @DisplayName("Попытка сдвинуть объект, у которого невозможно изменить положение в пространстве, приводит к ошибке")
    public void execute_whenExceptionSetPositionThenException() {
        when(movable.getPosition()).thenReturn(startPosition);
        when(movable.getVelocity()).thenReturn(velocity);
        doThrow(IllegalArgumentException.class).when(movable).setPosition(any());

        assertThrows(IllegalArgumentException.class, () -> move.execute());
    }
}
