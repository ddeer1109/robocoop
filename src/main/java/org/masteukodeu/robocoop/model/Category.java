package org.masteukodeu.robocoop.model;

public class Category {
    private final String id;
    private final String name;
    private final boolean hidden;

    public Category(String id, String name, boolean hidden) {
        this.id = id;
        this.name = name;
        this.hidden = hidden;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isHidden() {
        return hidden;
    }
}
