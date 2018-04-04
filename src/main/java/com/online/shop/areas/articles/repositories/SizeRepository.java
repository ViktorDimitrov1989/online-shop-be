package com.online.shop.areas.articles.repositories;

import com.online.shop.areas.articles.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {

    Set<Size> findAllByNameIn(Collection<String> name);
}
