package ru.otus.homework03.domain;

import lombok.Value;

@Value
public class RecordCommandException {
    String commandName;
    String exceptionName;
}
