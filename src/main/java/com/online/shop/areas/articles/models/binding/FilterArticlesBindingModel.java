package com.online.shop.areas.articles.models.binding;

import java.util.List;

public class FilterArticlesBindingModel {

    private List<String> selectedSizes;

    private List<String> selectedColors;

    private List<Long> selectedCategories;

    private List<String> selectedBrands;

    private List<String> selectedStatuses;

    private String chosenSeason;

    private String chosenGender;

    public FilterArticlesBindingModel() {
    }

    public List<String> getSelectedSizes() {
        return selectedSizes;
    }

    public void setSelectedSizes(List<String> selectedSizes) {
        this.selectedSizes = selectedSizes;
    }

    public List<String> getSelectedColors() {
        return selectedColors;
    }

    public void setSelectedColors(List<String> selectedColors) {
        this.selectedColors = selectedColors;
    }

    public List<Long> getSelectedCategories() {
        return selectedCategories;
    }

    public void setSelectedCategories(List<Long> selectedCategories) {
        this.selectedCategories = selectedCategories;
    }

    public List<String> getSelectedBrands() {
        return selectedBrands;
    }

    public void setSelectedBrands(List<String> selectedBrands) {
        this.selectedBrands = selectedBrands;
    }

    public List<String> getSelectedStatuses() {
        return selectedStatuses;
    }

    public void setSelectedStatuses(List<String> selectedStatuses) {
        this.selectedStatuses = selectedStatuses;
    }

    public String getChosenSeason() {
        return chosenSeason;
    }

    public void setChosenSeason(String chosenSeason) {
        this.chosenSeason = chosenSeason;
    }

    public String getChosenGender() {
        return chosenGender;
    }

    public void setChosenGender(String chosenGender) {
        this.chosenGender = chosenGender;
    }
}
