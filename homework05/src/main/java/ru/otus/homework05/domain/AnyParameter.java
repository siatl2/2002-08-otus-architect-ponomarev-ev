package ru.otus.homework05.domain;

import lombok.Getter;

import java.util.function.Function;

@Getter
public class AnyParameter {
    private Object object;
    private Function function;

    public AnyParameter(Object object) {
        this.object = object;
    }

    public AnyParameter(Function function) {
        this.function = function;
    }

}
