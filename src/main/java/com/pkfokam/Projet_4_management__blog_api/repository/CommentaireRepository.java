package com.pkfokam.Projet_4_management__blog_api.repository;

import com.pkfokam.Projet_4_management__blog_api.entity.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

    /**
     * Récupère tous les commentaires d'un article donné.
     *
     * @param articleId l'identifiant de l'article
     * @return la liste des commentaires
     */
    List<Commentaire> findByArticleIdOrderByDateCreationAsc(Long articleId);

    /**
     * Compte le nombre de commentaires pour un article.
     *
     * @param articleId l'identifiant de l'article
     * @return le nombre de commentaires
     */
    long countByArticleId(Long articleId);
}
