package ru.otus.homework06.adapter;

public interface Compilable extends Adapter {
    Object[] getCompileFiles();

    void setCompileFiles(Object[] value);
}
