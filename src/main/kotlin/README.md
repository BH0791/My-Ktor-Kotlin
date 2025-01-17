## Utilisation d'un token JWT
JSON Wew Token (JWT) est un standard ouvert (RFC 7519) qui définit une manière compacte et autonome pour sécuriser les échanges de données entre plusieurs parties. Les informations transmises sont vérifiables et fiables car elles sont signées numériquement. Les JWT peuvent être signés à l'aide d'un secret (avec l'algorithme HMAC) ou d'une paire de clés publique/privée à l'aide d'algorithmes RSA ou ECDSA.

## Processus d'authentification classique utilisant JWT.
1. L'utilisateur envoie ses informations d'identification (nom d'utilisateur et mot de passe) au serveur.
2. Le serveur vérifie si les informations d'identification sont correctes.
3. Si les informations d'identification sont correctes, le serveur crée un JWT et le renvoie à l'utilisateur.
4. L'utilisateur stocke le JWT et l'envoie dans l'en-tête de chaque requête.
5. Le serveur vérifie si le JWT est valide et autorise l'accès aux ressources protégées.
6. Si le JWT est invalide, le serveur renvoie une erreur.
7. L'utilisateur doit se connecter à nouveau pour obtenir un nouveau JWT.

### Implémentation de l'authentification JWT dans une application ktor.<br>
build.gradle.kts
````gradle
implementation("io.ktor:ktor-auth-jwt:$ktor_version")
````
application.conf
````kotlin
jwt{
    secret = "my_secret"
    secret = ${JWT_SECRET}
}
````
