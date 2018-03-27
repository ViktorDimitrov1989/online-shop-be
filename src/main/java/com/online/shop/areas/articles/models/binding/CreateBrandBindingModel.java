package com.online.shop.areas.articles.models.binding;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateBrandBindingModel {

    @NotNull(message = "Name cant be empty")
    private String name;
    @NotNull(message = "Description cant be empty")
    @Size(min = 35, message = "Description size must be at least 35 characters long.")
    private String description;

    public CreateBrandBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
