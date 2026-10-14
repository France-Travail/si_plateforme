# Artefacts à envisager — faire de ce dossier un îlot autonome

Cette checklist inventorie tout ce qui peut rendre ce cas d'usage **auto-suffisant pour un développeur partenaire** : arriver, comprendre le métier, rejouer le parcours, intégrer, et vivre avec dans la durée — sans solliciter le support.

**Mode d'emploi** : à l'instanciation du template, passer la liste, cocher l'existant, prioriser le reste. Tout n'est pas requis — le tri se fait avec deux filtres :
- **Filtre déflexion** : cet artefact évite-t-il des tickets ? (proportionnel au volume de partenaires)
- **Filtre contour** : sert-il à *monter le bateau* ? (sinon, il n'entre pas dans la boîte)

Priorités : `MVP` = requis pour publier au rayon · `V1` = avant ouverture large · `V2` = amélioration continue.

---

## 1 · Promettre — le job to be done

| Artefact | À quoi ça sert | Priorité | ✔ |
|---|---|---|---|
| `README.md` — le job, le parcours Mermaid, avant/après, golden path | La première chose vue ; décide si le partenaire reste | `MVP` | ☐ |
| Métriques avant/après réelles (remplacer les `[X]`) | Transforme la promesse en preuve chiffrée | `V1` | ☐ |
| Témoignage / mini case study d'un partenaire pilote | Le bateau qui navigue — preuve par les pairs | `V2` | ☐ |
| Image de partage (social preview) | La vignette dans Teams/mail | `V2` | ☐ |

## 2 · Comprendre — le pourquoi métier (`processus-metier/`)

*Quadrant Diataxis : **Explanation***

| Artefact | À quoi ça sert | Priorité | ✔ |
|---|---|---|---|
| Fiche processus : contexte, déclencheur, finalité, périmètre | Le partenaire comprend *pourquoi* ce parcours existe | `MVP` | ☐ |
| Acteurs & rôles (qui fait quoi : partenaire, FT, usager) | Évite les contresens d'implémentation | `MVP` | ☐ |
| Règles de gestion clés (éligibilité, délais réglementaires, cas d'exclusion) => Lien avec le contrat data ? | Le savoir qui n'est dans aucun contrat d'API | `V1` | ☐ |
| Glossaire local du cas d'usage (termes métier ↔ champs techniques) | Le pont langage métier / payload | `V1` | ☐ |
| Diagramme de séquence acteurs (Mermaid `sequenceDiagram`) | La vue dynamique quand le flowchart ne suffit plus | `V1` | ☐ |
| Cas limites & parcours d'exception (que se passe-t-il si…) | La FAQ écrite avant les questions | `V2` | ☐ |
| Modèle des données échangées (les objets métier manipulés, cardinalités) | Comprendre les payloads sans lire 6 contrats | `V2` | ☐ |

## 3 · Monter — la notice (`parcours.arazzo.yaml` + annexes)

*Quadrant Diataxis : **How-to** (le montage) + **Reference** (les tableaux)*

| Artefact | À quoi ça sert | Priorité | ✔ |
|---|---|---|---|
| `parcours.arazzo.yaml` — le workflow complet, étapes + dépendances | LA notice ; source de vérité de l'orchestration | `MVP` | ☐ |
| Tableau des versions d'API épinglées (dans le README) | Le parcours est validé contre du concret, pas du `latest` | `MVP` | ☐ |
| Pré-requis d'accès : conventionnement, FT Connect, scopes requis | Le partenaire sait ce qu'il doit obtenir *avant* de coder | `MVP` | ☐ |
| Matrice des erreurs par étape (code, cause probable, action) | Le premier réflexe au lieu du premier ticket | `V1` | ☐ |
| Diagramme de séquence technique (appels, tokens, corrélation) | Pour les archis côté partenaire | `V1` | ☐ |
| Lint Arazzo/Spectral en CI (le gate de qualité, visible) | L'espace montre les pratiques qu'il prêche | `V1` | ☐ |
| Guide des données propagées (quel output nourrit quel input) | Rend le YAML lisible par un humain pressé | `V2` | ☐ |

## 4 · Rejouer — le parcours en main (`postman/`)

*Quadrant Diataxis : **Tutorial** (le premier succès guidé)*

| Artefact | À quoi ça sert | Priorité | ✔ |
|---|---|---|---|
| Collection Postman du parcours, ordonnée étape par étape | Le premier « ça marche » sans écrire de code | `MVP` | ☐ |
| Environnement Postman (variables : URLs, credentials à compléter) | Import → run, sans lire de doc | `MVP` | ☐ |
| Jeu de données de référence anonymisé, **IDs stables** | Rejouable de façon déterministe — la réponse court-terme à l'absence de sandbox | `V1` | ☐ |
| Tutoriel « votre premier parcours en 15 minutes » (pas-à-pas guidé) | L'onboarding en autonomie — déflexion maximale | `V1` | ☐ |
| Mock du parcours (ex. Prism sur les contrats) pour travailler hors-ligne | Développer sans dépendre des environnements FT | `V2` | ☐ |

## 5 · Intégrer — le code (`exemples/`)

*Quadrant Diataxis : **How-to***

| Artefact | À quoi ça sert | Priorité | ✔ |
|---|---|---|---|
| Exemple d'intégration commenté, 1 langage (celui du 1er partenaire pilote) | Le point de départ copiable | `MVP` | ☐ |
| Second langage (java/node/.NET selon le parc des partenaires) | Couvrir le parc réel, pas le parc idéal | `V1` | ☐ |
| Patrons transverses appliqués : auth FT Connect, retry, pagination, corrélation | Les pièges classiques, résolus une fois pour tous | `V1` | ☐ |
| Checklist de mise en production (points de contrôle avant go-live) | Sécurise le passage prod, réduit les incidents entrants | `V1` | ☐ |
| Tests d'exemple (le parcours vérifié automatiquement) | L'exemple qui ne pourrit pas | `V2` | ☐ |

## 6 · Vivre avec — la durée

*Quadrant Diataxis : **Reference***

| Artefact | À quoi ça sert | Priorité | ✔ |
|---|---|---|---|
| `CHANGELOG.md` du cas d'usage + tags namespacés (`rsa/v1.2`) | Le partenaire sait ce qui a changé sans demander | `V1` | ☐ |
| Politique de compatibilité (qu'est-ce qu'un breaking change du *parcours*) | Le contrat de confiance dans la durée | `V1` | ☐ |
| Limites & quotas applicables au parcours | Évite les découvertes en prod | `V1` | ☐ |
| FAQ alimentée par les issues résolues | Chaque ticket répondu devient un ticket évité | `V2` | ☐ |
| Canal de contact / engagement de réponse | Le filet quand l'autonomie ne suffit pas | `V2` | ☐ |

---

## Ce qui n'entre PAS dans la boîte (rappel du contour)

- Les **contrats OpenAPI** → [francetravail.io](https://francetravail.io) (on épingle les versions, on ne duplique pas)
- L'**onboarding transverse** (conventionnement, FT Connect générique) → `transverse/`
- Les supports de présentation, CR, com interne → ailleurs (ils parlent *du* bateau, ils ne servent pas à le monter)
