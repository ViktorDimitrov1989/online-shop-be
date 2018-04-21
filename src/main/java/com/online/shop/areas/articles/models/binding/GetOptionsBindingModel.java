package com.online.shop.areas.articles.models.binding;

import com.online.shop.areas.articles.enums.Gender;
import com.online.shop.areas.articles.enums.Season;

import javax.validation.constraints.NotNull;

public class GetOptionsBindingModel {

    @NotNull(message = "Gender is not valid or is missing.")
    private Gender gender;
    @NotNull(message = "Season is not valid or is missing.")
    private Season season;
    @NotNull(message = "All flag is missing.")
    private Boolean all;

    public GetOptionsBindingModel() {
    }

    public GetOptionsBindingModel(Gender gender, Season season, Boolean all) {
        this.gender = gender;
        this.season = season;
        this.all = all;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Boolean getAll() {
        return all;
    }

    public void setAll(Boolean all) {
        this.all = all;
    }
}
