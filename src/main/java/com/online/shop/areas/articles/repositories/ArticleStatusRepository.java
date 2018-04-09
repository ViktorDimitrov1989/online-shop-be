package com.online.shop.areas.articles.repositories;

import com.online.shop.areas.articles.entities.ArticleStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleStatusRepository extends JpaRepository<ArticleStatus, Long> {

    ArticleStatus findOneById(Long id);

}
