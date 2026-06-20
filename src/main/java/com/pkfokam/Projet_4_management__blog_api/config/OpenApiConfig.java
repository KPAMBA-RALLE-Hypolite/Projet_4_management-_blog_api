package com.pkfokam.Projet_4_management__blog_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration de Swagger / OpenAPI 3.
 *
 * <p>Swagger UI disponible sur : <a href="http://localhost:8080/swagger-ui.html">http://localhost:8080/swagger-ui.html</a></p>
 * <p>Spec JSON disponible sur : <a href="http://localhost:8080/api-docs">http://localhost:8080/api-docs</a></p>
 */
@Configuration
public class OpenApiConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Bean
    public OpenAPI blogOpenAPI() {
        Server devServer = new Server()
                .url("http://localhost:" + serverPort)
                .description("Serveur de développement");

        Contact contact = new Contact()
                .name("Équipe Blog API")
                .email("contact@blogapi.com")
                .url("https://github.com/votre-username/blog-api");

        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("Blog API - Articles & Commentaires")
                .version("1.0.0")
                .description("""
                        ## API REST de gestion de blog
                        
                        Cette API permet de gérer des articles de blog et leurs commentaires.
                        
                        ### Fonctionnalités
                        - **Articles** : Créer, lire, mettre à jour et supprimer des articles
                        - **Commentaires** : Ajouter et consulter des commentaires sur les articles
                        
                        ### Base de données
                        En mode développement, une base H2 in-memory est utilisée.
                        Accès console H2 : [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
                        """)
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}
