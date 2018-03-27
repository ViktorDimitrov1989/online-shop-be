package com.online.shop.areas.articles.repositories;

import com.online.shop.areas.articles.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {

    Size findOneByName(String name);

}
