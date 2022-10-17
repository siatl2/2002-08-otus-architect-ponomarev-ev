package ru.otus.homework04.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.homework04.service.Command;
import ru.otus.homework04.service.ExpendFuel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BurnFuelCommandTest {
    @Mock
    private ExpendFuel expendFuel;

    private Command command;

    @Captor
    private ArgumentCaptor<Integer> interceptValueFuel;

    @BeforeEach
    void setUoTest(){
        command = new BurnFuelCommand(expendFuel);
    }

    @Test
    @DisplayName("Проверка уменьшения топлива")
    public void testExecuteVerifyExpendFuel() {
        when(expendFuel.getRemainder()).thenReturn(10);
        when(expendFuel.getExpend()).thenReturn(2);
        doNothing().when(expendFuel).setRemainder(interceptValueFuel.capture());

        command.execute();

        assertEquals(8, interceptValueFuel.getValue());
    }
}
