# Les Flow d'authentifications

Les Principaux concepts (Client, Ressource Owner, Ressource Server, Authentification Server)

![concept](https://github.com/user-attachments/assets/dd786909-6102-40c6-825b-a858ac9613f2)


## Flow Client Credentials 
Dans ce mode le client id et le client secret sont directement fournit dans la requête afin d'obtenir un Jeton d'authentification.
Ce flow doit être utilisé dans le mode machine to machine.
Ce mode est bien sur à proscrire dans nos PN puisqu'il adresse en clair le client id et client secret.

![client-credential](https://github.com/user-attachments/assets/c5886228-ca9e-4000-a545-b59fe2ef72bb)


## Flow Authorization Code Flow
Ce mode d'authentification est le mode nominal a utiliser pour authentifier nos écrans.
Il s'appui sur un mécanisme en 2 étapes: 

* Une première requête est initié avec le client id permettant de récupérer un code d'autorisation.
* Ce code d'autorisation est déposé sur une url connue de l'Autorisation Server et disponible dans l'application Client
* Une deuxième requête est alors effectue avec le Code d'autorisation pour finaliser la création du Jeton d'autorisation

![acf](https://github.com/user-attachments/assets/ba0d7153-4c0d-4cde-b9b0-9ff60dd3be09)


## Proteger le Client Secret

![clientsecret](https://github.com/user-attachments/assets/8704798f-b2fa-4d68-bae6-e6d1f2300761)

Afin de protéger votre client secret, il est nécessaire de dédier une ressource vous permmettant de le cacher de vos consomateurs.<br>
La solution est de mettre en place une ressource sur l'un des serveurs permettant de cacher le client secret et d'appeller par rebond le serveur d'authentification.<br>
