-- Données initiales pour le développement

INSERT INTO articles (titre, contenu, date_publication)
VALUES
    ('Premiers pas avec Spring Boot',
     'Spring Boot est un framework Java qui simplifie considérablement le développement d''applications. Dans cet article, nous allons découvrir les bases de Spring Boot et comment créer notre première application.',
     CURRENT_TIMESTAMP),

    ('Introduction à l''API REST',
     'Une API REST (Representational State Transfer) est une interface de programmation qui permet l''échange de données entre systèmes via le protocole HTTP. Découvrons ensemble les principes fondamentaux.',
     CURRENT_TIMESTAMP),

    ('JPA et Hibernate avec Spring',
     'Spring Data JPA simplifie l''accès aux bases de données relationnelles. Avec Hibernate comme implémentation JPA par défaut, Spring Boot offre une intégration transparente pour la persistance des données.',
     CURRENT_TIMESTAMP);

INSERT INTO commentaires (auteur, contenu, date_creation, article_id)
VALUES
    ('Alice Dupont', 'Excellent article, très bien expliqué !', CURRENT_TIMESTAMP, 1),
    ('Bob Martin', 'Merci pour ce partage, j''ai appris beaucoup de choses.', CURRENT_TIMESTAMP, 1),
    ('Claire Bernard', 'Très utile pour les débutants comme moi.', CURRENT_TIMESTAMP, 2),
    ('David Lemaire', 'Super contenu, j''attends la suite !', CURRENT_TIMESTAMP, 3);