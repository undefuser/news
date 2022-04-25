package ru.cifrak.news.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class TypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "newsType")
    private List<NewsEntity> news;

    public TypeEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<NewsEntity> getNews() {
        return news;
    }
}
