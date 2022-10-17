package ru.otus.homework04.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.homework04.domain.Vector;
import ru.otus.homework04.service.ChangableVelocity;
import ru.otus.homework04.service.Command;

@RequiredArgsConstructor
@Slf4j
public class ChangeVelocityCommand implements Command {
    private final String MESSAGE_CHANGE_VELOCITY_COMPLETED = "Изменение скорости завершено";
    private final ChangableVelocity changableVelocity;

    @Override
    public void execute() {
        Vector currentVelocity = changableVelocity.getVelocity();
        int angleDegress = (int)(360 * changableVelocity.getDirection() /
                                        changableVelocity.getDirectionNumbers());
        double angle = Math.toRadians(angleDegress);
        double angleVelocity = Math.sqrt(Math.pow(currentVelocity.getX(),2) +
                                Math.pow(currentVelocity.getY(),2));

        Vector newVelocity = new Vector(
                (int)(angleVelocity * Math.cos(angle)),
                (int)(angleVelocity * Math.sin(angle))
        );

        changableVelocity.setVelocity(newVelocity);
        log.info(MESSAGE_CHANGE_VELOCITY_COMPLETED);
    }
}
