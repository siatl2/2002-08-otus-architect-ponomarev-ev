package ru.otus.homework06.service;

import ru.otus.homework06.adapter.Adapter;
import ru.otus.homework06.domain.Vector;

public interface Movable extends Adapter {
    Vector getPosition();

    Vector getVelocity();

    void setPosition(Vector newPosition);
}
