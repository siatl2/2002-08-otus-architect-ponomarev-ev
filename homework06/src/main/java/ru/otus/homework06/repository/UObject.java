package ru.otus.homework06.repository;

public interface UObject {
    Object getProperty(String key);

    void setProperty(String key, Object value);
}
