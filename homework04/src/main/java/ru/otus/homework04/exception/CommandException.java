package ru.otus.homework04.exception;

public class CommandException extends RuntimeException {
    public CommandException(String message){
        super(message);
    }
}
