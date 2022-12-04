package ru.otus.homework06.adapter;

public interface Loadable extends Adapter {
    Object[] getClasses();

    void setClasses(Object[] value);
}
