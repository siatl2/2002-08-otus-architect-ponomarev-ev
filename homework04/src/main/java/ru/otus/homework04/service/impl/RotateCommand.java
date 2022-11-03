package ru.otus.homework04.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.homework04.service.Command;
import ru.otus.homework04.service.Rotable;

@RequiredArgsConstructor
@Slf4j
public class RotateCommand implements Command {
    private final String MESSAGE_ROTATE_COMPLETED = "Поворот завершен";
    private final Rotable rotable;

    @Override
    public void execute() {
        rotable.setDirection(
                (rotable.getDirection() + rotable.getAngularVelocity()) % rotable.getDirectionNumbers()
        );
        log.info(MESSAGE_ROTATE_COMPLETED);
    }
}
