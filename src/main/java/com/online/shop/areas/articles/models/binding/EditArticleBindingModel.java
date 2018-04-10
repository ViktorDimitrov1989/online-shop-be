package com.online.shop.areas.articles.models.binding;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Set;

public class EditArticleBindingModel {

    @NotNull(message = "Article id is missing.")
    private Long id;

    @NotNull(message = "Name is missing.")
    @Size(min = 5, message = "Name must be minimum 5 characters long.")
    private String name;

    @NotNull(message = "Description is missing.")
    @Size(min = 35, message = "Description must be minimum 5 characters long.")
    private String description;

    @Min(value = 0, message = "Minimum price value must be 0.")
    @NotNull(message = "Price is missing")
    private BigDecimal price;

    @Size(min = 1, message = "There must be at least one chosen size.")
    private Set<String> sizes;

    @Size(min = 1, message = "There must be at least one chosen color.")
    private Set<String> colors;

    public EditArticleBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<String> getSizes() {
        return sizes;
    }

    public void setSizes(Set<String> sizes) {
        this.sizes = sizes;
    }

    public Set<String> getColors() {
        return colors;
    }

    public void setColors(Set<String> colors) {
        this.colors = colors;
    }
}
