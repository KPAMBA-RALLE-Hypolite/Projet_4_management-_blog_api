package com.pkfokam.Projet_4_management__blog_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Données d'un commentaire retournées par l'API")
public class CommentaireResponse {

    @Schema(description = "Identifiant unique du commentaire", example = "1")
    private Long id;

    @Schema(description = "Nom de l'auteur du commentaire", example = "Jean Dupont")
    private String auteur;

    @Schema(description = "Contenu du commentaire", example = "Très bon article !")
    private String contenu;

    @Schema(description = "Date de création du commentaire", example = "2024-06-15T14:22:00")
    private LocalDateTime dateCreation;

    @Schema(description = "Identifiant de l'article associé", example = "1")
    private Long articleId;
}
