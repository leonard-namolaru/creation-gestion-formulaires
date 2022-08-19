/**
* bdd.sql
* @auteur  Huihui Huang, Leonard Namolaru
* @travail_effectué  Tout le code qui apparaît dans ce fichier
* @projet Sujet L2N - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes
*   
*/

CREATE TABLE utilisateur(
   id SERIAL , /* serial = autoincrementing integer */
   utilisateur_type smallint NOT NULL DEFAULT '0', /* 0 - Utilisateur normal, 1 - Administrateur */
   nom varchar(255) NOT NULL,
   utilisateur_prenom varchar(255) NOT NULL,
   email varchar(255) NOT NULL PRIMARY KEY,
   mot_de_passe varchar(56) NOT NULL,
   date_inscription date NOT NULL,
   utilisateur_sexe smallint NOT NULL DEFAULT '0', /* 0 - Homme, 1 - Femme */
   utilisateur_date_naissance date NOT NULL
);

CREATE TABLE l2n1_categories(
   categorie_id SERIAL PRIMARY KEY, /* serial = autoincrementing integer */
   categorie_titre varchar(255) NOT NULL,
   categorie_desc text NOT NULL,
   categorie_nb_groupes smallint NOT NULL DEFAULT '0' /* Le nombre de groupes de questions dans la catégorie */
);

CREATE TABLE l2n1_questions(
   question_id SERIAL PRIMARY KEY, /* serial = autoincrementing integer */
   categorie_id integer NOT NULL,
   question_groupe smallint NOT NULL DEFAULT '1', /* Les questions d'une catégorie peuvent être divisées en plusieurs groupes */
   question_label varchar(255) NOT NULL, /* Le texte de la question elle-même */
   question_type smallint NOT NULL, /* Par exemple: boutons radio. Chaque type de question aura un numéro spécifique. Par exemple - le numéro 1 dans ce champ = boutons radio */
   question_obligatoire smallint NOT NULL DEFAULT '0', /* 0 - Pas obligatoire, 1 - obligatoire */
   
   CONSTRAINT t_cartegorie_id_fk FOREIGN KEY (categorie_id) references l2n1_categories (categorie_id)
);

CREATE TABLE l2n1_propositions( /* Les réponses / options des questions */
   proposition_id SERIAL PRIMARY KEY, /* serial = autoincrementing integer */
   question_id integer NOT NULL,
   proposition_label varchar(255) NOT NULL, /* Le texte de la proposition / réponse / option elle-même */
   proposition_score integer NOT NULL, /* Le texte de la proposition / réponse / option elle-même */
   
   CONSTRAINT t_question_id_fk FOREIGN KEY (question_id) references l2n1_questions (question_id)
);

CREATE TABLE l2n1_reponses( /* Les réponses des utilisateurs aux questions */
   reponse_log_id SERIAL PRIMARY KEY, /* serial = autoincrementing integer */
   utilisateur_email varchar(255) NOT NULL,
   question_id integer NOT NULL,
   proposition_id integer,
   reponse_valeur varchar(255),
   categorie_id integer NOT NULL,

   CONSTRAINT t_question_id_fk FOREIGN KEY (question_id) references l2n1_questions (question_id),
   CONSTRAINT t_proposition_id_fk FOREIGN KEY (proposition_id) references l2n1_propositions (proposition_id),
   CONSTRAINT t_utilisateur_email_fk FOREIGN KEY (utilisateur_email) references utilisateur (email),
   CONSTRAINT t_categorie_id_fk FOREIGN KEY (categorie_id) references l2n1_categories (categorie_id)
);
