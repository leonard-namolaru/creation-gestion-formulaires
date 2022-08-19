package com.sdzee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * <b>La classe DAO Utilitaire</b>: <br>
 * <ul>
 * 	<li>Initialiser une requête préparée avec des paramètres</li>
 *	<li>Récupérer une ligne d'une table et enregistrer son contenu dans un bean</li>
 *	<li>Fermer proprement les ressources ouvertes (Connection, PreparedStatement, ResultSet)</li>
 * </ul>
 * 
 * <p>Les méthodes utilitaires peuvent être utilisées depuis n'importe quel DAO</p>
 * 
 * <p>Travail_effectué : Implémentation du code (source 1) dans le projet et ajout d'explications à partir du tutoriel (source 1)</p>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * 
 * @author  Huihui Huang, Leonard Namolaru
 */
public final class DAOUtilitaire {

	/* ************************ CONSTRUCTEURS *********************** */
	
	/**
	 * Constructeur par défaut
	 */
	public DAOUtilitaire() {
	}

	/* ************************ METHODES ***************************** */
	
	/**
	 * Méthode qui permet l'initialisation de la requête préparée basée sur la connexion passée en
	 * argument, avec la requetes SQL et les objets données
	 * 
	 * <p>Une méthode purement utilitaire, que nous pourrons réutiliser telle quelle dans n'importe quel DAO</p>
	 * 
	 * @param connexion - La connexion à la base de donnée
	 * @param sql - La requête SQL
	 * @param returnGeneratedKeys - La génération d'une clé unique 
	 * @param objets - Liste d'objet
	 * 
	 * @return L'instruction SQL paramétrée
	 * 
	 * @throws SQLException Si erreurs pendant la requête SQL
	 * 
	 * @see DAOUtilitaire
	 */
	public static PreparedStatement initialisationRequetePreparee(Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets) 
	   throws SQLException {
		/* Initialiser une requête préparée via un appel à connexion.prepareStatement()
		 * c'est lors de cet appel qu'il faut préciser si la requête doit retourner un champ auto-généré ou non (boolean returnGeneratedKeys) */
		PreparedStatement preparedStatement = connexion.prepareStatement(sql,returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);

		/*
		 * Placement de chaque objet dans la requête préparée via un appel à la méthode
		 * preparedStatement.setObject()
		 */
		for (int i = 0; i < objets.length; i++) {
			preparedStatement.setObject(i + 1, objets[i]);
		}

		return preparedStatement;
	}
	
	
	/**
	 * Méthode qui gère la fermeture silencieuse du resultset
	 * 
	 * @param resultSet	- Les résultats d'une requête
	 * 
	 * @see DAOUtilitaire
	 */
	public static void fermetureSilencieuse(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				System.out.println("Echec de la fermeture du ResultSet : " + e.getMessage());
			}
		}
	}

	/**
	 * Méthode qui gère la fermeture silencieuse du statement
	 * 
	 * @param statement - L'instruction SQL
	 * 
	 * @see DAOUtilitaire
	 */
	public static void fermetureSilencieuse(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				System.out.println("Echec de la fermeture du Statement : " + e.getMessage());
			}
		}
	}

	/**
	 * Méthode qui gère la fermeture silencieuse de la connexion
	 * 
	 * @param connexion - La connexion à la BDD
	 * 
	 * @see DAOUtilitaire
	 */
	public static void fermetureSilencieuse(Connection connexion) {
		if (connexion != null) {
			try {
				connexion.close();
			} catch (SQLException e) {
				System.out.println("Echec de la fermeture de la connexion : " + e.getMessage());
			}
		}
	}

	/**
	 * Méthode qui gère les fermetures silencieuses du statement et de la connexio
	 * 
	 * @param statement	- L'instruction SQL
	 * @param connexion	- La connexion à la BDD
	 * 
	 * @see #fermetureSilencieuse(Connection)
	 * @see #fermetureSilencieuse(Statement)
	 */
	public static void fermeturesSilencieuses(Statement statement, Connection connexion) {
		fermetureSilencieuse(statement);
		fermetureSilencieuse(connexion);
	}

	/**
	 * Méthode qui gère les fermetures silencieuses du resultset, du statement et de la connexion
	 * 
	 * @param resultSet	- Les résultats d'une requête
	 * @param statement - L'instruction SQL
	 * @param connexion	- La connexion à la BDD
	 * 
	 * @see #fermetureSilencieuse(Connection)
	 * @see #fermetureSilencieuse(ResultSet)
	 * @see #fermetureSilencieuse(Statement)
	 */
	public static void fermeturesSilencieuses(ResultSet resultSet, Statement statement, Connection connexion) {
		fermetureSilencieuse(resultSet);
		fermetureSilencieuse(statement);
		fermetureSilencieuse(connexion);
	}

}