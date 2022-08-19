package com.sdzee.dao;

import java.util.List;

import com.sdzee.beans.Categorie;
import com.sdzee.beans.Proposition;
import com.sdzee.beans.Question;
import com.sdzee.beans.Reponse;
import com.sdzee.beans.Utilisateur;

/**
 * <b>La classe ReponseDao</b>: <br>
 * <ul>
 * 	<li>Interface du DAO Reponse</li>
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
 * @author Leonard Namolaru, Huihui Huang, Rhidine Andriamihaja
 * 
 */
public interface ReponseDao {
	
	/* En Java, les méthodes d'une interface sont obligatoirement publiques et abstraites, 
     * inutile donc de préciser les mots-clés public et abstract dans leurs signatures. 
     * L'écriture reste permise, mais elle est déconseillée dans les spécifications Java SE 
     * publiées par Oracle, dans le chapitre concernant les interfaces.
     */
	
	/* ********************** METHODES **************************** */
	
	/**
	 * Méthode abstraite qui créer une réponse
	 * 
	 * @param reponse - Un bean Reponse
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDao
	 * 
	 * @author Huihui Huang, Leonard Namolaru
	 */
	void creer (Reponse reponse) throws DAOException;

	
	/**
	 * Méthode abstraite qui vérifie si l'utilisateur a déjà répondu à une catégorie: si les réponses existes alors ils sont écrasées 
	 * 
	 * @param reponses - une liste de beans Reponse
	 * @param categorie - Un bean Categorie
	 * @param utilisateur - Un bean Utilisateur
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDao
	 * 
	 * @author Leonard Namolaru, Huihui Huang
	 */
	void enregistrementReponses(List<Reponse> reponses, Categorie categorie, Utilisateur utilisateur)throws DAOException;
	
	
	/**
	 * Méthode abstraite qui supprime une réponse
	 * 
	 * @param reponse - Un bean Reponse
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDao
	 * 
	 * @author Huihui Huang
	 */
	void supprimerReponse(Reponse reponse) throws DAOException;
	
	
	/**
	 * Méthode abstraite qui affiche tous les réponse des catégorie que l'utilisateur à répondu
	 * 
	 * @param utilisateur- Un bean Utilisateur
	 * 
	 * @return Une liste de beans Reponse
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDao
	 * 
	 * @author Huihui Huang
	 */
	List<Reponse> listerCategorie(Utilisateur utilisateur) throws DAOException;
	
	
	/**
	 * Méthode abstraite qui calcul le score d'une catégorie regroupé par utilisateur
	 * 
	 * @param categorie - Un bean Categorie
	 * @param start- Le début de la ligne dans la BDD
	 * @param count - Le nombre de ligne par page
	 * 
	 * @return La liste d'utilisateur accompagné de son score
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDao
	 * 
	 * @author Huihui Huang, Rhidine Andriamihaja
	 */
	List<Reponse> score(Categorie categorie, int start, int count) throws DAOException;
	
	
	/**
	 * Méthode abstraite qui permet de trouver la liste de réponse d'une proposition
	 * 
	 * @param proposition - Un bean Proposition
	 * 
	 * @return La liste de réponse
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDao
	 * 
	 * @author Huihui Huang
	 */
	List<Reponse> trouver(Proposition proposition) throws DAOException;
	
	/**
	 * Méthode abstraite qui permet de trouver la liste de réponse d'une question
	 * 
	 * @param question - Un bean question
	 * 
	 * @return La liste des beans Reponse
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDao
	 * 
	 * @author Huihui Huang
	 */
	List<Reponse> trouver(Question question) throws DAOException;
	
	
	/**
	 * Méthode abstraite qui compte le nombre d'utilisateur qui on répondu à une catégorie spécifique
	 * 
	 * @param categorie - Un bean Categorie
	 * 
	 * @return Le nombre total d'utilisateur
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDao
	 * 
	 * @author Huihui Huang, Rhidine Andriamihaja
	 */
	int total(Categorie categorie) throws DAOException;
	
		
	/**
	 * Méthode abstraite qui trouve la liste de réponse en fonction de l'utilisateur et de la catégorie
	 * 
	 * @param utilisateur - Un bean Utilisateur
	 * @param categorie - un bean Categorie
	 *  
	 * @return La liste des beans Reponse
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDao
	 * 
	 * @author Huihui Huang
	 */
	List<Reponse> trouverAll(Utilisateur utilisateur, Categorie categorie) throws DAOException;
	
	
	/**
	 * Méthode abstraite qui liste tous les réponses de la base de données
	 * 
	 * @return La liste des beans Reponse
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDao
	 * 
	 * @author Huihui Huang
	 */
	List<Reponse> lister() throws DAOException;
	
	
	/**
	 * Méthode abstraite qui supprime la liste de réponse d'un utilisateur
	 * 
	 * @param utilisateur - Un bean Utilisateur
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDao
	 * 
	 * @author Huihui Huang
	 */
	void supprimerReponseUser(Utilisateur utilisateur) throws DAOException;
	
}
