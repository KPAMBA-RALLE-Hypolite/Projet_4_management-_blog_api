package com.pkfokam.Projet_4_management__blog_api.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Enveloppe générique pour toutes les réponses de l'API.
 *
 * @param <T> le type de données retournées
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Réponse standard de l'API")
public class ApiResponse<T> {

    @Schema(description = "Indique si la requête a réussi", example = "true")
    private boolean success;

    @Schema(description = "Message informatif", example = "Article créé avec succès")
    private String message;

    @Schema(description = "Données retournées")
    private T data;

    @Schema(description = "Horodatage de la réponse")
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    /**
     * Crée une réponse de succès avec données.
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Crée une réponse de succès sans données.
     */
    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .build();
    }

    /**
     * Crée une réponse d'erreur.
     */
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .build();
    }
}
