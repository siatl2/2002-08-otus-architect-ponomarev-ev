package ru.otus.homework04.service;

import ru.otus.homework04.domain.Vector;

public interface ChangableVelocity {
    Vector getVelocity();

    void setVelocity(Vector newValue);

    int getDirection();

    int getDirectionNumbers();
}
