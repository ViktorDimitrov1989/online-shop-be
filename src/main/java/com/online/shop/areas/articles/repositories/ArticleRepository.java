package com.online.shop.areas.articles.repositories;

import com.online.shop.areas.articles.entities.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
