package com.pkfokam.Projet_4_management__blog_api.service;

import com.pkfokam.Projet_4_management__blog_api.dto.CommentaireRequest;
import com.pkfokam.Projet_4_management__blog_api.dto.CommentaireResponse;

import java.util.List;

public interface CommentaireService {

    CommentaireResponse ajouterCommentaire(Long articleId, CommentaireRequest request);

    List<CommentaireResponse> obtenirCommentairesParArticle(Long articleId);
}
