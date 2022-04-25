package ru.cifrak.news.model;

import ru.cifrak.news.entity.TypeEntity;

public class Type {
    private Long id;
    private String name;
    private String color;

    public static Type toModel(TypeEntity typeEntity) {
        Type type = new Type();
        type.id = typeEntity.getId();
        type.name = typeEntity.getName();
        type.color = typeEntity.getColor();
        return type;
    }

    public Type() {
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
}
