package ru.cifrak.news.model;

import ru.cifrak.news.entity.NewsEntity;

public class News {
    private Long id;
    private String headline;
    private String description;
    private String text;
    private Type newsType;

    public static News toModel(NewsEntity newsEntity) {
        News news = new News();

        news.id = newsEntity.getId();
        news.headline = newsEntity.getHeadline();
        news.description = newsEntity.getDescription();
        news.text = newsEntity.getText();
        news.newsType = Type.toModel(newsEntity.getNewsType());

        return news;
    }

    public News() {
    }

    public Long getId() {
        return id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Type getNewsType() {
        return newsType;
    }

    public void setNewsType(Type newsType) {
        this.newsType = newsType;
    }
}
