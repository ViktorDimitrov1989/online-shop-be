package com.online.shop.Schedulers;

import com.online.shop.areas.articles.entities.Article;
import com.online.shop.areas.articles.entities.ArticleStatus;
import com.online.shop.areas.articles.enums.Status;
import com.online.shop.areas.articles.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class ExpireDateStatusScheduler {

    private final ArticleRepository articleRepository;

    @Autowired
    public ExpireDateStatusScheduler(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void updateStatuses() {
        List<Article> allArticles = this.articleRepository.findAll();

        for (Article article : allArticles) {
            Date today = new Date();
            Date expireDate = article.getStatus().getExpireDate();

            if(expireDate == null){
                continue;
            }

            Calendar todayCal = Calendar.getInstance();
            Calendar expireDateCal = Calendar.getInstance();
            todayCal.setTime(today);
            expireDateCal.setTime(expireDate);

            if(todayCal.after(expireDateCal)){
                ArticleStatus articleStatus = new ArticleStatus(
                        article.getStatus().getId(),
                        Status.REGULAR,
                        null,
                        0,
                        article.getStatus().isAvailable());

                article.setStatus(articleStatus);

                this.articleRepository.save(article);
            }

        }

    }

}
