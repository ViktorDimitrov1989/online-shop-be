package com.online.shop.areas.articles.repositories;

import com.online.shop.areas.articles.entities.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long>{

    Color findOneByName(String name);

    Set<Color> findAllByNameIn(Collection<String> colorNames);
}
