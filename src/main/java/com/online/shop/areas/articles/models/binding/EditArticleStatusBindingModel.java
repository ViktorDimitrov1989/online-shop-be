package com.online.shop.areas.articles.models.binding;

import com.online.shop.areas.articles.annotations.IsDateAfterTomorrow;
import com.online.shop.areas.articles.enums.Status;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class EditArticleStatusBindingModel {

    @NotNull(message = "Id is missing.")
    private Long id;

    private boolean available;

    @Min(value = 0, message = "Discount min value must be 0.")
    @Max(value = 100, message = "Discount max value must be 100.")
    private int discount;

    @NotNull(message = "Status is missing.")
    private Status status;

    @IsDateAfterTomorrow
    private Date expireDate;

    public EditArticleStatusBindingModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}
