# Vous avez un job à faire. On vous montre le montage complet.

Bienvenue sur l'espace d'intégration partenaires de la **plateforme France Travail**.

Chaque cas d'usage est livré comme un **parcours mis en scène de bout en bout** : le processus métier expliqué, l'orchestration prête à l'emploi ([Arazzo](https://spec.openapis.org/arazzo/latest.html)), une collection Postman à rejouer et des exemples de code. Bien plus qu'une liste d'API à assembler soi-même!

---

## 🚢 Les cas d'usage

Commencez par votre métier :

| Le job | Pour qui | État | Entrée |
|---|---|---|---|
| 🚢 Donner suite aux propositions de sanction | Conseil départemental | `pilote` | [→ ouvrir](./cas_d_usage/sanction/) |
| ⏳ Orienter un allocation RSA vers le bon accompagnement | Conseil départemental | `bientôt` | — |

> 🧱 **Les API unitaires** (contrats, référence) sont documentées sur le catalogue officiel : **[francetravail.io](https://francetravail.io)**. Ici, on ne duplique pas les briques — on montre comment les monter.

---

## Comment lire cet espace

| Symbole | Signification |
|---|---|
| 🚢 | **Cas d'usage** — un parcours métier complet, mis en scène. Commencez ici. |
| 🧱 | **Brique** — un contrat d'API unitaire, documenté sur [francetravail.io](https://francetravail.io). |
| ⏳ | **Bientôt** — en cours de mise en scène, pas encore publié. |

Chaque dossier de cas d'usage est un **îlot autonome** : tout ce qu'il faut pour comprendre, rejouer et intégrer le parcours est à l'intérieur.

---

## Structure du dépôt

```
cas_d_usage/          les parcours partenaires (1 dossier = 1 cas d'usage)
transverse/           ce qui sert tous les parcours (onboarding, accès, glossaire)
```

## Poser une question

Ouvrez une [issue](../../issues) en précisant le cas d'usage concerné dans le titre : `[sanction] Ma question`.

---

## Règles de l'espace (pour les contributeurs)

1. **1 dossier = 1 cas d'usage partenaire**, nommé en langage métier (`orientation-allocataire-rsa`), jamais en langage API.
2. **N'entre au catalogue des cas d'usage que ce qui est mis en scène** : un cas d'usage est publié dans le tableau ci-dessus uniquement quand sa photo (README), sa notice (Arazzo) et son rejouable (Postman) existent.
3. **Les briques restent dans le catalogue API** : aucun contrat OpenAPI n'est dupliqué ici — on référence [francetravail.io](https://francetravail.io) en épinglant les versions validées.
4. **Nouveau cas d'usage** : copier [`cas_d_usage/_template/`](./cas_d_usage/_template/), suivre son README.

_Espace en construction — 1 parcours publié, d'autres arrivent._