package com.pkfokam.Projet_4_management__blog_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "articles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "commentaires")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String titre;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenu;

    @Column(name = "date_publication", nullable = false)
    private LocalDateTime datePublication;

    @OneToMany(
            mappedBy = "article",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Commentaire> commentaires = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (this.datePublication == null) {
            this.datePublication = LocalDateTime.now();
        }
    }

    public void addCommentaire(Commentaire commentaire) {
        commentaires.add(commentaire);
        commentaire.setArticle(this);
    }

    public void removeCommentaire(Commentaire commentaire) {
        commentaires.remove(commentaire);
        commentaire.setArticle(null);
    }
}