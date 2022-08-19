package com.sdzee.dao;

/**
 * <b>La classe DAOException</b>: <br>
 * <ul>
 * 	<li>Classes héritant de RuntimeException qui définit les constructeurs Exception
 * 		personnalisée qui va encapsuler les exceptions liées au JDBC</li>
 * </ul>
 * 
 * <p>Travail_effectué : Implémentation du code (source 1) dans le projet et ajout d'explications à partir du tutoriel (source 1)</p>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 */
public class DAOException extends RuntimeException {
	// Clé de hachage SHA qui identifie de manière unique la classe
	private static final long serialVersionUID = 1L;

	/* *********************** CONSTRUCTEURS ********************* */
	
	/**
	 * Constructeur qui affiche l'erreur
	 * 
	 * @param message - Le message d'ereur
	 * 
	 * @see DAOException
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Constructeur qui affiche les erreurs avec la cause
	 * 
	 * @param message - Le message d'erreur
	 * @param cause - La cause de l'erreur
	 * 
	 * @see DAOException
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * COnstructeur qui affiche la cause de l'erreur
	 * 
	 * @param cause - La cause de l'erreur
	 * 
	 * @see DAOException
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}