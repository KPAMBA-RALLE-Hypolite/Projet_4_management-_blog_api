package com.pkfokam.Projet_4_management__blog_api.controller;

import com.pkfokam.Projet_4_management__blog_api.dto.ApiResponse;
import com.pkfokam.Projet_4_management__blog_api.dto.ArticleRequest;
import com.pkfokam.Projet_4_management__blog_api.dto.ArticleResponse;
import com.pkfokam.Projet_4_management__blog_api.dto.ArticleSummaryResponse;
import com.pkfokam.Projet_4_management__blog_api.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/api/articles")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Articles", description = "API de gestion des articles de blog")
public class ArticleController {

    private final ArticleService articleService;

    // ==========================================
    // POST /api/articles - Créer un article
    // ==========================================

    @PostMapping
    @Operation(
            summary = "Créer un nouvel article",
            description = "Crée un nouvel article de blog avec le titre, le contenu et optionnellement la date de publication."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Article créé avec succès",
                    content = @Content(schema = @Schema(implementation = ArticleResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Données invalides (validation échouée)"
            )
    })
    public ResponseEntity<ApiResponse<ArticleResponse>> creerArticle(
            @Valid @RequestBody ArticleRequest request) {
        log.info("POST /api/articles - Création d'un article : {}", request.getTitre());
        ArticleResponse article = articleService.creerArticle(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Article créé avec succès", article));
    }

    // ==========================================
    // GET /api/articles - Lister tous les articles
    // ==========================================

    @GetMapping
    @Operation(
            summary = "Lister tous les articles",
            description = "Retourne la liste de tous les articles triés par date de publication décroissante, avec un extrait du contenu."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Liste des articles récupérée avec succès"
    )
    public ResponseEntity<ApiResponse<List<ArticleSummaryResponse>>> obtenirTousLesArticles() {
        log.info("GET /api/articles - Récupération de tous les articles");
        List<ArticleSummaryResponse> articles = articleService.obtenirTousLesArticles();
        return ResponseEntity.ok(
                ApiResponse.success(
                        String.format("%d article(s) trouvé(s)", articles.size()),
                        articles
                )
        );
    }

    // ==========================================
    // GET /api/articles/{id} - Obtenir un article
    // ==========================================

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtenir un article par ID",
            description = "Retourne les détails complets d'un article, incluant tous ses commentaires."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Article trouvé"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Article non trouvé"
            )
    })
    public ResponseEntity<ApiResponse<ArticleResponse>> obtenirArticleParId(
            @Parameter(description = "Identifiant de l'article", example = "1")
            @PathVariable Long id) {
        log.info("GET /api/articles/{} - Récupération de l'article", id);
        ArticleResponse article = articleService.obtenirArticleParId(id);
        return ResponseEntity.ok(ApiResponse.success("Article récupéré avec succès", article));
    }

    // ==========================================
    // PUT /api/articles/{id} - Mettre à jour un article
    // ==========================================

    @PutMapping("/{id}")
    @Operation(
            summary = "Mettre à jour un article",
            description = "Met à jour le titre, le contenu et/ou la date de publication d'un article existant."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Article mis à jour avec succès"
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
    public ResponseEntity<ApiResponse<ArticleResponse>> mettreAJourArticle(
            @Parameter(description = "Identifiant de l'article", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ArticleRequest request) {
        log.info("PUT /api/articles/{} - Mise à jour de l'article", id);
        ArticleResponse article = articleService.mettreAJourArticle(id, request);
        return ResponseEntity.ok(ApiResponse.success("Article mis à jour avec succès", article));
    }

    // ==========================================
    // DELETE /api/articles/{id} - Supprimer un article
    // ==========================================

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprimer un article",
            description = "Supprime définitivement un article et tous ses commentaires associés."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Article supprimé avec succès"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Article non trouvé"
            )
    })
    public ResponseEntity<ApiResponse<Void>> supprimerArticle(
            @Parameter(description = "Identifiant de l'article", example = "1")
            @PathVariable Long id) {
        log.info("DELETE /api/articles/{} - Suppression de l'article", id);
        articleService.supprimerArticle(id);
        return ResponseEntity.ok(ApiResponse.success("Article supprimé avec succès"));
    }
}
