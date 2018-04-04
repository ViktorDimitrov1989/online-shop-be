package com.online.shop.areas.articles.repositories;

import com.online.shop.areas.articles.entities.*;
import com.online.shop.areas.articles.enums.Gender;
import com.online.shop.areas.articles.enums.Season;
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

    Page<Article> findAllDistinctBySizesInAndColorsInAndBrandInAndCategoryInAndCategorySeasonAndCategoryGender(
            Collection<Size> sizes,
            Collection<Color> colors,
            Collection<Brand> brands,
            Collection<Category> categories,
            Season season,
            Gender gender,
            Pageable pageCnt);

}
