package ru.otus.homework02.service;

import ru.otus.homework02.domain.Vector;

public interface Movable {
    Vector getPosition();

    Vector getVelocity();

    void setPosition(Vector newPosition);
}
