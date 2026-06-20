package com.pkfokam.Projet_4_management__blog_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Données d'un article retournées par l'API")
public class ArticleResponse {

    @Schema(description = "Identifiant unique de l'article", example = "1")
    private Long id;

    @Schema(description = "Titre de l'article", example = "Introduction à Spring Boot")
    private String titre;

    @Schema(description = "Contenu complet de l'article")
    private String contenu;

    @Schema(description = "Date de publication de l'article", example = "2024-06-15T10:30:00")
    private LocalDateTime datePublication;

    @Schema(description = "Nombre total de commentaires sur cet article", example = "5")
    private int nombreCommentaires;

    @Schema(description = "Liste des commentaires associés à l'article")
    private List<CommentaireResponse> commentaires;
}