package com.pkfokam.Projet_4_management__blog_api.service.impl;

import com.pkfokam.Projet_4_management__blog_api.dto.ArticleRequest;
import com.pkfokam.Projet_4_management__blog_api.dto.ArticleResponse;
import com.pkfokam.Projet_4_management__blog_api.dto.ArticleSummaryResponse;
import com.pkfokam.Projet_4_management__blog_api.dto.CommentaireResponse;
import com.pkfokam.Projet_4_management__blog_api.entity.Article;
import com.pkfokam.Projet_4_management__blog_api.exception.ResourceNotFoundException;
import com.pkfokam.Projet_4_management__blog_api.repository.ArticleRepository;
import com.pkfokam.Projet_4_management__blog_api.repository.CommentaireRepository;
import com.pkfokam.Projet_4_management__blog_api.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation du service de gestion des articles.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentaireRepository commentaireRepository;

    @Override
    public ArticleResponse creerArticle(ArticleRequest request) {
        log.info("Création d'un nouvel article : {}", request.getTitre());

        Article article = Article.builder()
                .titre(request.getTitre())
                .contenu(request.getContenu())
                .datePublication(
                        request.getDatePublication() != null
                                ? request.getDatePublication()
                                : LocalDateTime.now()
                )
                .build();

        Article saved = articleRepository.save(article);
        log.info("Article créé avec succès, ID : {}", saved.getId());
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleSummaryResponse> obtenirTousLesArticles() {
        log.debug("Récupération de tous les articles");
        return articleRepository.findAllByOrderByDatePublicationDesc()
                .stream()
                .map(this::mapToSummaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleResponse obtenirArticleParId(Long id) {
        log.debug("Récupération de l'article ID : {}", id);
        Article article = articleRepository.findByIdWithCommentaires(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));
        return mapToResponse(article);
    }

    @Override
    public ArticleResponse mettreAJourArticle(Long id, ArticleRequest request) {
        log.info("Mise à jour de l'article ID : {}", id);
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));

        article.setTitre(request.getTitre());
        article.setContenu(request.getContenu());
        if (request.getDatePublication() != null) {
            article.setDatePublication(request.getDatePublication());
        }

        Article updated = articleRepository.save(article);
        log.info("Article mis à jour avec succès, ID : {}", updated.getId());
        return mapToResponse(updated);
    }

    @Override
    public void supprimerArticle(Long id) {
        log.info("Suppression de l'article ID : {}", id);
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", id));
        articleRepository.delete(article);
        log.info("Article supprimé avec succès, ID : {}", id);
    }

    // ==========================================
    // Méthodes de mapping privées
    // ==========================================

    private ArticleResponse mapToResponse(Article article) {
        List<CommentaireResponse> commentaires = article.getCommentaires().stream()
                .map(c -> CommentaireResponse.builder()
                        .id(c.getId())
                        .auteur(c.getAuteur())
                        .contenu(c.getContenu())
                        .dateCreation(c.getDateCreation())
                        .articleId(article.getId())
                        .build())
                .collect(Collectors.toList());

        return ArticleResponse.builder()
                .id(article.getId())
                .titre(article.getTitre())
                .contenu(article.getContenu())
                .datePublication(article.getDatePublication())
                .nombreCommentaires(commentaires.size())
                .commentaires(commentaires)
                .build();
    }

    private ArticleSummaryResponse mapToSummaryResponse(Article article) {
        String extrait = article.getContenu().length() > 150
                ? article.getContenu().substring(0, 150) + "..."
                : article.getContenu();

        long nbCommentaires = commentaireRepository.countByArticleId(article.getId());

        return ArticleSummaryResponse.builder()
                .id(article.getId())
                .titre(article.getTitre())
                .extrait(extrait)
                .datePublication(article.getDatePublication())
                .nombreCommentaires(nbCommentaires)
                .build();
    }
}
