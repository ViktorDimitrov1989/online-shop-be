package com.online.shop.areas.articles.repositories;

import com.online.shop.areas.articles.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findOneByName(String name);

    Category findOneById(Long id);
}
