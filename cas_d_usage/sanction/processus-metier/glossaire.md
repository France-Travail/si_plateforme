# Glossaire — Traitement des propositions de sanction RSA

> Quadrant : **Reference** — le langage de ce cas d'usage. Un terme, une définition, pas de narration.
>
> **Statut des entrées** : `[À VALIDER]` = définition proposée, à confirmer par l'équipe métier · `[À TRANCHER]` = tension de vocabulaire à arbitrer avant publication. Ce glossaire est extrait des ateliers de conception (board Excalidraw « API Gestion des sanctions RSA ») — il capture le langage réellement employé, il ne l'invente pas.

---

## Objets métier

| Terme | Définition | Statut |
|---|---|---|
| **Manquement** | Non-respect par le bénéficiaire d'une obligation de son Contrat d'Engagement. Fait générateur de toute la chaîne de sanction. | `[À VALIDER]` |
| **Conséquence de sanction** | L'objet pivot du modèle : la mesure concrète attachée à un manquement (suspension, suppression, radiation). Un manquement porte 1..N conséquences. **C'est sur les conséquences de sanction que le partenaire agit via l'API**, pas sur le manquement lui-même. | `[À VALIDER]` — définition prioritaire, terme le moins intuitif et le plus central |
| **Sanction** | Résultat d'une décision suite à un manquement de l'usager à ses obligations. Se définit par son type, sa durée (en fonction du manquement) et la décision prise selon les justificatifs portés à l'examen de la situation. | `[À VALIDER]` — voir [tension n°1](#1--sanction-vs-conséquence-de-sanction) |
| **Suspension (du RSA)** | Conséquence de sanction : interruption **temporaire** du versement du RSA. | `[À VALIDER]` |
| **Suppression (du RSA)** | Conséquence de sanction : interruption **définitive** du versement du RSA. ⚠️ Ne pas confondre avec l'*annulation*, qui porte sur la sanction elle-même — voir [tension n°3](#3--suppression-vs-annulation). | `[À VALIDER]` |
| **Levée de suspension** | Fin anticipée d'une suspension en cours, avant son terme prévu. | `[À VALIDER]` |
| **Radiation** | Retrait de la liste des demandeurs d'emploi. Particularité : proposée par le Conseil départemental **à** France Travail — le sens de circulation inverse des autres propositions. | `[À VALIDER]` — fonctionnalité marquée « en cours » à date |
| **Contrat d'Engagement** | Le référentiel des obligations du bénéficiaire, dont le manquement déclenche la chaîne de sanction. | → à définir dans le glossaire transverse (concept commun à plusieurs cas d'usage) |

## Décisions & flux

| Terme | Définition | Statut |
|---|---|---|
| **Proposition** | Demande de décision émise par France Travail vers le Conseil départemental (proposition de suspension, de levée, de suppression). Principe directeur : **FT propose, le CD décide.** | `[À VALIDER]` — voir [tension n°2](#2--proposition-est-bidirectionnelle) |
| **Instruire** | Examiner une proposition de suspension avant décision (code activité ACT366). Étape distincte de *décider* : instruire prépare, décider clôt. | `[À VALIDER]` |
| **Décider (valider / refuser)** | L'acte du Conseil départemental qui clôt une proposition, dans un sens ou dans l'autre. Le geste métier central du cas d'usage. | `[À VALIDER]` |
| **Annulation de sanction** | Retour arrière sur une sanction validée (suspension ou suppression). ⚠️ Ne pas confondre avec la *suppression*, qui est un type de sanction — voir [tension n°3](#3--suppression-vs-annulation). | `[À VALIDER]` |
| **Événement (`typeEvenement`)** | Le véhicule technique par lequel le partenaire transmet sa décision (ex. `VALID_SANCTION`, `REFUS_SANCTION`, `VALID_LEVEE_SUSP`, `ANNULATION_SANCTION`). La table de correspondance complète codes activités ↔ événements fait partie de la référence du cas d'usage. | Référence — voir table dédiée |
| **Code activité (ACT366, ACT367, ACT369, ACT370)** | Position d'une proposition dans son cycle de vie, présent dans le fichier de propositions reçu par le partenaire (ex. ACT366 = proposition de suspension à instruire, ACT367 = suspension à traiter, ACT369 = levée à traiter, ACT370 = suppression à traiter). Jargon interne FT **mais exposé au partenaire** — donc défini ici plutôt que masqué. | `[À VALIDER]` |
| **Organisme payeur (CAF / CCMSA)** | Destinataire final de la décision : l'organisme qui applique la conséquence sur le versement. Sa notification est l'*outcome* visible du parcours. | `[À VALIDER]` |

## Acteurs

| Terme | Définition | Statut |
|---|---|---|
| **Bénéficiaire du RSA (bRSA)** | La personne concernée par le manquement et la sanction. | `[À TRANCHER]` — voir [tension n°4](#4--brsa--usager--allocataire--demandeur-demploi) |
| **Accédant** | L'agent (du Conseil départemental ou d'un délégataire) qui consomme l'API et porte les décisions. | `[À VALIDER]` — terme FT interne, à confirmer côté partenaire |
| **Délégataire** | Organisme agissant pour le compte du Conseil départemental dans l'accompagnement et le traitement des sanctions. | `[À VALIDER]` |
| **Conseil départemental (CD)** | Le partenaire décisionnaire : les décisions de suspension et de suppression du RSA sont prononcées dans tous les cas par le Conseil départemental. | `[À VALIDER]` |

---

## Tensions de vocabulaire à trancher

> Ces tensions sont le vrai livrable de ce glossaire : des ambiguïtés relevées dans les ateliers, à arbitrer **avant** publication au rayon. Chaque arbitrage rendu se reporte dans les définitions ci-dessus et le statut passe à validé.

### 1 · « Sanction » vs « conséquence de sanction »

Le langage courant (et le titre du board) dit **sanction** ; le modèle et l'API agissent sur la **conséquence de sanction**. Si le README parle de l'un et la notice Arazzo de l'autre, le partenaire se perd.
**À trancher** : lequel est le terme public (README, portail) ; l'autre devient terme de modèle (payloads, référence) avec renvoi explicite entre les deux.

### 2 · « Proposition » est bidirectionnelle

France Travail *propose* les sanctions au Conseil départemental — mais le Conseil départemental *propose* la radiation à France Travail. Même mot, deux sens de circulation.
**À trancher** : soit expliciter systématiquement l'émetteur (« proposition FT », « proposition CD »), soit différencier les termes (ex. *proposition* vs *demande de radiation*).

### 3 · « Suppression » vs « annulation »

Homonymie dangereuse : la **suppression** supprime *le RSA* (un type de sanction), l'**annulation** supprime *la sanction* (un retour arrière). Un partenaire pressé les confondra.
**Décision proposée** : conserver les deux termes (ils sont installés) mais rendre les définitions mutuellement référencées (« ne pas confondre avec ») — fait ci-dessus, à confirmer.

### 4 · bRSA / usager / allocataire / demandeur d'emploi

Les ateliers emploient les quatre termes pour la même personne. Le cas d'usage voisin (`orientation-allocataire-rsa`) dit « allocataire ».
**À trancher** : élire **un** terme partenaire unique pour tout l'espace, reléguer les autres en synonymes déconseillés. Décision de niveau **glossaire transverse**, pas locale à cette boîte — à remonter.

---

## Ce qui remonte au glossaire transverse

| Terme | Raison |
|---|---|
| Contrat d'Engagement | Concept commun à plusieurs cas d'usage |
| Le terme élu pour la personne (tension n°4) | Cohérence de tout l'espace partenaires |
| Accédant / Délégataire | Vocabulaire d'accès commun à tous les parcours CD |

---

_Extrait des ateliers de conception · Version 0.1 · Dernière revue : [X]_