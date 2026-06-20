package com.pkfokam.Projet_4_management__blog_api.repository;

import com.pkfokam.Projet_4_management__blog_api.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a LEFT JOIN FETCH a.commentaires WHERE a.id = :id")
    Optional<Article> findByIdWithCommentaires(@Param("id") Long id);

    List<Article> findAllByOrderByDatePublicationDesc();

    List<Article> findByTitreContainingIgnoreCase(String titre);
}
