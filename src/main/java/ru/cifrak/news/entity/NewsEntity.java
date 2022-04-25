package ru.cifrak.news.entity;

import javax.persistence.*;

@Entity
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String headline;
    private String description;
    private String text;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeEntity newsType;

    public NewsEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String name) {
        this.headline = name;
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

    public TypeEntity getNewsType() {
        return newsType;
    }

    public void setNewsType(TypeEntity newsType) {
        this.newsType = newsType;
    }
}
