package com.online.shop.areas.articles.models.binding;

import java.util.List;

public class FilterArticlesBindingModel {

    private List<String> forbiddenSizes;

    private List<String> forbiddenColors;

    private List<Long> forbiddenCategories;

    private List<String> forbiddenBrands;

    private List<String> forbiddenStatuses;

    private String season;

    private String gender;

    public FilterArticlesBindingModel() {
    }

    public List<String> getForbiddenSizes() {
        return forbiddenSizes;
    }

    public void setForbiddenSizes(List<String> forbiddenSizes) {
        this.forbiddenSizes = forbiddenSizes;
    }

    public List<String> getForbiddenColors() {
        return forbiddenColors;
    }

    public void setForbiddenColors(List<String> forbiddenColors) {
        this.forbiddenColors = forbiddenColors;
    }

    public List<Long> getForbiddenCategories() {
        return forbiddenCategories;
    }

    public void setForbiddenCategories(List<Long> forbiddenCategories) {
        this.forbiddenCategories = forbiddenCategories;
    }

    public List<String> getForbiddenBrands() {
        return forbiddenBrands;
    }

    public void setForbiddenBrands(List<String> forbiddenBrands) {
        this.forbiddenBrands = forbiddenBrands;
    }

    public List<String> getForbiddenStatuses() {
        return forbiddenStatuses;
    }

    public void setForbiddenStatuses(List<String> forbiddenStatuses) {
        this.forbiddenStatuses = forbiddenStatuses;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
