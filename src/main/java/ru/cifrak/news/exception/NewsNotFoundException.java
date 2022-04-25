package ru.cifrak.news.exception;

public class NewsNotFoundException extends Exception {
    public NewsNotFoundException(String message) {
        super("Не найдена новость " + message);
    }
}
