package com.online.shop.areas.articles.models.binding;

import java.util.List;

public class FilterArticlesBindingModel {

    private List<String> chosenSizes;

    private List<String> chosenColors;

    private List<Long> chosenCategories;

    private List<String> chosenBrands;

    private List<String> chosenStatuses;

    public FilterArticlesBindingModel(List<String> chosenSizes,
                                      List<String> chosenColors,
                                      List<Long> chosenCategories,
                                      List<String> chosenBrands,
                                      List<String> chosenStatuses) {
        this.chosenSizes = chosenSizes;
        this.chosenColors = chosenColors;
        this.chosenCategories = chosenCategories;
        this.chosenBrands = chosenBrands;
        this.chosenStatuses = chosenStatuses;
    }

    public FilterArticlesBindingModel() {
    }

    public List<String> getChosenSizes() {
        return chosenSizes;
    }

    public void setChosenSizes(List<String> chosenSizes) {
        this.chosenSizes = chosenSizes;
    }

    public List<String> getChosenColors() {
        return chosenColors;
    }

    public void setChosenColors(List<String> chosenColors) {
        this.chosenColors = chosenColors;
    }

    public List<Long> getChosenCategories() {
        return chosenCategories;
    }

    public void setChosenCategories(List<Long> chosenCategories) {
        this.chosenCategories = chosenCategories;
    }

    public List<String> getChosenBrands() {
        return chosenBrands;
    }

    public void setChosenBrands(List<String> chosenBrands) {
        this.chosenBrands = chosenBrands;
    }

    public List<String> getChosenStatuses() {
        return chosenStatuses;
    }

    public void setChosenStatuses(List<String> chosenStatuses) {
        this.chosenStatuses = chosenStatuses;
    }
}
