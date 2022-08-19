package com.sdzee.dao;

import java.util.List;

import com.sdzee.beans.Utilisateur;

/**
 * <b>La classe UtilisateurDao</b>: <br>
 * <ul>
 * 	<li>Interface du DAO Utilisateur</li>
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
 * @author Leonard Namolaru, Huihui Huang
 */
public interface UtilisateurDao {
	
	/* En Java, les méthodes d'une interface sont obligatoirement publiques et abstraites, 
     * inutile donc de préciser les mots-clés public et abstract dans leurs signatures. 
     * L'écriture reste permise, mais elle est déconseillée dans les spécifications Java SE 
     * publiées par Oracle, dans le chapitre concernant les interfaces.
     */
	
	/* ********************** METHODES **************************** */


	/**
	 * Méthode abstraite qui créer utilisateur lors de son inscription
	 * 
	 * @param utilisateur - Un bean utilisateur
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see UtilisateurDao
	 * 
	 * @author Leonard Namolaru
	 */
	void creer(Utilisateur utilisateur) throws DAOException;

	
	/**
	 * Méthode abstraite qui permet de trouver un utilisateur (la recherche d'un utilisateur, lors de la connexion)
	 * 
	 * @param email	- L'email d'un bean Utilisateur
	 * 
	 * @return Un bean Utilisateur
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see UtilisateurDao
	 * 
	 * @author Huihui Huang
	 */
	Utilisateur trouver(String email) throws DAOException;
	
	
	/**
	 * Méthode abstraite qui permet la  mise à jour des données personnels de l'utilisateur
	 * 
	 * @param utilisateur - Un bean Utilisateur
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see UtilisateurDao
	 * 
	 * @author Huihui Huang
	 */
	void miseAJour(Utilisateur utilisateur) throws DAOException;

	
	/**
	 * Méthode abstraite qui permet la mise à jour des données personnels d'un utilisateurs grâce à l'administrateur
	 * 
	 * @param utilisateur - Un bean Utilisateur
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see UtilisateurDao
	 * 
	 * @author Huihui Huang
	 */
	void miseAJourAdmin(Utilisateur utilisateur) throws DAOException;
	

	/**
	 * Méthode abstraite qui permet la modification du mot de passe de l'utilisateur
	 * 
	 * @param utilisateur - Un bean Utilisateur
	 * 
	 * @throws DAOException	Si erreurs pendant la requête SQL
	 * 
	 * @see UtilisateurDao
	 * 
	 * @author Huihui Huang
	 */
	void miseAJourPassword(Utilisateur utilisateur) throws DAOException;
	
	
	/**
	 * Méthode abstraite qui permet de supprimer un utilisateur
	 * 
	 * @param email - L'Email d'un bean Utilisateur
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see UtilisateurDao
	 * 
	 * @author Huihui Huang
	 */
	void supprimer(String email) throws DAOException;
	
	
	/**
	 * Méthode abstraite qui permet de calculer le nombre d'utilisateur inscrit
	 * 
	 * @return Le nombre total d'utilisateur inscrit
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see UtilisateurDao
	 * 
	 * @author Huihui Huang
	 */
	int getTotal() throws DAOException;
	
	/**
	 * Méthode abstraite qui liste tous les utilisateurs inscrits
	 * 
	 * @param start- Le début de la ligne dans la BDD
	 * @param count - Le nombre de ligne par page
	 * 
	 * @return La liste des utilisateurs inscrits 
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see UtilisateurDao
	 * 
	 * @author Huihui Huang
	 */
	List<Utilisateur> lister(int start, int count) throws DAOException;
	
	/**
	 * Méthode abstraite qui liste tous les utilisateurs inscrits sans limite
	 * 
	 * @return La liste des utilisateurs inscrits 
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see UtilisateurDao
	 * 
	 * @author Huihui Huang
	 */
	List<Utilisateur> lister() throws DAOException;
	
	
	
}