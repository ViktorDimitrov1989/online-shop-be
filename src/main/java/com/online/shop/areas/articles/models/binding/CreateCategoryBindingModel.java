package com.online.shop.areas.articles.models.binding;
import com.online.shop.areas.articles.annotations.IsMinAndMaxAgeCorrect;

import javax.validation.constraints.NotNull;

@IsMinAndMaxAgeCorrect
public class CreateCategoryBindingModel {

    @NotNull(message = "Name cant be empty")
    private String name;
    @NotNull(message = "Min age cant be empty")
    private int minAge;
    @NotNull(message = "Max age cant be empty")
    private int maxAge;
    @NotNull(message = "Gender age cant be empty")
    private String gender;
    @NotNull(message = "Season cant be empty")
    private String season;

    public CreateCategoryBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}
