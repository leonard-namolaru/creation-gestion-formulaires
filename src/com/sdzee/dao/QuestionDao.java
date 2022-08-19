package com.sdzee.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sdzee.beans.Categorie;
import com.sdzee.beans.Question;

/**
 * <b>La classe QuestionDao</b>: <br>
 * <ul>
 * 	<li>Interface du DAO Question</li>
 * 	<li>Les méthodes et attributs de cette interfaces sont abstraites </li>
 * </ul>
 * 	
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet et ajout d'explications à partir des tutoriels (source 1, source 2)</p>
 * 
 * <p>Projet Sujet : L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * <p>Source2 : openclassrooms.com - Apprenez à programmer en Java - Auteur : Cyrille Herby - License CC BY-NC-SA 2.0</p>
 * 
 * @author  Leonard Namolaru, Huihui Huang
 * 
 */
public interface QuestionDao {
	
	/* En Java, les méthodes d'une interface sont obligatoirement publiques et abstraites, 
     * inutile donc de préciser les mots-clés public et abstract dans leurs signatures. 
     * L'écriture reste permise, mais elle est déconseillée dans les spécifications Java SE 
     * publiées par Oracle, dans le chapitre concernant les interfaces.
     */
	
	/* ********************** METHODES **************************** */
	
	/**
	 * Méthode abstraite qui créer une question
	 * 
	 * @param question - Un bean Question
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see QuestionDao
	 * 
	 * @author Leonard Namolaru
	 */
	void creer(Question question) throws DAOException;

	
	/**
	 * Méthode abstraite qui créer une liste de question par catégorie
	 * 
	 * @param categorie- Un bean Categorie
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see QuestionDao
	 * 
	 * @author Leonard Namolaru
	 */
	void creer(Categorie categorie) throws DAOException;
	
	
	/**
	 * Méthode abstraite pour retrouver les question existantes dans la BDD
	 * 
	 * @param categorie - Un bean Categorie
	 * 
	 * @return Liste des beans Question d'une catégorie
	 * 
	 * @throws DAOException Si erreurs pendant la requêtes SQL
	 * 
	 * @see QuestionDao
	 * 
	 * @author Huihui Huang
	 */
	 List<Question> lister(Categorie categorie) throws DAOException;
	 
	 
	 /**
	  * Méthode abstraite qui liste la liste de question d'une catégorie par groupe 
	  * 
	  * @param categorie - Un bean Categorie
	  * @param numeroGroupe - Le numéro de groupe 
	  * 
	  * @return La liste de question
	  * 
	  * @throws DAOException Si erreurs pendant la requêtes SQL
	  * 
	  * @see QuestionDao
	  * 
	  * @author Leonard Namolaru
	  */
	 List<Question> lister(Categorie categorie, Integer numeroGroupe) throws DAOException;
	 
	 
	 /**
	  * Méthode abstraite qui retrouve une question par son id
	  * 
	  * @param id- ID d'un bean Question
	  * 
	  * @return Un bean Question
	  * 
	  * @throws DAOException Si erreurs pendant la requêtes SQL
	  * 
	  * @see QuestionDao
	  * 
	  * @author Huihui Huang
	  */
	 Question trouver(Integer id) throws DAOException;
	 
	 
	 /**
	  * Méthode abstraite qui supprime une question d'une catégorie
	  * 
	  * @param question - Un bean Question
	  * 
	  * @throws DAOException Si erreurs pendant la requête SQL
	  * 
	  * @see QuestionDao
	  * 
	  * @author Huihui Huang
	  */
	 void supprimer (Question question) throws DAOException; 

	 
	 /**
	  * Méthode abstraite qui supprime une question d'une catégorie (Le groupe en plus)
	  * 
	  * @param categorie - Un bean Categorie
	  * @param question - Un bean Question
	  * @param categorieDao - CategorieDao
	  * 
	  * @throws DAOException Si erreurs pendant la requête SQL
	  * 
	  * @see QuestionDao
	  * 
	  * @author Leonard Namolaru
	  */
	 void supprimer (Categorie categorie, Question question, CategorieDao categorieDao) throws DAOException; 
	 
	 
	 /**
	  * Méthode abstraite qui fait la mise à jour d'une question 
	  * 
	  * @param request - Une requête HTTP qui contient la demande du client
	  * @param question - Une liste de bean Question
	  * @param categorie - Un bean Categorie
	  * 
	  * @throws DAOException Si erreurs pendant la requête SQL
	  * 
	  * @see QuestionDao
	  * 
	  * @author Leonard Namolaru
	  */
	 void update (HttpServletRequest request, List<Question> question, Categorie categorie) throws DAOException;
	 
	 
	 /**
	  * Méthode abstraite qui fait la mise à jour d'une liste de question
	  * 
	  * @param questions - Une liste de beans Question
	  * 
	  * @throws DAOException Si erreurs pendant la requête SQL
	  * 
	  * @see QuestionDao
	  * 
	  * @author Leonard Namolaru
	  */
	 void updateQuestions (List<Question> questions) throws DAOException;




}