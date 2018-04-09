package com.online.shop.areas.articles.models.binding;
import com.amazonaws.annotation.NotThreadSafe;
import com.online.shop.areas.articles.annotations.IsDateAfterTomorrow;
import com.online.shop.areas.articles.enums.Gender;
import com.online.shop.areas.articles.enums.Season;
import com.online.shop.areas.articles.enums.Status;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

//TODO: make validations
public class CreateArticleBindingModel {

    @NotNull(message = "Name is missing.")
    @Size(min = 5, message = "Name must be minimum 5 characters long.")
    private String name;

    @NotNull(message = "Description is missing.")
    @Size(min = 35, message = "Description must be minimum 5 characters long.")
    private String description;

    @NotNull(message = "Category is missing.")
    private Long category;

    @NotNull(message = "Brand is missing.")
    private String brandName;

    @NotNull(message = "Status is missing.")
    private Status status;

    @NotNull(message = "Date is missing.")
    @IsDateAfterTomorrow
    private Date expireDate;

    @NotNull(message = "Discount is missing.")
    @Min(value = 0, message = "Minimum discount value must be 0.")
    @Max(value = 100, message = "Max discount value must be 100.")
    private int discount;

    @Min(value = 0, message = "Minimum price value must be 0.")
    @NotNull(message = "Price is missing")
    private BigDecimal price;

    private boolean isAvailable;

    @Size(min = 1, message = "There must be at least one chosen size.")
    private Set<String> sizes;

    @Size(min = 1, message = "There must be at least one chosen color.")
    private Set<String> colors;

    public CreateArticleBindingModel() {
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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
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

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
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

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }
}
