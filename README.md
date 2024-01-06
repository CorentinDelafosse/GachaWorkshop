# GachaWorkshop

## Table des Matières

- [Objectif](#objectif)
- [Front-end](#front-end)
- [Back-end](#back-end)
- [Idées supplémentaires](#idées-supplémentaires)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Configuration](#configuration)
- [Utilisation](#utilisation)


## Objectif

Le projet GachaWorkshop vise à développer une application web de gestion de stock pour un magasin. L'application comprend deux interfaces principales : une pour la saisie de nouveaux articles et leur enregistrement dans une base de données MongoDB, et une autre dédiée à la recherche d'articles.

### Front-end

1. **Page d'ajout d'article :**
   - Permet la saisie des informations telles que la désignation et le prix unitaire d'un nouvel article.
   - Enregistre l'article dans la base de données MongoDB.

2. **Page de recherche :**
   - Fournit une interface conviviale pour rechercher des articles existants.
   - Affiche les résultats de la recherche, en récupérant les informations depuis MongoDB ou Redis.

3. **Page d'affichage d'un seul article :**
   - Permet d'afficher les détails d'un article spécifique lorsqu'on clique dessus.
   - Affiche les informations détaillées, y compris la désignation, le prix unitaire, etc.

### Back-end

#### Etape 1

Charger une collection MongoDB avec des articles comprenant leur désignation et leur prix unitaire.

#### Etape 2

Mettre en place un système de recherche permettant de trouver des articles dans la base de données MongoDB.

#### Etape 3

Chaque recherche effectuée est stockée dans une base Redis avec une durée de vie de 1 heure.

#### Etape 4

À chaque nouvelle recherche, le système vérifie si l'article recherché est présent dans Redis. Si c'est le cas, il est récupéré depuis Redis, sinon depuis MongoDB.

### Idées supplémentaires

- **Gestion d'utilisateurs :**
  - Ajouter un système d'authentification pour permettre l'accès et la gestion personnalisée du stock à différents utilisateurs.

- **Suppression d'articles :**
  - Intégrer une fonctionnalité permettant de supprimer des articles du stock.

- **Import d'articles depuis un fichier texte/CSV :**
  - Ajouter la possibilité d'importer plusieurs articles en une seule opération depuis un fichier texte ou CSV.

## Prérequis

Assurez-vous d'avoir installé les dépendances suivantes avant de lancer l'application :
- Node.js
- MongoDB
- Redis

## Installation

1. Clonez le dépôt Git : `git clone https://github.com/votre-utilisateur/GachaWorkshop.git`
2. Naviguez vers le répertoire du projet : `cd GachaWorkshop`
3. Installez les dépendances : `npm install`

## Configuration

Modifiez les fichiers de configuration suivants pour spécifier les paramètres de connexion à MongoDB et Redis :

### Redis

Le fichier de configuration pour Redis se trouve à l'emplacement suivant :
`src/main/java/App/BD/RedisConfig.java`

### MongoDB

Le fichier de configuration pour MongoDB se trouve à l'emplacement suivant :
`src/main/java/App/BD/MongoDB.java`



## Utilisation

Avant de lancer l'application Java, assurez-vous d'avoir Redis démarré sur votre machine.

1. Lancez Redis sur votre machine.

2. Exécutez l'application Java située dans `src/main/java/App/Application.java`.

3. Accédez au site via votre navigateur à l'adresse : `http://localhost:8080`.

   Assurez-vous que le port 8080 n'est pas déjà utilisé sur votre machine.
