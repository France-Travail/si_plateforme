# Le processus métier — Donner suite aux sanctions RSA

> Quadrant : **Explanation** — ce document explique la finalité et la cohérence du parcours. Le détail des appels et de leur enchaînement est décrit dans le [parcours Arazzo](../parcours_arazzo_sanctions-v1.0.md).

## Contexte et finalité

Dans le cadre de la Loi pour le plein emploi, France Travail et les conseils départementaux doivent coordonner leurs actions lorsqu'un bénéficiaire du RSA ne respecte pas ses engagements. Le système d'information du conseil départemental ne doit donc pas seulement appeler une API de sanction : il doit être capable de repérer un dossier à traiter, d'identifier l'usager concerné, d'accéder aux éléments du manquement, de prendre une décision puis de la transmettre au bon acteur.

Le parcours principal débute lorsque France Travail, en qualité de référent d'accompagnement, constate un manquement et émet une proposition de suspension, de suppression ou de levée de suspension du RSA. Cette proposition devient une activité à traiter par le conseil départemental. Celui-ci consulte le contexte et les conséquences proposées, instruit le dossier lorsque cela est nécessaire, puis accepte, refuse ou adapte la proposition. La décision est alors enregistrée dans le système de France Travail afin que le dossier partagé reflète la suite donnée par le département.

Un second parcours couvre le cas où le conseil départemental est lui-même à l'origine d'une décision de sanction pour un bénéficiaire qu'il accompagne. Il transmet cette décision à France Travail et peut, lorsque la décision le justifie, demander la radiation de l'usager de la liste des demandeurs d'emploi. Si la sanction provient déjà de France Travail, sa transmission n'est pas répétée : son identifiant est directement réutilisé pour la demande de radiation.

La valeur du parcours réside ainsi dans la **composition transverse de plusieurs API** :

1. les activités opérationnelles signalent les propositions en attente et fournissent les identifiants nécessaires ;
2. la recherche usager vérifie que l'usager est rattaché à la structure et délivre le jeton autorisant l'accès à son dossier ;
3. la gestion des sanctions expose le manquement, recueille la décision du département et, le cas échéant, transmet une demande de radiation.

Les sorties de chaque étape alimentent la suivante. Le numéro France Travail relie l'activité à l'usager, le jeton usager sécurise l'accès à ses données et l'identifiant de conséquence de sanction assure le suivi d'une même proposition jusqu'à sa décision. Le partenaire n'a donc pas à reconstruire seul les correspondances, les dépendances et les contrôles nécessaires entre ces domaines fonctionnels.

## Les acteurs

| Acteur | Rôle dans le parcours |
|---|---|
| Conseil départemental | Instruit les propositions concernant les bénéficiaires du RSA, confirme, refuse ou adapte la sanction et transmet les décisions dont il est à l'origine. Il peut également demander une radiation lorsque la décision prise le justifie. |
| France Travail | Détecte les manquements des bénéficiaires qu'il accompagne, émet les propositions de sanction, met les activités à disposition du département et enregistre les décisions reçues. |
| Bénéficiaire du RSA | Est la personne concernée par le manquement et par les conséquences de la décision. Son rattachement à la structure conditionne l'accès du partenaire au dossier. |

## Règles de gestion clés

| Règle | Impact sur l'intégration |
|---|---|
| Une activité représente une proposition à traiter | Le partenaire parcourt les activités retournées et exécute le traitement pour chaque usager et chaque conséquence de sanction. L'absence d'activité termine normalement le parcours. |
| L'accès au détail d'un dossier est habilité par usager | Le jeton usager doit être obtenu à partir du numéro France Travail avant de consulter ou de modifier une sanction. Il ne se substitue pas au jeton d'accès du partenaire : les deux sont nécessaires. |
| L'identifiant de conséquence de sanction porte la continuité du dossier | Cet identifiant, récupéré avec l'activité ou lors de la transmission d'une décision partenaire, doit être propagé jusqu'aux appels de décision et de radiation. |
| La suspension comporte une phase d'instruction | Le département indique d'abord qu'il instruit la proposition avant de rendre sa décision. Attention : refuser la saisie en instruction signifie que la proposition de France Travail est retenue telle quelle. |
| La décision dépend du type de proposition | Une suspension ou une suppression peut être acceptée, refusée ou adaptée. Une levée de suspension peut être acceptée ou refusée. Les modalités, le montant ou le taux et la durée transmis doivent rester cohérents avec la décision prise. |
| L'origine de la sanction détermine le parcours de radiation | Pour une décision émise par le département, celle-ci est d'abord transmise à France Travail. Pour une proposition France Travail déjà validée, l'étape est omise et l'identifiant existant est réutilisé. |
| Le succès d'un appel conditionne la suite | Une étape n'est franchie que si la réponse attendue est obtenue. Une erreur d'authentification, d'habilitation ou d'enregistrement ne doit pas être assimilée à une décision traitée. |

## Cas limites connus

- **Aucune proposition en attente** : le parcours se termine sans traitement ; il ne s'agit pas d'une erreur.
- **Usager non rattaché à la structure** : le jeton usager ne peut pas être délivré. La proposition ne peut pas être consultée par cette structure et doit être écartée du traitement courant ou signalée selon le contexte d'exécution.
- **Identifiants absents ou incohérents** : sans numéro France Travail ou identifiant de conséquence de sanction, le chaînage ne peut pas se poursuivre. Le dossier ne doit pas être considéré comme traité.
- **Refus d'instruire une suspension** : cette action ne correspond pas au refus de la sanction ; elle revient à accepter la préconisation de France Travail sans instruction complémentaire.
- **Demande de radiation sans sanction identifiable** : la demande ne peut être transmise tant qu'une décision partenaire n'a pas produit d'identifiant ou qu'une sanction France Travail validée n'a pas été identifiée.
