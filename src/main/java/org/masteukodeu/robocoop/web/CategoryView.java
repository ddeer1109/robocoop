package org.masteukodeu.robocoop.web;

import org.masteukodeu.robocoop.model.Category;

public class CategoryView {
    public final Category category;
    public final boolean blocked;

    public CategoryView(Category category, boolean blocked) {
        this.category = category;
        this.blocked = blocked;
    }
}
