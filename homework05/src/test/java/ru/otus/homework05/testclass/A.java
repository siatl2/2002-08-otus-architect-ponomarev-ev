package ru.otus.homework05.testclass;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class A {

    public static final String MESSAGE_CONSTUCTOR_NO_ARGS = "Вызван конструктор без параметров";
    public static final String MESSAGE_CONSTRUCTOR_TWO_ARGS = "Вызван конструктор с двумя параметрами";

    public A(){
        log.info(MESSAGE_CONSTUCTOR_NO_ARGS);
    }

    public A(Object a1, Object a2){
        log.info(MESSAGE_CONSTRUCTOR_TWO_ARGS);
    }
}
