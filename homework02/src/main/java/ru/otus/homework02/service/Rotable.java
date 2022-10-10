package ru.otus.homework02.service;

public interface Rotable {
    int getDirection();

    int getAngularVelocity();

    int getDirectionNumbers();

    void setDirection(int newDirection);
}
