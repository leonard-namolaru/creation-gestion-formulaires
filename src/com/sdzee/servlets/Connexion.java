package com.sdzee.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.beans.Utilisateur;
import com.sdzee.cookies.ConnexionCookie;
import com.sdzee.dao.DAOFactory;
import com.sdzee.dao.UtilisateurDao;
import com.sdzee.forms.ConnexionForm;

/**
 * <b>La classe Connexion</b>: 
 * <ul>
 * 	<li>Hérite de la classe HTTPSERVLET</li>
 * 	<li>Servlet qui permet la connexion d'un utilisateur ou administrateur</li>
 * </ul>
 * 
 * <p>La classe HttpServlet : une classe abstraite (= on ne pourra pas l'utiliser telle quelle et il sera nécessaire de passer par une servlet qui en hérite). </p>
 * 
 * La classe propose les méthodes Java nécessaires au traitement des requêtes et réponses HTTP  :<br>
 * <ul>
 *	<li>doGet() pour gérer la méthode GET.</li>
 *	<li>doPost() pour gérer la méthode POST.</li>
 * 	<li>doHead() pour gérer la méthode HEAD.</li>
 * </ul>
 * <p>Une servlet doit implémenter au moins une des méthodes doXXX(), afin d'être capable de traiter une requête entrante, 
 * Puisque ce sont elles qui prennent en charge les requêtes entrantes, les servlets vont être les points d'entrée de notre application web.</p>
 * 	
 * 
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet</p>
 * 
 * <p>Projet Sujet : L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * 
 * @author Leonard Namolaru, Huihui Huang
 */
public class Connexion extends HttpServlet {
	/* *********************************** ATTRIBUTS ********************************** */
	
	// Clé de hachage SHA qui identifie de manière unique la classe= gestionnaire de version d'une classe 
	private static final long serialVersionUID = 1L;
	
	private static final String CONF_DAO_FACTORY	= "daofactory";
	private static final String VUE 				= "/Connexion.jsp";
	private static final String VUE_UTILISATEUR		= "/AccueilUtilisateur";
	private static final String VUE_ADMIN			= "/AccueilAdministrateur";
	private static final String ATT_USER 			= "utilisateur";
	private static final String ATT_FORM 			= "form";
	private static final String ATT_SESSION_USER 	= "sessionUtilisateur";
	private static final String ATT_SESSION_ADMIN 	= "sessionAdmin";
   
    private ConnexionCookie gestionSauvegardeConnexion;
    private UtilisateurDao utilisateurDao;
    
    /* ************************************** METHODES ********************************** */
    
    /**
     * Récupération d'une instance de notre DAO Utilisateur et du cookie
     * 
     * @throws ServletException Si erreur pendant la récupération de l'instance DAO Utilisateur
     * 
     * @see ConnexionCookie
     * @see UtilisateurDao
     */
    public void init() throws ServletException {
    	this.gestionSauvegardeConnexion= new ConnexionCookie();
        this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Affichage de la page de connexion 
		this.getServletContext().getRequestDispatcher( VUE ).forward(request, response);

    }
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @see ConnexionForm#connexion(HttpServletRequest)
	 * @see Utilisateur
	 * @see ConnexionCookie#recupererCookie(HttpServletRequest)
	 * @see ConnexionCookie#enregistrerCookie(HttpServletRequest, HttpServletResponse)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	// 1. Préparation de l'objet formulaire 
    	ConnexionForm form= new ConnexionForm(utilisateurDao);
    	
    	// 2. Traitement de la requête et récupération du bean en résultant 
    	Utilisateur utilisateur= form.connexion(request);
    
    	
    	// 3. Création de la session 
    	HttpSession session= request.getSession();

    	// 4. Création de la session utilisateur et administrateur si connexion réussie 
    	if (form.getErreurs().isEmpty()) {
    		if (utilisateur.getType()== 1) {
    			session.setAttribute(ATT_SESSION_ADMIN, utilisateur);
    			
    			/* Enregistrement du cookie lors de la connexion */
    			gestionSauvegardeConnexion.enregistrerCookie(request, response);
    			
    			/* Redirection à la page d'accueil dédiée à l'administrateur */
    			response.sendRedirect(getServletContext().getContextPath() + VUE_ADMIN);
    			
    		} else if (utilisateur.getType()==0){
    			session.setAttribute(ATT_SESSION_USER, utilisateur);
    			
    			/* Enregistrement du cookie lors de la connexion */
    			gestionSauvegardeConnexion.enregistrerCookie(request, response);
    			
    			/* Redirection à la page d'accueil dédiée à l'utilisateur */
    			response.sendRedirect(getServletContext().getContextPath() + VUE_UTILISATEUR);
    		}
    	} else {
    		/* Echec de la connexion */
    		/* Stockage des attributs dans request à null */
    		session.setAttribute(ATT_SESSION_USER, null);
    		session.setAttribute(ATT_SESSION_ADMIN, null);
    		
    		// 5. Stockage des attributs à request et transmission des données à la vue 
        	request.setAttribute(ATT_USER, utilisateur);
        	request.setAttribute(ATT_FORM, form);
        	
        	request.getServletContext().getRequestDispatcher(VUE).forward(request, response);
    	}
    
    }
    
}