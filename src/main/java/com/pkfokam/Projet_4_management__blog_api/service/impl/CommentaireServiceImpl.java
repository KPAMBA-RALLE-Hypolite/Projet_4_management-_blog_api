package com.pkfokam.Projet_4_management__blog_api.service.impl;

import com.pkfokam.Projet_4_management__blog_api.dto.CommentaireRequest;
import com.pkfokam.Projet_4_management__blog_api.dto.CommentaireResponse;
import com.pkfokam.Projet_4_management__blog_api.entity.Article;
import com.pkfokam.Projet_4_management__blog_api.entity.Commentaire;
import com.pkfokam.Projet_4_management__blog_api.exception.ResourceNotFoundException;
import com.pkfokam.Projet_4_management__blog_api.repository.ArticleRepository;
import com.pkfokam.Projet_4_management__blog_api.repository.CommentaireRepository;
import com.pkfokam.Projet_4_management__blog_api.service.CommentaireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CommentaireServiceImpl implements CommentaireService {

    private final ArticleRepository articleRepository;
    private final CommentaireRepository commentaireRepository;

    @Override
    public CommentaireResponse ajouterCommentaire(Long articleId, CommentaireRequest request) {
        log.info("Ajout d'un commentaire sur l'article ID : {}", articleId);

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article", "id", articleId));

        Commentaire commentaire = Commentaire.builder()
                .auteur(request.getAuteur())
                .contenu(request.getContenu())
                .dateCreation(LocalDateTime.now())
                .article(article)
                .build();

        Commentaire saved = commentaireRepository.save(commentaire);
        log.info("Commentaire créé avec succès, ID : {}", saved.getId());
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentaireResponse> obtenirCommentairesParArticle(Long articleId) {
        log.debug("Récupération des commentaires pour l'article ID : {}", articleId);

        // Vérifier que l'article existe
        if (!articleRepository.existsById(articleId)) {
            throw new ResourceNotFoundException("Article", "id", articleId);
        }

        return commentaireRepository.findByArticleIdOrderByDateCreationAsc(articleId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CommentaireResponse mapToResponse(Commentaire commentaire) {
        return CommentaireResponse.builder()
                .id(commentaire.getId())
                .auteur(commentaire.getAuteur())
                .contenu(commentaire.getContenu())
                .dateCreation(commentaire.getDateCreation())
                .articleId(commentaire.getArticle().getId())
                .build();
    }
}
