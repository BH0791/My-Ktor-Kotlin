# Développement d'API avec ktor

## Sommaire
### Chapitre-1
1. [x] Introduction
   1. [x] Qu'est-ce que ktor
   2. [x] Comparaison avec le framework Spring
   3. [x] Le temps de démarrage
   4. [x] L'écosystème
   5. [x] La performance
   6. [x] Créer une application serveur en utilisant ktor
   7. [x] Choisir un moteur HTTP 
2. [x] Les base de ktor
   1. [x] Ecrire une API REST
   2. [x] La sérialisation des objets
   3. [x] Validation des contenus des requêtes
   4. [x] Contrôler le code HTTP renvoyé lrs d'une exception
   5. [x] Gérer les en-têtes HTTP
   6. [x] Servir du contenu statique
   7. [x] Template HTML
   8. [x] Bien structurer son application
3. [x] Authentification basique
   1. [x] Authentification basique
   2. [x] Utilisation d'un token JWT
   3. [x] Intégration avec LDPA
   4. [x] Authentification OAuth
   5. [x] Gestion des sessions et des cookies
4. [x] Ktor: documenter son API
   1. [x] Documentation OpenAPI
   2. [x] Exposer l'interface OpenAPI
   3. [x] Exposer l'interface Swagger
5. [x] Tests son API REST
   1. [x] Configuration de l'environnement de test
   2. [x] Ecrire du test
6. [x] Se connecter à une API REST avec le client HTTP
    1. [x] Installation et configuration
    2. [x] Envoyer des requêtes HTTP
7. [x] Utilisation des WebSockets
   1. [x] Créer un serveur WebSocket
   2. [x] Définir des points d'accès WebSocket
   3. [x] Utiliser WebSockers côté client
8.[x] Monitoring d'une application Ktor
    1. [x] Loging
    2. [x] Integration avec Micrometer
    3. [x] Utilisation de Jaeger
9.[x] Déploiement de l'application
   1. [x] Déploiement sous forme d'un Fat-JAR
   2. [x] Déploiement sous forme d'un dossier d'installation
   3. [x] Déploiement sous forme d'image Docker
10.[x] Conclusion
### Chapitre-2
1. [x] Introduction


[RFC 7231](https://datatracker.ietf.org/doc/html/rfc7231#section-6.1)
```kotlin
+------+-------------------------------+--------------------------+
| Code | Reason-Phrase                 | Defined in...            |
+------+-------------------------------+--------------------------+
| 100  | Continue                      | Section 6.2.1            |
| 101  | Switching Protocols           | Section 6.2.2            |
| 200  | OK                            | Section 6.3.1            |
| 201  | Created                       | Section 6.3.2            |
| 202  | Accepted                      | Section 6.3.3            |
| 203  | Non-Authoritative Information | Section 6.3.4            |
| 204  | No Content                    | Section 6.3.5            |
| 205  | Reset Content                 | Section 6.3.6            |
| 206  | Partial Content               | Section 4.1 of [RFC7233] |
| 300  | Multiple Choices              | Section 6.4.1            |
| 301  | Moved Permanently             | Section 6.4.2            |
| 302  | Found                         | Section 6.4.3            |
| 303  | See Other                     | Section 6.4.4            |
| 304  | Not Modified                  | Section 4.1 of [RFC7232] |
| 305  | Use Proxy                     | Section 6.4.5            |
| 307  | Temporary Redirect            | Section 6.4.7            |
| 400  | Bad Request                   | Section 6.5.1            |
| 401  | Unauthorized                  | Section 3.1 of [RFC7235] |
| 402  | Payment Required              | Section 6.5.2            |
| 403  | Forbidden                     | Section 6.5.3            |
| 404  | Not Found                     | Section 6.5.4            |
| 405  | Method Not Allowed            | Section 6.5.5            |
| 406  | Not Acceptable                | Section 6.5.6            |
| 407  | Proxy Authentication Required | Section 3.2 of [RFC7235] |
| 408  | Request Timeout               | Section 6.5.7            |
| 409  | Conflict                      | Section 6.5.8            |
| 410  | Gone                          | Section 6.5.9            |
| 411  | Length Required               | Section 6.5.10           |
| 412  | Precondition Failed           | Section 4.2 of [RFC7232] |
| 413  | Payload Too Large             | Section 6.5.11           |
| 414  | URI Too Long                  | Section 6.5.12           |
| 415  | Unsupported Media Type        | Section 6.5.13           |
| 416  | Range Not Satisfiable         | Section 4.4 of [RFC7233] |
| 417  | Expectation Failed            | Section 6.5.14           |
| 426  | Upgrade Required              | Section 6.5.15           |
| 500  | Internal Server Error         | Section 6.6.1            |
| 501  | Not Implemented               | Section 6.6.2            |
| 502  | Bad Gateway                   | Section 6.6.3            |
| 503  | Service Unavailable           | Section 6.6.4            |
| 504  | Gateway Timeout               | Section 6.6.5            |
| 505  | HTTP Version Not Supported    | Section 6.6.6            |
+------+-------------------------------+--------------------------+