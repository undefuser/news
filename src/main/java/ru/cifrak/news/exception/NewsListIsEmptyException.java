package ru.cifrak.news.exception;

public class NewsListIsEmptyException extends Exception {
    public NewsListIsEmptyException() {
        super("Список новостей пуст!");
    }
}
