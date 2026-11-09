package ru.yozora.module;

public enum Category {
    OPTIMIZATION("Оптимизация"),
    VISUAL("Визуал"),
    HUD("HUD"),
    MOVEMENT("Движение");

    private final String title;

    Category(String title) {
        this.title = title;
    }

    public String title() {
        return title;
    }
}
