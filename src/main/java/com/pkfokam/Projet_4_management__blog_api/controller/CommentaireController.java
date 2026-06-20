package com.pkfokam.Projet_4_management__blog_api.controller;

import com.pkfokam.Projet_4_management__blog_api.dto.ApiResponse;
import com.pkfokam.Projet_4_management__blog_api.dto.CommentaireRequest;
import com.pkfokam.Projet_4_management__blog_api.dto.CommentaireResponse;
import com.pkfokam.Projet_4_management__blog_api.service.CommentaireService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles/{articleId}/commentaires")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Commentaires", description = "API de gestion des commentaires d'articles")
public class CommentaireController {

    private final CommentaireService commentaireService;

    // ==========================================
    // POST /api/articles/{articleId}/commentaires
    // ==========================================

    @PostMapping
    @Operation(
            summary = "Ajouter un commentaire",
            description = "Ajoute un nouveau commentaire à un article existant. L'article doit exister."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Commentaire ajouté avec succès"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Données invalides"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Article non trouvé"
            )
    })
    public ResponseEntity<ApiResponse<CommentaireResponse>> ajouterCommentaire(
            @Parameter(description = "Identifiant de l'article", example = "1")
            @PathVariable Long articleId,
            @Valid @RequestBody CommentaireRequest request) {
        log.info("POST /api/articles/{}/commentaires - Ajout d'un commentaire", articleId);
        CommentaireResponse commentaire = commentaireService.ajouterCommentaire(articleId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Commentaire ajouté avec succès", commentaire));
    }

    // ==========================================
    // GET /api/articles/{articleId}/commentaires
    // ==========================================

    @GetMapping
    @Operation(
            summary = "Lister les commentaires d'un article",
            description = "Retourne tous les commentaires d'un article, triés par date de création croissante."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Liste des commentaires récupérée avec succès"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Article non trouvé"
            )
    })
    public ResponseEntity<ApiResponse<List<CommentaireResponse>>> obtenirCommentaires(
            @Parameter(description = "Identifiant de l'article", example = "1")
            @PathVariable Long articleId) {
        log.info("GET /api/articles/{}/commentaires - Récupération des commentaires", articleId);
        List<CommentaireResponse> commentaires =
                commentaireService.obtenirCommentairesParArticle(articleId);
        return ResponseEntity.ok(
                ApiResponse.success(
                        String.format("%d commentaire(s) trouvé(s)", commentaires.size()),
                        commentaires
                )
        );
    }
}
