package com.pkfokam.Projet_4_management__blog_api.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Résumé d'un article pour l'affichage en liste")
public class ArticleSummaryResponse {

    @Schema(description = "Identifiant unique de l'article", example = "1")
    private Long id;

    @Schema(description = "Titre de l'article", example = "Introduction à Spring Boot")
    private String titre;

    @Schema(description = "Extrait du contenu (150 premiers caractères)")
    private String extrait;

    @Schema(description = "Date de publication de l'article", example = "2024-06-15T10:30:00")
    private LocalDateTime datePublication;

    @Schema(description = "Nombre total de commentaires", example = "3")
    private long nombreCommentaires;
}
