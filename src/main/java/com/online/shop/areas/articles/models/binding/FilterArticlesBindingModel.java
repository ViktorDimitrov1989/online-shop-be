package com.online.shop.areas.articles.models.binding;

import com.online.shop.areas.articles.enums.Gender;
import com.online.shop.areas.articles.enums.Season;
import com.online.shop.areas.articles.enums.Status;

import javax.validation.constraints.NotNull;
import java.util.List;

public class FilterArticlesBindingModel {

    @NotNull(message = "Selected sizes are missing.")
    private List<String> selectedSizes;

    @NotNull(message = "Selected colors are missing.")
    private List<String> selectedColors;

    @NotNull(message = "Selected categories are missing.")
    private List<Long> selectedCategories;

    @NotNull(message = "Selected brands are missing.")
    private List<String> selectedBrands;

    @NotNull(message = "Selected statuses are missing.")
    private List<Status> selectedStatuses;

    @NotNull(message = "Selected season is missing.")
    private Season chosenSeason;

    @NotNull(message = "Selected gender is missing.")
    private Gender chosenGender;

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

    public List<Status> getSelectedStatuses() {
        return selectedStatuses;
    }

    public void setSelectedStatuses(List<Status> selectedStatuses) {
        this.selectedStatuses = selectedStatuses;
    }

    public Season getChosenSeason() {
        return chosenSeason;
    }

    public void setChosenSeason(Season chosenSeason) {
        this.chosenSeason = chosenSeason;
    }

    public Gender getChosenGender() {
        return chosenGender;
    }

    public void setChosenGender(Gender chosenGender) {
        this.chosenGender = chosenGender;
    }
}
