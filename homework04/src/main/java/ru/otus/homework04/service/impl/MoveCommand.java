package ru.otus.homework04.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.homework04.domain.Vector;
import ru.otus.homework04.service.Command;
import ru.otus.homework04.service.Movable;

@RequiredArgsConstructor
@Slf4j
public class MoveCommand implements Command {
    private final String MESSAGE_MOVE_COMPLETED = "Движение завершено";
    private final Movable movable;

    @Override
    public void execute() {
        movable.setPosition(new Vector(
                movable.getPosition().getX() + movable.getVelocity().getX(),
                movable.getPosition().getY() + movable.getVelocity().getY())
        );
        log.info(MESSAGE_MOVE_COMPLETED);
    }
}
