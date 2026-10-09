# Prérequis
- jdk 21

# module demo-openapi-tools

## Objectif
- Illustrer la génération du code source client en fonction de contrats openapi via l'outil [openapi-generator](https://github.com/OpenAPITools/openapi-generator)
- Illustrer le pattern _tolerant reader_ lors de la sérialisation et désérialisation des flux json en structures objet

## Génération d'un client API avec OpenAPI Generator
Cet exemple s'appuie sur le plugin maven de l'outil https://github.com/OpenAPITools/openapi-generator (cf configuration du plugin dans le pom)

Le code source est généré en fonction des fichiers openAPI stockés sous le répertoire _openapi-specs_

Le plugin maven permet de spécifier l'écosystème du client API, en l'ocurrence pour cet exemple :
- utlisation de librairie Jackson pour la sérialisation/déserialisation des structures JSON
- utlisation la librairie Apache http-client pour la préparation et l'exécution des requêtes http

### Lancer la commande
`./mvnw -f demo-openapitools clean compile -Pgenerate-from-openapi
`

### Résultat attendu :
- Génération d'un projet client API complet dans _target/generated-sources/openapi_
- Les sources sont dispatchés en 3 packages :
    - **api** : appel des ressources API
    - **invoker** : détail d'implémentation de l'appel aux ressources API
    - **model** : les structures objet pour la serialisation/déserialisation jSON côté client

## Pattern Tolerant Reader
Vérifier que le code du client API est tolérant aux évolutions mineures du contrat OpenAPI (ie quand la compatibilité ascendante du contrat est assurée).


### Point d'attention sur la classe générée _invoker/ApiClient_

La configuration Jackson générée respecte le pattern _Tolerant Reader_ en introduisant une configuration fine sur la serialisation/déserialisation des structures JSON :
<pre>
  public ApiClient(CloseableHttpClient httpClient) {
    objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
    ...

</pre>

**Pensons-y !**