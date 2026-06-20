# 📝 Blog API - Articles & Commentaires

Une API REST complète pour la gestion d'un blog, développée avec Spring Boot 3, Spring Data JPA et documentée avec Swagger/OpenAPI.

---

## 🚀 Fonctionnalités

- **Articles** : Créer, lire, mettre à jour et supprimer des articles de blog
- **Commentaires** : Ajouter et consulter des commentaires sur les articles
- **Documentation interactive** via Swagger UI
- **Validation** des données entrantes
- **Gestion globale des erreurs** avec réponses JSON standardisées
- **Base de données H2** en mémoire pour le développement (pas d'installation requise)
- **Support MySQL/PostgreSQL** pour la production

---

## 📋 Prérequis

| Outil | Version minimale |
|-------|-----------------|
| Java  | 17+             |
| Maven | 3.8+            |
| Git   | 2.x             |

> **Note :** Aucune base de données à installer pour le mode développement (H2 in-memory inclus).

---

## 🛠️ Installation

### 1. Cloner le dépôt

```bash
git clone https://github.com/votre-username/blog-api.git
cd blog-api
```

### 2. Vérifier les prérequis

```bash
java -version   # doit afficher Java 17+
mvn -version    # doit afficher Maven 3.8+
```

### 3. Compiler le projet

```bash
mvn clean install
```

---

## ⚙️ Configuration de la base de données

### Mode Développement (défaut) — H2 In-Memory

Aucune configuration requise. La base de données H2 démarre automatiquement avec des données de test.

Fichier : `src/main/resources/application-dev.properties`

```properties
spring.datasource.url=jdbc:h2:mem:blogdb
spring.datasource.username=sa
spring.datasource.password=
```

Console H2 accessible sur : [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL** : `jdbc:h2:mem:blogdb`
- **Username** : `sa`
- **Password** : *(laisser vide)*

---

### Mode Production — MySQL

#### 1. Créer la base de données MySQL

```sql
CREATE DATABASE blogdb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'bloguser'@'localhost' IDENTIFIED BY 'motdepasse';
GRANT ALL PRIVILEGES ON blogdb.* TO 'bloguser'@'localhost';
FLUSH PRIVILEGES;
```

#### 2. Configurer les variables d'environnement

```bash
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=blogdb
export DB_USERNAME=bloguser
export DB_PASSWORD=motdepasse
```

#### 3. Lancer avec le profil production

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

---

## ▶️ Lancement de l'application

### Développement (H2 in-memory)

```bash
mvn spring-boot:run
```

### Avec un profil spécifique

```bash
# Développement
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Production (MySQL requis)
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### Via le JAR compilé

```bash
mvn clean package
java -jar target/blog-api-1.0.0.jar

# Avec profil
java -jar target/blog-api-1.0.0.jar --spring.profiles.active=prod
```

L'application démarre sur : **http://localhost:8080**

---

## 📚 Documentation API (Swagger)

| Interface | URL |
|-----------|-----|
| Swagger UI | [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) |
| OpenAPI JSON | [http://localhost:8080/api-docs](http://localhost:8080/api-docs) |

---

## 🔗 Endpoints disponibles

### Articles

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `POST` | `/api/articles` | Créer un nouvel article |
| `GET` | `/api/articles` | Lister tous les articles |
| `GET` | `/api/articles/{id}` | Obtenir un article par ID |
| `PUT` | `/api/articles/{id}` | Mettre à jour un article |
| `DELETE` | `/api/articles/{id}` | Supprimer un article |

### Commentaires

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `POST` | `/api/articles/{articleId}/commentaires` | Ajouter un commentaire |
| `GET` | `/api/articles/{articleId}/commentaires` | Lister les commentaires d'un article |

---

## 📡 Exemples de requêtes

### Créer un article

```http
POST http://localhost:8080/api/articles
Content-Type: application/json

{
  "titre": "Mon premier article",
  "contenu": "Ceci est le contenu de mon premier article de blog. Il peut être très long.",
  "datePublication": "2024-06-15T10:30:00"
}
```

**Réponse (201 Created) :**
```json
{
  "success": true,
  "message": "Article créé avec succès",
  "data": {
    "id": 1,
    "titre": "Mon premier article",
    "contenu": "Ceci est le contenu de mon premier article de blog.",
    "datePublication": "2024-06-15T10:30:00",
    "nombreCommentaires": 0,
    "commentaires": []
  },
  "timestamp": "2024-06-15T10:30:01"
}
```

---

### Lister tous les articles

```http
GET http://localhost:8080/api/articles
```

**Réponse (200 OK) :**
```json
{
  "success": true,
  "message": "3 article(s) trouvé(s)",
  "data": [
    {
      "id": 3,
      "titre": "Troisième article",
      "extrait": "Extrait des 150 premiers caractères...",
      "datePublication": "2024-06-15T12:00:00",
      "nombreCommentaires": 2
    }
  ],
  "timestamp": "2024-06-15T12:01:00"
}
```

---

### Obtenir un article par ID (avec commentaires)

```http
GET http://localhost:8080/api/articles/1
```

---

### Mettre à jour un article

```http
PUT http://localhost:8080/api/articles/1
Content-Type: application/json

{
  "titre": "Titre mis à jour",
  "contenu": "Contenu mis à jour avec de nouvelles informations importantes."
}
```

---

### Supprimer un article

```http
DELETE http://localhost:8080/api/articles/1
```

---

### Ajouter un commentaire

```http
POST http://localhost:8080/api/articles/1/commentaires
Content-Type: application/json

{
  "auteur": "Jean Dupont",
  "contenu": "Excellent article, merci pour le partage !"
}
```

**Réponse (201 Created) :**
```json
{
  "success": true,
  "message": "Commentaire ajouté avec succès",
  "data": {
    "id": 1,
    "auteur": "Jean Dupont",
    "contenu": "Excellent article, merci pour le partage !",
    "dateCreation": "2024-06-15T14:22:00",
    "articleId": 1
  },
  "timestamp": "2024-06-15T14:22:00"
}
```

---

### Lister les commentaires d'un article

```http
GET http://localhost:8080/api/articles/1/commentaires
```

---

## 🏗️ Structure du projet

```
blog-api/
├── src/
│   ├── main/
│   │   ├── java/com/blog/api/
│   │   │   ├── BlogApiApplication.java       # Point d'entrée
│   │   │   ├── config/
│   │   │   │   ├── OpenApiConfig.java        # Configuration Swagger
│   │   │   │   └── WebConfig.java            # Configuration CORS
│   │   │   ├── controller/
│   │   │   │   ├── ArticleController.java    # Endpoints articles
│   │   │   │   └── CommentaireController.java # Endpoints commentaires
│   │   │   ├── dto/
│   │   │   │   ├── ApiResponse.java          # Enveloppe de réponse générique
│   │   │   │   ├── ArticleRequest.java       # DTO création/mise à jour article
│   │   │   │   ├── ArticleResponse.java      # DTO réponse article complet
│   │   │   │   ├── ArticleSummaryResponse.java # DTO résumé article
│   │   │   │   ├── CommentaireRequest.java   # DTO création commentaire
│   │   │   │   └── CommentaireResponse.java  # DTO réponse commentaire
│   │   │   ├── entity/
│   │   │   │   ├── Article.java              # Entité JPA Article
│   │   │   │   └── Commentaire.java          # Entité JPA Commentaire
│   │   │   ├── exception/
│   │   │   │   ├── GlobalExceptionHandler.java # Gestionnaire d'erreurs global
│   │   │   │   └── ResourceNotFoundException.java
│   │   │   ├── repository/
│   │   │   │   ├── ArticleRepository.java    # Repository JPA Article
│   │   │   │   └── CommentaireRepository.java # Repository JPA Commentaire
│   │   │   └── service/
│   │   │       ├── ArticleService.java       # Interface service Article
│   │   │       ├── ArticleServiceImpl.java   # Implémentation service Article
│   │   │       ├── CommentaireService.java   # Interface service Commentaire
│   │   │       └── CommentaireServiceImpl.java # Implémentation service Commentaire
│   │   └── resources/
│   │       ├── application.properties        # Configuration principale
│   │       ├── application-dev.properties    # Config développement (H2)
│   │       ├── application-prod.properties   # Config production (MySQL)
│   │       └── data.sql                      # Données initiales de test
│   └── test/
│       └── java/com/blog/api/
│           └── ArticleServiceTest.java       # Tests unitaires
└── pom.xml
```

---

## 🧪 Tests

### Lancer les tests unitaires

```bash
mvn test
```

### Lancer les tests avec rapport

```bash
mvn test jacoco:report
# Rapport disponible dans : target/site/jacoco/index.html
```

---

## 🛡️ Gestion des erreurs

Toutes les erreurs retournent une réponse JSON standardisée :

```json
{
  "success": false,
  "message": "Article non trouvé(e) avec id : '99'",
  "timestamp": "2024-06-15T10:30:00"
}
```

| Code HTTP | Situation |
|-----------|-----------|
| `200` | Succès |
| `201` | Ressource créée |
| `400` | Données invalides / Validation échouée |
| `404` | Ressource non trouvée |
| `500` | Erreur interne du serveur |

---

## 🔧 Stack Technologique

| Technologie | Version | Rôle |
|-------------|---------|------|
| Java | 17 | Langage |
| Spring Boot | 3.2.5 | Framework principal |
| Spring Data JPA | 3.2.5 | Persistance des données |
| Hibernate | 6.x | Implémentation JPA |
| H2 Database | 2.x | Base de données dev |
| MySQL | 8.x | Base de données prod |
| Lombok | 1.18.x | Réduction du boilerplate |
| Springdoc OpenAPI | 2.5.0 | Documentation Swagger |
| JUnit 5 | 5.x | Tests unitaires |
| Mockito | 5.x | Mocking pour les tests |

---

## 👤 Auteur

Projet développé dans le cadre du **Projet 4 - API de Gestion d'un Blog**.

---

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.