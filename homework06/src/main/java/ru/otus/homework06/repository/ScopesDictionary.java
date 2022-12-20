package ru.otus.homework06.repository;

public interface ScopesDictionary<T> {
    public T getCurrentScope();

    public void createScope(String scopeName);

    public void changeScope(String toScopeName);
}
