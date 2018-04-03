package com.online.shop.areas.articles.repositories;

import com.online.shop.areas.articles.entities.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(value = "SELECT a" +
            " FROM articles AS a" +
            " INNER JOIN articles_colors AS ac ON a.id = ac.article_id" +
            " INNER JOIN colors AS c ON ac.color_id = c.id" +
            " INNER JOIN articles_sizes AS asz ON a.id = asz.article_id" +
            " INNER JOIN sizes AS s ON asz.size_id = s.id" +
            " INNER JOIN brands AS b ON a.brand_id = b.id" +
            " INNER JOIN categories AS cat ON a.category_id = cat.id" +
            " INNER JOIN article_statuses AS ast ON a.article_status_id = ast.id" +
            " WHERE" +
            " c.name NOT IN (:colors)" +
            " AND s.name NOT IN (:sizes)" +
            " AND b.name NOT IN (:brands)" +
            " AND cat.id NOT IN (:categories)" +
            " AND cat.season = :season" +
            " AND cat.gender = :gender", nativeQuery = true)
    List<Article> findFilteredArticles(@Param(value= "colors")List<String> colors,
                                       @Param(value= "categories")List<Long> categories,
                                       @Param(value= "sizes")List<String> sizes,
                                       @Param(value= "brands")List<String> brands,
                                       @Param(value = "season") String season,
                                       @Param(value = "gender") String gender);

}
