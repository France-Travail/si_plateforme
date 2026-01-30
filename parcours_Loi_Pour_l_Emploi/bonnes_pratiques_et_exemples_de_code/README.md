# 🧪 Bonne pratiques et exemples de code

Ce dépôt regroupe plusieurs **projets java**, chacun illustrant des **bonnes pratiques** ou un **cas d’usage concret** autour de la consommation d’APIs.

L’objectif est de fournir :
- des **exemples de code réutilisables**
- des **patterns éprouvés**
- des **références techniques** pour des projets orientés API (REST, OpenAPI, CI/CD)

---
# 📦 Structure du dépôt

## Authentification-Client-Credential-et-ACF
- illustrer les flows d'authentification pour utiliser les API exposées sur francetravail.io
- exemples en java et php

Plus d'informations dans le README du module !

## demo-openapi-tools
- Illustrer la génération du code source client en fonction de contrats openapi via l'outil [openapi-generator](https://github.com/OpenAPITools/openapi-generator)
- Illustrer le pattern _tolerant reader_ lors de la sérialisation et désérialisation des flux json en structures objet

Plus d'informations dans le README du module !

## demo-traitementMasse
- illustrer un traitement de masse pour un ensemble d'usagers sur les api du périmètre _Loi Plein Emploi_
- illustrer les optimisations possibles sur ce traitement de masse :
  - parallélisation des appels en tenant compte des quotas
  - stratégie de rejeu sur erreurs

Plus d'informations dans le README du module !



