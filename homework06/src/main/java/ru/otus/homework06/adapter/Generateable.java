package ru.otus.homework06.adapter;

public interface Generateable extends Adapter {
    Object[] getSourceFiles();

    void setSourceFiles(Object[] value);
}
