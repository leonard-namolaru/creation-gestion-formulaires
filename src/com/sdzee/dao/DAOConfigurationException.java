package com.sdzee.dao;

/**
 * <b>La classe DAOCongifurationException</b>: <br>
 * <ul>
 * 	<li>Classes héritant de RuntimeException qui définit les constructeurs Exception
 * personnalisée qui va encapsuler les exceptions liées au JDBC</li>
 * </ul>
 * 
 * <p>Travail_effectué : Implémentation du code (source 1) dans le projet et ajout d'explications à partir du tutoriel (source 1)</p>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 */
public class DAOConfigurationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/* ************************ CONSTRUCTEURS ********************* */

	/**
	 * Constructeur qui affiche le message d'exception
	 * 
	 * @param message - Un message d'exception
	 * 
	 * @see DAOConfigurationException
	 */
	public DAOConfigurationException(String message) {
		super(message);
	}

	/**
	 * Constructeur qui affiche le message d'exception et ma cause de l'exception
	 * 
	 * @param message - Le message d'exception
	 * @param cause - La cause de l'exception
	 * 
	 * @see DAOConfigurationException
	 */
	public DAOConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	/**
	 * Constructeur qui affiche la cause de l'exception
	 * 
	 * @param cause - La cause de l'exception
	 * 
	 * @see DAOConfigurationException
	 */
	public DAOConfigurationException(Throwable cause) {
		super(cause);
	}
}