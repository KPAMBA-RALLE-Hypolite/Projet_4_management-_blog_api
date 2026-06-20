package com.pkfokam.Projet_4_management__blog_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Données pour ajouter un commentaire à un article")
public class CommentaireRequest {

    @NotBlank(message = "Le nom de l'auteur est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom de l'auteur doit contenir entre 2 et 100 caractères")
    @Schema(
            description = "Nom de l'auteur du commentaire",
            example = "Jean Dupont",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String auteur;

    @NotBlank(message = "Le contenu du commentaire est obligatoire")
    @Size(min = 2, max = 1000, message = "Le commentaire doit contenir entre 2 et 1000 caractères")
    @Schema(
            description = "Texte du commentaire",
            example = "Très bon article, merci pour le partage !",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String contenu;
}