# Développez une application full Stack

## Prérequis

Assurez-vous d'avoir les éléments suivants installés sur votre machine :

- **Node.js** (version x.x.x) : [Télécharger Node.js](https://nodejs.org/)
- **Angular CLI** (version x.x.x) : `npm install -g @angular/cli`
- **Java JDK** (version x.x.x) : [Télécharger le JDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- **Maven** (version x.x.x) : [Télécharger Maven](https://maven.apache.org/download.cgi)
- **Git** : [Télécharger Git](https://git-scm.com/)

## Installation

### 1. Cloner le projet

```bash
git clone https://github.com/votre-utilisateur/votre-repo.git
cd votre-repo
```

### 2. Installer la partie Frontend (Angular)

1. Accédez au répertoire `frontend` :

   ```bash
   cd frontend
   ```

2. Installez les dépendances avec npm :

   ```bash
   npm install
   ```

### 3. Installer la partie Backend (Spring Boot)

1. Accédez au répertoire `backend` :

   ```bash
   cd ../backend
   ```

2. Compilez le projet Maven :

   ```bash
   mvn clean install
   ```

### 4. Configuration de la base de données

Assurez-vous de configurer les paramètres de la base de données dans le fichier `application.properties` ou `application.yml` du répertoire `backend/src/main/resources`. Voici un exemple de configuration :

```properties
# Exemples de propriétés pour une base de données MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/nom_de_la_base
spring.datasource.username=nom_utilisateur
spring.datasource.password=mot_de_passe
spring.jpa.hibernate.ddl-auto=update
```

### 5. Lancer l'application

#### Lancer le Backend

1. Dans le répertoire `backend`, démarrez le serveur Spring Boot :

   ```bash
   mvn spring-boot:run
   ```

   Cela démarre le serveur backend sur `http://localhost:8080`.

#### Lancer le Frontend

1. Accédez au répertoire `frontend` :

   ```bash
   cd ../frontend
   ```

2. Démarrez l'application Angular :

   ```bash
   ng serve
   ```

   Cela démarre le serveur frontend sur `http://localhost:4200`.

### 6. Accéder à l'application

Ouvrez votre navigateur et accédez à `http://localhost:4200` pour visualiser l'application.

## Structure du Projet

Décrivez brièvement la structure de votre projet :

```
votre-repo/
│
├── backend/              # Code Spring Boot
│   ├── src/              # Dossier source pour le backend
│   ├── pom.xml           # Fichier Maven pour les dépendances
│   └── ...               # Autres fichiers et dossiers backend
│
├── frontend/             # Code Angular
│   ├── src/              # Dossier source pour le frontend
│   ├── angular.json      # Configuration Angular
│   ├── package.json      # Dépendances npm pour le frontend
│   └── ...               # Autres fichiers et dossiers frontend
│
└── README.md             # Ce fichier README
```

## Fonctionnalités

- **Inscription et connexion des utilisateurs**
- **Gestion des produits/entités**
- **Consultation des données en temps réel**
- **etc.**

## Technologies utilisées

- **Frontend** : Angular, TypeScript, HTML, CSS
- **Backend** : Spring Boot, Java, Maven
- **Base de données** : MySQL/PostgreSQL (ou autre selon votre configuration)

## Auteur

- **Mirabile Thomas** - Étudiant(e) en Openclassroom FullStack
