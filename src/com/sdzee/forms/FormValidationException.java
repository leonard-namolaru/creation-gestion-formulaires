package com.sdzee.forms;

/**
 * <b>La classe FormValidationException</b>: <br>
 * <ul>
 * 	<li>Encapsule les exceptions</li>
 * </ul>
 * 
 * <p>Travail_effectué : Implémentation du code dans le projet et ajout d'explications à partir des tutoriels (source 1))</p>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * 
 * @author Leonard Namolaru
 */
public class FormValidationException extends Exception {
	
	private static final long serialVersionUID = 1L;

	/* ********************* CONSTRUCTEURS ******************** */
	
	/**
	 * Constructeur qui affiche le message d'erreur
	 * 
	 * @param message - Un message d'erreur
	 * 
	 * @see FormValidationException
	 */
    public FormValidationException( String message ) {
        super( message );
    }
}