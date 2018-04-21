package com.online.shop.areas.articles.repositories;

import com.online.shop.areas.articles.entities.Category;
import com.online.shop.areas.articles.enums.Gender;
import com.online.shop.areas.articles.enums.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findOneByNameAndGenderAndSeason(String name, Gender gender, Season season);

    Category findOneById(Long id);

    Set<Category> findAllByIdIn(Collection<Long> ids);

}
