package ru.otus.exceptions;

public class WrongBanknoteValueException extends Exception {
    public WrongBanknoteValueException(String message) {
        super(message);
    }
}
