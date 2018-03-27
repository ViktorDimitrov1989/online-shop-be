package com.online.shop.areas.articles.entities;
import com.online.shop.areas.articles.enums.Status;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "article_statuses")
public class ArticleStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "expire_date")
    private Date expireDate;

    @Column(name = "discount")
    private int discount;

    public ArticleStatus() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
