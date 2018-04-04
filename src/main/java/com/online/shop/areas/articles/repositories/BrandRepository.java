package com.online.shop.areas.articles.repositories;

import com.online.shop.areas.articles.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findOneByName(String name);

    Set<Brand> findAllByNameIn(Collection<String> names);

}
