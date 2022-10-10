package ru.otus.homework02.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework02.domain.Vector;
import ru.otus.homework02.service.Command;
import ru.otus.homework02.service.Movable;

@RequiredArgsConstructor
public class Move implements Command {
    private final Movable movable;

    @Override
    public void execute() {
        movable.setPosition(new Vector(
                movable.getPosition().getX() + movable.getVelocity().getX(),
                movable.getPosition().getY() + movable.getVelocity().getY())
        );
    }
}
