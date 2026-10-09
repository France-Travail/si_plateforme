# Loi Plein Emploi – parcours fonctionnels autour des sanctions

Workflows fonctionnels décrivant les principales cinématiques autour des sanctions dans le cadre de la Loi Plein Emploi.

---

## Table des matières

- [Valider une proposition de suspension de RSA](#valider-une-proposition-de-suspension-de-rsa)
  - [Récupérer l'access token](#recuperer-access-token-suspension)
  - [Récupérer les propositions de suspension RSA à instruire](#recuperer-propositions-suspension-rsa-a-instruire)
  - [Forger un jeton usager](#forger-jeton-usager-suspension)
  - [Consulter la proposition de sanction](#consulter-proposition-sanction)
  - [Instruire la proposition de sanction](#instruire-proposition-sanction)
  - [Valider la suspension RSA](#valider-suspension-rsa)

- [Valider une proposition de suppression de RSA](#valider-une-proposition-de-suppression-de-rsa)
  - [Récupérer l'access token](#recuperer-access-token-suppression)
  - [Récupérer les propositions de suppression RSA à traiter](#recuperer-propositions-suppression-rsa-a-traiter)
  - [Forger un jeton usager](#forger-jeton-usager-suppression)
  - [Consulter la proposition de suppression RSA](#consulter-proposition-suppression-rsa)
  - [Valider la suppression RSA](#valider-suppression-rsa)

- [Transmettre une demande de radiation](#transmettre-une-demande-de-radiation)
  - [Récupérer l'access token](#recuperer-access-token-radiation)
  - [Forger un jeton usager](#forger-jeton-usager-radiation)
  - [Transmettre la décision de sanction](#transmettre-decision-sanction)
  - [Consulter la décision de sanction](#consulter-decision-sanction)
  - [Transmettre la demande de radiation](#transmettre-demande-radiation-step)

---

## Sources OpenAPI

| Nom | Type | URL |
|-----|------|-----|
| FranceTravailAuthCC | OpenAPI | `./access_token_CC_openapi.yaml` |
| rechercheUsager | OpenAPI | `file://API_LPE_RechercheUsager_openapi.json` |
| activitesOperationnelles | OpenAPI | `file://API_LPE_ActivitesOperationnelles_v2_openapi.json` |
| sanctionRSA | OpenAPI | `file://API_LPE_SanctionsRSA_v1.1_openapi.json` |

---

## Valider une proposition de suspension de RSA {#valider-une-proposition-de-suspension-de-rsa}

**Résumé :** Valider une proposition de suspension de RSA pour un bRSA accompagné par FT

**Description :** Ce workflow décrit comment un partenaire confirme une proposition de suspension de RSA. France Travail, en tant que référent d'accompagnement d'un usager bRSA, effectue les contrôles de respect des engagements et peut être amené à transmettre des propositions de sanction pour cet usager en cas de manquement. Le partenaire a alors la responsabilité de confirmer ou d'infirmer la proposition de sanction. Ce workflow décrit un cas de proposition de suspension de RSA ; il est à noter que le workflow est quasi identique pour une proposition de suppression de RSA (sans l'étape d'instruction et au paramétrage près).

### Inputs

| Paramètre | Type | Requis |
|-----------|------|--------|
| `client_id` | string | Oui |
| `client_secret` | string | Oui |

### Steps

---

#### Récupérer l'access token {#recuperer-access-token-suspension}

**Description :** Récupération d'un access token via le flux OAuth2 client_credentials avec les scopes adéquats.

**Endpoint :** `POST https://entreprise.francetravail.fr/connexion/oauth2/access_token?realm=/agent`

**Body (application/x-www-form-urlencoded) :**

| Paramètre | Valeur |
|-----------|--------|
| `grant_type` | `client_credentials` |
| `client_id` | `$inputs.client_id` |
| `client_secret` | `$inputs.client_secret` |
| `scope` | `profil_accedant api_rechercher-usagerv2 rechercheusager api_activites-operationnellesv2 activitesCD api_gestion-sanctions-rsav1 gestiondesmanquements` |

**Critères de succès :**
- Status code = 200
- `$response.body.access_token` ≠ null

**Outputs :**
- <a name="output-accessToken-suspension"></a>`accessToken` : `$response.body.access_token`

**En cas d'erreur :**
- Si status code = 400 → fin du workflow (code erreur : `invalid_client`)

---

#### Récupérer les propositions de suspension RSA à instruire {#recuperer-propositions-suspension-rsa-a-instruire}

**Description :** Récupération de la liste des propositions de suspension RSA à instruire. Seules les sanctions de type suspension RSA sont remontées (`codeActivite=ACT366`), mais il est possible de récupérer toutes les activités autour des sanctions via le `codeFamille GLI100`.

**Endpoint :** `GET https://api.francetravail.io/partenaire/activites-operationnelles/v2/activites/chercher`

**Paramètres :**

| Nom | Emplacement | Valeur |
|-----|-------------|--------|
| `Authorization` | header | `Bearer` [accessToken](#output-accessToken-suspension) |
| `codeActivite` | query | `ACT366` |

**Critères de succès :**
- Status code = 200
- `$response.body.activites[0].usager[0].typeIdentifiant == "numeroFranceTravail"`
- `$response.body.activites[0].attributsSpecifiques[0].nom == "identifiantConsequenceSanction"`

**Outputs :**
- <a name="output-numeroFranceTravail-suspension"></a>`numeroFranceTravail` : `$response.body.activites[0].usager[0].identifiant`
- <a name="output-identifiantConsequenceSanction-suspension"></a>`identifiantConsequenceSanction` : `$response.body.activites[0].attributsSpecifiques[0].valeur`

**Branchements :**
- Si `$response.body.activites.length > 0` → aller à [Forger un jeton usager](#forger-jeton-usager-suspension)
- Si `$response.body.activites.length == 0` → fin du workflow

---

#### Forger un jeton usager {#forger-jeton-usager-suspension}

**Description :** Génération d'un jeton d'habilitation qui permettra au partenaire de consulter les données de sanctions par usager. Cette étape doit être exécutée pour chaque usager retourné à l'étape précédente.

**Endpoint :** `POST https://api.francetravail.io/partenaire/rechercher-usager/v2/usagers/par-numero-francetravail`

**Paramètres :**

| Nom | Emplacement | Valeur |
|-----|-------------|--------|
| `Authorization` | header | `Bearer` [accessToken](#output-accessToken-suspension) |

**Body (application/json) :**
```json
{
  "numeroFranceTravail": "$steps.recuperer-propositions-suspension-RSA-a-instruire.outputs.numeroFranceTravail"
}
```

**Critères de succès :**
- Status code = 200
- `$response.body.jetonUsager` ≠ null

**Outputs :**
- <a name="output-jetonUsager-suspension"></a>`jetonUsager` : `$response.body.jetonUsager`

**En cas d'erreur :**
- Si status code = 403 ET `$response.body.codeRetour == "R002"` → retour à [Récupérer les propositions de suspension RSA à instruire](#recuperer-propositions-suspension-rsa-a-instruire)

---

#### Consulter la proposition de sanction {#consulter-proposition-sanction}

**Description :** Consultation de la proposition de sanction d'un usager. Cette étape doit être répétée pour chaque usager retourné à l'étape [Récupérer les propositions de suspension RSA à instruire](#recuperer-propositions-suspension-rsa-a-instruire).

**Endpoint :** `GET https://api.francetravail.io/partenaire/gestion-sanctions-rsa/v1/detailManquement`

**Paramètres :**

| Nom | Emplacement | Valeur |
|-----|-------------|--------|
| `Authorization` | header | `Bearer` [accessToken](#output-accessToken-suspension) |
| `ft-jeton-usager` | header | [jetonUsager](#output-jetonUsager-suspension) |
| `identifiantSanction` | query | [identifiantConsequenceSanction](#output-identifiantConsequenceSanction-suspension) |

**Critères de succès :**
- Status code = 200

**Outputs :**
- <a name="output-modaliteProposee-suspension"></a>`modaliteProposee` : `$response.body.modaliteProposee`
- <a name="output-montantPropose-suspension"></a>`montantPropose` : `$response.body.montantPropose`
- <a name="output-typeConsequenceSanction-suspension"></a>`typeConsequenceSanction` : `$response.body.typeConsequenceSanction`
- <a name="output-dureeProposee-suspension"></a>`dureeProposee` : `$response.body.dureeProposee`

---

#### Instruire la proposition de sanction {#instruire-proposition-sanction}

**Description :** Instruire la proposition de sanction d'un usager. Cette étape doit être répétée pour chaque usager retourné à l'étape précédente. Ici on saisit (i.e. on indique qu'on instruit) la sanction proposée (`SAISIE_SANCTION`). Il est aussi possible de refuser l'instruction (`REFUS_SAISIE_SANCTION`). Attention : `REFUS_SAISIE_SANCTION` signifie que la sanction est validée telle que préconisée par FranceTravail.

**Endpoint :** `POST https://api.francetravail.io/partenaire/gestion-sanctions-rsa/v1/evenementSanctionRSA`

**Paramètres :**

| Nom | Emplacement | Valeur |
|-----|-------------|--------|
| `Authorization` | header | `Bearer` [accessToken](#output-accessToken-suspension) |
| `ft-jeton-usager` | header | [jetonUsager](#output-jetonUsager-suspension) |

**Body (application/json) :**
```json
{
  "identifiantConsequenceSanction": "$steps.recuperer-propositions-suspension-RSA-a-instruire.outputs.identifiantConsequenceSanction",
  "evenement": {
    "typeEvenement": "SAISIE_SANCTION",
    "dateEvenement": "<YYYY-MM-dd>"
  },
  "typeConsequenceSanctionValide": "$steps.consulter-proposition-sanction-usager.outputs.typeConsequenceSanction"
}
```

**Critères de succès :**
- Status code = 200
- `$response.body.codeSortie == "C001"`

---

#### Valider la suspension RSA {#valider-suspension-rsa}

**Description :** Valider la proposition de sanction d'un usager. Cette étape doit être répétée pour chaque usager retourné à l'étape précédente. Ici on valide une proposition sanction (`VALID_SANCTION`), mais il est possible de la refuser (`REFUS_SANCTION`). On peut choisir de conserver les modalités de sanction proposées par FranceTravail, ou les adapter (`modaliteDecisionValidee` = M ou P ; `montantValide` : en euros si modalité M ou un taux entre 30 et 100 si modalité P ; `dureeValidee` : nombre de mois entre 1 et 4).

**Endpoint :** `POST https://api.francetravail.io/partenaire/gestion-sanctions-rsa/v1/evenementSanctionRSA`

**Paramètres :**

| Nom | Emplacement | Valeur |
|-----|-------------|--------|
| `Authorization` | header | `Bearer` [accessToken](#output-accessToken-suspension) |
| `ft-jeton-usager` | header | [jetonUsager](#output-jetonUsager-suspension) |

**Body (application/json) :**
```json
{
  "identifiantConsequenceSanction": "$steps.recuperer-propositions-suspension-RSA-a-instruire.outputs.identifiantConsequenceSanction",
  "evenement": {
    "typeEvenement": "VALID_SANCTION",
    "dateEvenement": "<YYYY-MM-dd>"
  },
  "typeConsequenceSanctionValide": "$steps.consulter-proposition-sanction-usager.outputs.typeConsequenceSanction",
  "modaliteDecisionValidee": "$steps.consulter-proposition-sanction-usager.outputs.modaliteProposee",
  "montantValide": "$steps.consulter-proposition-sanction-usager.outputs.montantPropose",
  "dureeValidee": "$steps.consulter-proposition-sanction-usager.outputs.dureeProposee"
}
```

**Critères de succès :**
- Status code = 200
- `$response.body.codeSortie == "C001"`

---

## Valider une proposition de suppression de RSA {#valider-une-proposition-de-suppression-de-rsa}

**Résumé :** Valider une proposition de suppression de RSA pour un bRSA accompagné par FT

**Description :** Ce workflow décrit comment un partenaire confirme une proposition de suppression de RSA initiée par France Travail. France Travail, en tant que référent d'accompagnement d'un usager bRSA, effectue les contrôles de respect des engagements et peut être amené à transmettre des propositions de sanction pour cet usager en cas de manquement. Le partenaire a alors la responsabilité de confirmer ou d'infirmer la proposition de sanction. À noter qu'il est quasi identique au workflow pour une levée de suspension RSA, au paramétrage près (`codeActivite` : ACT369, `typeEvenement` : VALID_LEVEE_SUSP ou REFUS_LEVEE_SUSP).

### Inputs

| Paramètre | Type | Requis |
|-----------|------|--------|
| `client_id` | string | Oui |
| `client_secret` | string | Oui |

### Steps

---

#### Récupérer l'access token {#recuperer-access-token-suppression}

**Description :** Récupération d'un access token via le flux OAuth2 client_credentials avec les scopes adéquats.

**Endpoint :** `POST https://entreprise.francetravail.fr/connexion/oauth2/access_token?realm=/agent`

**Body (application/x-www-form-urlencoded) :**

| Paramètre | Valeur |
|-----------|--------|
| `grant_type` | `client_credentials` |
| `client_id` | `$inputs.client_id` |
| `client_secret` | `$inputs.client_secret` |
| `scope` | `profil_accedant api_rechercher-usagerv2 rechercheusager api_activites-operationnellesv2 activitesCD api_gestion-sanctions-rsav1 gestiondesmanquements` |

**Critères de succès :**
- Status code = 200
- `$response.body.access_token` ≠ null

**Outputs :**
- <a name="output-accessToken-suppression"></a>`accessToken` : `$response.body.access_token`

**En cas d'erreur :**
- Si status code = 400 → fin du workflow (code erreur : `invalid_client`)

---

#### Récupérer les propositions de suppression RSA à traiter {#recuperer-propositions-suppression-rsa-a-traiter}

**Description :** Récupération de la liste des propositions de suppression RSA à traiter.

**Endpoint :** `GET https://api.francetravail.io/partenaire/activites-operationnelles/v2/activites/chercher`

**Paramètres :**

| Nom | Emplacement | Valeur |
|-----|-------------|--------|
| `Authorization` | header | `Bearer` [accessToken](#output-accessToken-suppression) |
| `codeActivite` | query | `ACT370` |

**Critères de succès :**
- Status code = 200
- `$response.body.activites[0].usager[0].typeIdentifiant == "numeroFranceTravail"`
- `$response.body.activites[0].attributsSpecifiques[0].nom == "identifiantConsequenceSanction"`

**Outputs :**
- <a name="output-numeroFranceTravail-suppression"></a>`numeroFranceTravail` : `$response.body.activites[0].usager[0].identifiant`
- <a name="output-identifiantConsequenceSanction-suppression"></a>`identifiantConsequenceSanction` : `$response.body.activites[0].attributsSpecifiques[0].valeur`

**Branchements :**
- Si `$response.body.activites.length > 0` → aller à [Forger un jeton usager](#forger-jeton-usager-suppression)
- Si `$response.body.activites.length == 0` → fin du workflow

---

#### Forger un jeton usager {#forger-jeton-usager-suppression}

**Description :** Génération d'un jeton d'habilitation qui permettra au partenaire de consulter les données de sanctions par usager. Cette étape doit être exécutée pour chaque usager retourné à l'étape précédente.

**Endpoint :** `POST https://api.francetravail.io/partenaire/rechercher-usager/v2/usagers/par-numero-francetravail`

**Paramètres :**

| Nom | Emplacement | Valeur |
|-----|-------------|--------|
| `Authorization` | header | `Bearer` [accessToken](#output-accessToken-suppression) |

**Body (application/json) :**
```json
{
  "numeroFranceTravail": "$steps.recuperer-propositions-suppression-RSA-a-traiter.outputs.numeroFranceTravail"
}
```

**Critères de succès :**
- Status code = 200
- `$response.body.jetonUsager` ≠ null

**Outputs :**
- <a name="output-jetonUsager-suppression"></a>`jetonUsager` : `$response.body.jetonUsager`

**En cas d'erreur :**
- Si status code = 403 ET `$response.body.codeRetour == "R002"` → retour à [Récupérer les propositions de suppression RSA à traiter](#recuperer-propositions-suppression-rsa-a-traiter)

---

#### Consulter la proposition de suppression RSA {#consulter-proposition-suppression-rsa}

**Description :** Consultation de la proposition de sanction d'un usager. Cette étape doit être répétée pour chaque usager retourné à l'étape [Récupérer les propositions de suppression RSA à traiter](#recuperer-propositions-suppression-rsa-a-traiter).

**Endpoint :** `GET https://api.francetravail.io/partenaire/gestion-sanctions-rsa/v1/detailManquement`

**Paramètres :**

| Nom | Emplacement | Valeur |
|-----|-------------|--------|
| `Authorization` | header | `Bearer` [accessToken](#output-accessToken-suppression) |
| `ft-jeton-usager` | header | [jetonUsager](#output-jetonUsager-suppression) |
| `identifiantSanction` | query | [identifiantConsequenceSanction](#output-identifiantConsequenceSanction-suppression) |

**Critères de succès :**
- Status code = 200

**Outputs :**
- <a name="output-modaliteProposee-suppression"></a>`modaliteProposee` : `$response.body.modaliteProposee`
- <a name="output-montantPropose-suppression"></a>`montantPropose` : `$response.body.montantPropose`
- <a name="output-typeConsequenceSanction-suppression"></a>`typeConsequenceSanction` : `$response.body.typeConsequenceSanction`
- <a name="output-dureeProposee-suppression"></a>`dureeProposee` : `$response.body.dureeProposee`

---

#### Valider la suppression RSA {#valider-suppression-rsa}

**Description :** Valider la proposition de sanction d'un usager. Cette étape doit être répétée pour chaque usager retourné à l'étape précédente. Ici on valide une proposition sanction (`VALID_SANCTION`), mais il est possible de la refuser (`REFUS_SANCTION`). On peut choisir de conserver les modalités de sanction proposées par FranceTravail, ou les modifier (`modaliteDecisionValidee` = M ou P ; `montantValide` : en euros si modalité M ou un taux entre 30 et 100 si modalité P ; `dureeValidee` : nombre de mois entre 1 et 4).

**Endpoint :** `POST https://api.francetravail.io/partenaire/gestion-sanctions-rsa/v1/evenementSanctionRSA`

**Paramètres :**

| Nom | Emplacement | Valeur |
|-----|-------------|--------|
| `Authorization` | header | `Bearer` [accessToken](#output-accessToken-suppression) |
| `ft-jeton-usager` | header | [jetonUsager](#output-jetonUsager-suppression) |

**Body (application/json) :**
```json
{
  "identifiantConsequenceSanction": "$steps.recuperer-propositions-suppression-RSA-a-traiter.outputs.identifiantConsequenceSanction",
  "evenement": {
    "typeEvenement": "VALID_SANCTION",
    "dateEvenement": "<YYYY-MM-dd>"
  },
  "typeConsequenceSanctionValide": "$steps.consulter-proposition-suppression-RSA.outputs.typeConsequenceSanction",
  "modaliteDecisionValidee": "$steps.consulter-proposition-suppression-RSA.outputs.modaliteProposee",
  "montantValide": "$steps.consulter-proposition-suppression-RSA.outputs.montantPropose",
  "dureeValidee": "$steps.consulter-proposition-suppression-RSA.outputs.dureeProposee"
}
```

**Critères de succès :**
- Status code = 200
- `$response.body.codeSortie == "C001"`

---

## Transmettre une demande de radiation {#transmettre-une-demande-de-radiation}

**Résumé :** Transmettre une demande de radiation d'un usager

**Description :** Ce workflow décrit comment un partenaire partage une décision de sanction RSA prise pour un usager qu'il accompagne, si la décision légitime une demande de radiation de la liste des demandeurs d'emploi. Dans ce cas ce n'est pas FranceTravail qui est à l'origine de la proposition de sanction, mais le partenaire. Ce workflow est toutefois adaptable pour un cas de demande de radiation faisant suite à une décision prise pour un bRSA accompagné par France Travail : on n'effectue pas l'étape de transmission de la décision, et on indique à l'étape de demande de radiation l'identifiant de la sanction qui a été validée suite à une proposition de sanction émise par France Travail.

### Inputs

| Paramètre | Type | Requis |
|-----------|------|--------|
| `client_id` | string | Oui |
| `client_secret` | string | Oui |

### Steps

---

#### Récupérer l'access token {#recuperer-access-token-radiation}

**Description :** Récupération d'un access token via le flux OAuth2 client_credentials avec les scopes adéquats.

**Endpoint :** `POST https://entreprise.francetravail.fr/connexion/oauth2/access_token?realm=/agent`

**Body (application/x-www-form-urlencoded) :**

| Paramètre | Valeur |
|-----------|--------|
| `grant_type` | `client_credentials` |
| `client_id` | `$inputs.client_id` |
| `client_secret` | `$inputs.client_secret` |
| `scope` | `profil_accedant api_rechercher-usagerv2 rechercheusager api_gestion-sanctions-rsav1 gestiondesmanquements decisionSanctionPartenaire demandeRadiation` |

**Critères de succès :**
- Status code = 200
- `$response.body.access_token` ≠ null

**Outputs :**
- <a name="output-accessToken-radiation"></a>`accessToken` : `$response.body.access_token`

**En cas d'erreur :**
- Si status code = 400 → fin du workflow (code erreur : `invalid_client`)

---

#### Forger un jeton usager {#forger-jeton-usager-radiation}

**Description :** Génération d'un jeton d'habilitation qui permettra au partenaire de consulter les données de sanctions par usager. Cette étape doit être exécutée pour chaque usager à traiter.

**Endpoint :** `POST https://api.francetravail.io/partenaire/rechercher-usager/v2/usagers/par-numero-francetravail`

**Paramètres :**

| Nom | Emplacement | Valeur |
|-----|-------------|--------|
| `Authorization` | header | `Bearer` [accessToken](#output-accessToken-radiation) |

**Body (application/json) :**
```json
{
  "numeroFranceTravail": "<numeroFranceTravail de l'usager>"
}
```

**Critères de succès :**
- Status code = 200
- `$response.body.jetonUsager` ≠ null

**Outputs :**
- <a name="output-jetonUsager-radiation"></a>`jetonUsager` : `$response.body.jetonUsager`

**En cas d'erreur :**
- Si status code = 403 ET `$response.body.codeRetour == "R002"` → fin en erreur (usager non rattaché à la structure)

---

#### Transmettre la décision de sanction {#transmettre-decision-sanction}

> **Étape facultative** : à réaliser uniquement si le partenaire est à l'origine de la proposition de sanction (et non France Travail).

**Endpoint :** `POST https://api.francetravail.io/partenaire/gestion-sanctions-rsa/v1/decisionSanctionRSAPartenaire`

**Paramètres :**

| Nom | Emplacement | Valeur |
|-----|-------------|--------|
| `Authorization` | header | `Bearer` [accessToken](#output-accessToken-radiation) |
| `ft-jeton-usager` | header | [jetonUsager](#output-jetonUsager-radiation) |

**Critères de succès :**
- Status code = 200
- `$response.body.codeSortie == "C001"`

**Outputs :**
- <a name="output-identifiantConsequenceSanction-radiation"></a>`identifiantConsequenceSanction` : `$response.body.identifiantConsequenceSanction`

---

#### Consulter la décision de sanction {#consulter-decision-sanction}

**Description :** Vérifier la prise en compte de la décision de sanction.

**Endpoint :** `GET https://api.francetravail.io/partenaire/gestion-sanctions-rsa/v1/detailManquement`

**Paramètres :**

| Nom | Emplacement | Valeur |
|-----|-------------|--------|
| `Authorization` | header | `Bearer` [accessToken](#output-accessToken-radiation) |
| `ft-jeton-usager` | header | [jetonUsager](#output-jetonUsager-radiation) |
| `identifiantSanction` | query | [identifiantConsequenceSanction](#output-identifiantConsequenceSanction-radiation) |

**Critères de succès :**
- Status code = 200

---

#### Transmettre la demande de radiation {#transmettre-demande-radiation-step}

**Description :** Transmettre la demande de radiation des demandeurs d'emploi pour un usager bénéficiaire du RSA non accompagné par FT.

**Endpoint :** `POST https://api.francetravail.io/partenaire/gestion-sanctions-rsa/v1/demandeRadiation`

**Paramètres :**

| Nom | Emplacement | Valeur |
|-----|-------------|--------|
| `Authorization` | header | `Bearer` [accessToken](#output-accessToken-radiation) |
| `ft-jeton-usager` | header | [jetonUsager](#output-jetonUsager-radiation) |

**Body (application/json) :**
```json
{
  "identifiantConsequenceSanction": "$steps.transmettre-decision-sanction-usager.outputs.identifiantConsequenceSanction"
}
```

**Critères de succès :**
- Status code = 200

---

*Document généré le 2026-05-18 — Version 1.0.0*
