package com.sdzee.dao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sdzee.beans.Categorie;
import com.sdzee.beans.Proposition;
import com.sdzee.beans.Question;
import com.sdzee.beans.Utilisateur;

/**
 * <b>La classe PropositionDAO</b>: <br>
 * <ul>
 * 	<li>Interface du DAO Categorie</li>
 * 	<li>Les méthodes et attributs de cette interfaces sont abstraites </li>
 * </ul>
 * 
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet et ajout d'explications à partir du tutoriel (source 1)</p>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * 
 * 
 * @author Leonard Namolaru, Huihui Huang, Rhidine Andriamihaja
 * 
 */
public interface PropositionDao {
	
	/*
	 * En Java, les méthodes d'une interface sont obligatoirement publiques et abstraites, 
     * inutile donc de préciser les mots-clés public et abstract dans leurs signatures. 
     * L'écriture reste permise, mais elle est déconseillée dans les spécifications Java SE 
     * publiées par Oracle, dans le chapitre concernant les interfaces.
     */
	
	/* ********************** METHODES **************************** */
	
	/**
	 * Méthode abstraite qui créer une liste de proposition en fonction de la catégorie dans la base de données
	 * 
	 * @param categorie - Un bean Categorie
	 * 
	 * @throws DAOException Si erreurs lors de la requête SQL
	 * 
	 * @see PropositionDao
	 * 
	 * @author Leonard Namolaru
	 */
	void creer(Categorie categorie) throws DAOException;
	
	
	/**
	 * Méthode abstraite qui créer une proposition dans la BDD
	 * 
	 * @param proposition - Un bean Proposition
	 * 
	 * @throws DAOException Si erreurs lors de la requête SQL
	 * 
	 * @see PropositionDao
	 * 
	 * @author Leonard Namolaru
	 */
	void creer(Proposition proposition) throws DAOException;
	
	
	/**
	 * Méthode abstraite pour trouver la liste de proposition d'une catégorie
	 * 
	 * @param id - ID d'un bean Categorie
	 * 
	 * @return La liste des beans Proposition
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see PropositionDao
	 * 
	 * @author Huihui Huang
	 */
	List<Proposition> lister(Integer id) throws DAOException;
	
	
	/**
	 * Méthode abstraite pour trouver les propostions d'une question
	 * 
	 * @param question - Un bean Question
	 * 
	 * @return La liste des beans Proposition
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see PropositionDao
	 * 
	 * @author Leonard Namolaru
	 */
	List<Proposition> listerQuestionId(Question question) throws DAOException;
	
	
	/**
	 * Méthode abstraite qui retrouve une proposition par son id
	 * 
	 * @param id- ID d'un bean Proposition
	 * 
	 * @return Un bean Proposition
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see PropositionDao
	 */
	Proposition trouver(Integer id) throws DAOException;
	
	
	/**
	 * Méthode abstraite qui supprime une proposition d'une catégorie
	 * 
	 * @param proposition - Un bean Proposition
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see PropositionDao 
	 * 
	 * @author Huihui Huang
	 */
	void supprimer (Proposition proposition) throws DAOException;
	
	
	/**
	 * Méthode abstraite qui fait la mise à jour de tous les propositions d'une catégorie
	 * 
	 * @param categorie - Un bean Categorie
	 * @param request - Une requête HTTP qui contient la demande du client
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see PropositionDao 
	 * 
	 * @author Leonard Namolaru
	 */
	void update (HttpServletRequest request, Categorie categorie) throws DAOException;
	
	
	/**
	 * Méthode abstraite qui calcule le score d'un utilisateur qui a répondu à une catégorie
	 * 
	 * @param categorie - Un bean Categorie
	 * @param utilisateur - Un bean Utilisateur
	 * 
	 * @return Le score obtenu par l'utilisateur d'une catégorie
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see PropositionDao
	 * 
	 * @author Huihui Huang
	 */
	int scoreObtenu (Categorie categorie, Utilisateur utilisateur) throws DAOException;
	
	
	/**
	 * Méthode abstraite qui calcule le score d'une question à choix unique
	 * 
	 * @param question - Un bean Question
	 * 
	 * @return Le score max d'une question
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see PropositionDao
	 * 
	 * @author Huihui Huang
	 */
	int scoreTotalQUnique(Question question) throws DAOException;
	
	
	/**
	 * Méthode abstraite qui calcule le score d'une question à choix multiple
	 * 
	 * @param question - Un bean Question
	 * 
	 * @return Le score max d'une question
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see PropositionDao
	 * 
	 * @author Huihui Huang
	 */
	int scoreTotalQMultiple(Question question) throws DAOException;
		
	
	/**
	 * Méthode abstraite qui calcule le pourcentage d'utilisateurs qui ont répondus à les propositions d'une catégorie et de question
	 * 
	 * @param categorie - Un bean Categorie
	 * @param question - Un bean Question
	 * 
	 * @return La liste des beans Proposition
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see PropositionDao
	 * 
	 * @author Huihui Huang
	 */
	List<Proposition> pourcentageGroupByQuestion(Categorie categorie, Question question) throws DAOException;
	
	
	
}