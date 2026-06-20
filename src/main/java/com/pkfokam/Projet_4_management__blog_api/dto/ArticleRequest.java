package com.pkfokam.Projet_4_management__blog_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Données pour créer ou mettre à jour un article")
public class ArticleRequest {

    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 3, max = 255, message = "Le titre doit contenir entre 3 et 255 caractères")
    @Schema(
            description = "Titre de l'article",
            example = "Introduction à Spring Boot",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String titre;

    @NotBlank(message = "Le contenu est obligatoire")
    @Size(min = 10, message = "Le contenu doit contenir au moins 10 caractères")
    @Schema(
            description = "Contenu complet de l'article",
            example = "Spring Boot est un framework Java qui facilite la création d'applications...",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String contenu;

    @Schema(
            description = "Date de publication (optionnelle, utilise la date courante si non fournie)",
            example = "2024-06-15T10:30:00"
    )
    private LocalDateTime datePublication;
}
