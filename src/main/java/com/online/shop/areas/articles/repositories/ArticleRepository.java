package com.online.shop.areas.articles.repositories;

import com.online.shop.areas.articles.entities.*;
import com.online.shop.areas.articles.enums.Gender;
import com.online.shop.areas.articles.enums.Season;
import com.online.shop.areas.articles.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findAllDistinctBySizesInAndColorsInAndBrandInAndCategoryInAndCategorySeasonAndCategoryGenderAndStatusStatusIn(
            Collection<Size> sizes,
            Collection<Color> colors,
            Collection<Brand> brands,
            Collection<Category> categories,
            Season season,
            Gender gender,
            Collection<Status> statuses,
            Pageable pageCnt);

    Article findOneById(Long id);

    Collection<Article> findAllByName(String name);

    @Query(value = "SELECT * FROM articles ORDER BY id DESC LIMIT 7", nativeQuery = true)
    List<Article> findLastAddedArticles();

}
