package ru.otus.homework02.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework02.service.Command;
import ru.otus.homework02.service.Rotable;

@RequiredArgsConstructor
public class Rotate implements Command {
    private final Rotable rotable;

    @Override
    public void execute() {
        rotable.setDirection(
                (rotable.getDirection() + rotable.getAngularVelocity()) % rotable.getDirectionNumbers()
        );
    }
}
