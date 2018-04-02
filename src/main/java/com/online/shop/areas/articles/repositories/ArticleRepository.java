package com.online.shop.areas.articles.repositories;

import com.online.shop.areas.articles.entities.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findAllByBrandNameInAndCategoryIdInAndSizesNameInAndColorsNameIn(List<String> brandNames,
                                                                                   List<Long> categoryIds,
                                                                                   List<String> sizes,
                                                                                   List<String> colors,
                                                                                   Pageable pageable);

}
