package ru.otus.homework04.service;

import ru.otus.homework04.domain.Vector;

public interface Movable {
    Vector getPosition();

    Vector getVelocity();

    void setPosition(Vector newPosition);
}
