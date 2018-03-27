package com.online.shop.areas.articles.dto.articleStatus;

import java.util.Date;

public class ArticleStatusResponseDto {

    private String status;

    private Date expireDate;

    private int discount;

    public ArticleStatusResponseDto() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
}
