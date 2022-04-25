package ru.cifrak.news.exception;

public class TypeNotFoundException extends Exception {
    public TypeNotFoundException(String message) {
        super("Не найден тип " + message);
    }
}
