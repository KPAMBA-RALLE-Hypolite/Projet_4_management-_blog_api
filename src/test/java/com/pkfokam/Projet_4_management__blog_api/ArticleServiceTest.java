package com.pkfokam.Projet_4_management__blog_api;

import com.pkfokam.Projet_4_management__blog_api.dto.ArticleRequest;
import com.pkfokam.Projet_4_management__blog_api.dto.ArticleResponse;
import com.pkfokam.Projet_4_management__blog_api.dto.ArticleSummaryResponse;
import com.pkfokam.Projet_4_management__blog_api.entity.Article;
import com.pkfokam.Projet_4_management__blog_api.exception.ResourceNotFoundException;
import com.pkfokam.Projet_4_management__blog_api.repository.ArticleRepository;
import com.pkfokam.Projet_4_management__blog_api.repository.CommentaireRepository;
import com.pkfokam.Projet_4_management__blog_api.service.impl.ArticleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests unitaires - ArticleService")
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private CommentaireRepository commentaireRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;

    private Article articleSample;
    private ArticleRequest requestSample;

    @BeforeEach
    void setUp() {
        articleSample = Article.builder()
                .id(1L)
                .titre("Article de test")
                .contenu("Contenu de l'article de test avec suffisamment de texte")
                .datePublication(LocalDateTime.now())
                .commentaires(new ArrayList<>())
                .build();

        requestSample = ArticleRequest.builder()
                .titre("Article de test")
                .contenu("Contenu de l'article de test avec suffisamment de texte")
                .build();
    }

    @Test
    @DisplayName("Créer un article - succès")
    void creerArticle_Success() {
        given(articleRepository.save(any(Article.class))).willReturn(articleSample);

        ArticleResponse result = articleService.creerArticle(requestSample);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitre()).isEqualTo("Article de test");
        then(articleRepository).should(times(1)).save(any(Article.class));
    }

    @Test
    @DisplayName("Obtenir tous les articles - liste vide")
    void obtenirTousLesArticles_ListeVide() {
        given(articleRepository.findAllByOrderByDatePublicationDesc()).willReturn(List.of());

        List<ArticleSummaryResponse> result = articleService.obtenirTousLesArticles();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Obtenir un article par ID - trouvé")
    void obtenirArticleParId_Trouve() {
        given(articleRepository.findByIdWithCommentaires(1L)).willReturn(Optional.of(articleSample));

        ArticleResponse result = articleService.obtenirArticleParId(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Obtenir un article par ID - non trouvé")
    void obtenirArticleParId_NonTrouve() {
        given(articleRepository.findByIdWithCommentaires(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> articleService.obtenirArticleParId(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    @DisplayName("Mettre à jour un article - succès")
    void mettreAJourArticle_Success() {
        given(articleRepository.findById(1L)).willReturn(Optional.of(articleSample));
        given(articleRepository.save(any(Article.class))).willReturn(articleSample);

        ArticleRequest updateRequest = ArticleRequest.builder()
                .titre("Titre modifié")
                .contenu("Contenu modifié avec suffisamment de texte")
                .build();

        ArticleResponse result = articleService.mettreAJourArticle(1L, updateRequest);

        assertThat(result).isNotNull();
        then(articleRepository).should(times(1)).save(any(Article.class));
    }

    @Test
    @DisplayName("Supprimer un article - succès")
    void supprimerArticle_Success() {
        given(articleRepository.findById(1L)).willReturn(Optional.of(articleSample));
        willDoNothing().given(articleRepository).delete(any(Article.class));

        assertThatCode(() -> articleService.supprimerArticle(1L))
                .doesNotThrowAnyException();

        then(articleRepository).should(times(1)).delete(articleSample);
    }

    @Test
    @DisplayName("Supprimer un article - non trouvé")
    void supprimerArticle_NonTrouve() {
        given(articleRepository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> articleService.supprimerArticle(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
