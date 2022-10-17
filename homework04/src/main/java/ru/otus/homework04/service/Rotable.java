package ru.otus.homework04.service;

public interface Rotable {
    int getDirection();

    int getAngularVelocity();

    int getDirectionNumbers();

    void setDirection(int newDirection);
}
