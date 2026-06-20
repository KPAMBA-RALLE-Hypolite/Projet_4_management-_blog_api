package com.pkfokam.Projet_4_management__blog_api.service;

import com.pkfokam.Projet_4_management__blog_api.dto.ArticleRequest;
import com.pkfokam.Projet_4_management__blog_api.dto.ArticleResponse;
import com.pkfokam.Projet_4_management__blog_api.dto.ArticleSummaryResponse;

import java.util.List;

public interface ArticleService {

    ArticleResponse creerArticle(ArticleRequest request);

    List<ArticleSummaryResponse> obtenirTousLesArticles();

    ArticleResponse obtenirArticleParId(Long id);

    ArticleResponse mettreAJourArticle(Long id, ArticleRequest request);

    void supprimerArticle(Long id);
}