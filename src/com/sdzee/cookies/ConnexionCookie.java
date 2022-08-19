package com.sdzee.cookies;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

/**
 * <b>La classe ConnexionCookie</b>: <br>
 * <ul>
 * 	<li>Enregistrement du cookie lors de la connexion</li>
 * </ul>
 * 
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet et ajout d'explications à partir du tutoriel (source 1)</p>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 *
 * @author Huihui Huang
 */
public class ConnexionCookie {
	
	/* ************************ ATTRIBUTS *********************** */
	private static final String COOKIE_LAST_TIME 	= "heureConnexion";
	private static final int COOKIE_MAX_AGE			= 60*60*24*365;
	private static final String CHAMP_SAVE			= "lastconnect";
	
	
	/* ************************ METHODES *********************** */
	
	/**
	 * Méthode qui permet la création d'un cookie d'enregistrement de la dernière heure de connexion si l'utilisateur à coché le checkbox "Se souvenir de moi"
	 * 
	 * @param req - La requête HTTP qui contient la demande du client
	 * @param resp - La Réponse HTTP qui sera renvoyée au client 
	 * 
	 * @see ConnexionCookie
	 */
	public void enregistrerCookie(HttpServletRequest req, HttpServletResponse resp){
		/* Si l'utilisateur coche le checkbox "Se souvenir de moi"*/
		if(req.getParameter(CHAMP_SAVE) != null){
			/* Une date */
			DateTime heureConnexion = new DateTime();
			Cookie cookie = new Cookie(COOKIE_LAST_TIME, heureConnexion.toString());
			/* Conversion de l'heure de connexion */
			cookie.setMaxAge(COOKIE_MAX_AGE);
			/* Ajout du Cookie dans request*/
			resp.addCookie(cookie);
		} else {
			/* Si non cochée)->invalidation du cookie */
			Cookie cookie = new Cookie(COOKIE_LAST_TIME, "");
			/* Remettre à null*/
			cookie.setMaxAge(0);
			/* Ajout du Cookie dans request*/
			resp.addCookie(cookie);
		}
	}
}
