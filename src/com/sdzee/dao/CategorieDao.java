package com.sdzee.dao;

import java.util.List;
import com.sdzee.beans.Categorie;

/**
 * <b>La classe CategorieDAO</b>: <br>
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
 * @author Leonard Namolaru, Huihui Huang
 */
public interface CategorieDao {
	
	/*
	 * En Java, les méthodes d'une interface sont obligatoirement publiques et abstraites, 
     * inutile donc de préciser les mots-clés public et abstract dans leurs signatures. 
     * L'écriture reste permise, mais elle est déconseillée dans les spécifications Java SE 
     * publiées par Oracle, dans le chapitre concernant les interfaces.
     */
	
	/* ********************** METHODES **************************** */
	
	/**
	 * Méthode abstraite qui créer une catégorie dans la BDD
	 * 
	 * @param categorie - Un bean Categorie
	 * 
	 * @throws DAOException Si erreurs lors de la requête SQL
	 * 
	 * @see CategorieDao
	 * 
	 * @author Leonard Namolaru
	 */
	void creer(Categorie categorie) throws DAOException;
	
	
	/**
	 * Méthode abstraite qui liste tous les catégories existantes de la base de données
	 * 
	 * @param start- Début de la ligne dans la base de données
	 * @param count - Nombre de ligne par page
	 * 
	 * @return La liste des beans Categorie
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see CategorieDao
	 * 
	 * @author Huihui Huang
	 */
	List<Categorie> lister(int start, int count) throws DAOException;
	
	
	/**
	 * Méthode abstraite qui calcule le nombre total de catégorie existante
	 * 
	 * @return Le nombre total de Categorie dans la base de données
	 * 
	 * @throws DAOException Si erreurs pendant la requête
	 * 
	 * @see CategorieDao
	 * 
	 * @author Huihui Huang
	 */
	int getTotal() throws DAOException;
	
	
	/**
	 * Méthode abstraite qui permet de retrouver une catégorie dans la base de données
	 * 
	 * @param id - ID d'un bean Categorie
	 * 
	 * @return Un bean Categorie
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see CategorieDao
	 * 
	 * @author Huihui Huang
	 */
	Categorie trouver(Integer id) throws DAOException;
	
	
	/**
	 * Méthode abstraite qui supprimer la catégorie
	 * 
	 * @param categorie - Un bean Categorie
	 * 
	 * @throws DAOException Si erreurs pendant la requête
	 * 
	 * @see CategorieDao
	 * 
	 * @author Huihui Huang
	 */
	void supprimer (Categorie categorie) throws DAOException;
	
	
	/**
	 * Méthode abstraite qui fait la mise à jour d'un bean Categorie dans la base de données
	 * 
	 * @param categorie - Un bean Categorie
	 * 
	 * @throws DAOException Si erreurs pendant la requête
	 * 
	 * @see CategorieDao
	 * 
	 * @author Huihui Huang, Leonard Namolaru
	 */
	void update (Categorie categorie) throws DAOException;
	

}
