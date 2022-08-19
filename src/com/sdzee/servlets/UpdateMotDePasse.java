package com.sdzee.servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdzee.beans.Utilisateur;
import com.sdzee.dao.DAOFactory;
import com.sdzee.dao.UtilisateurDao;
import com.sdzee.forms.RetrouverMotDePasseForm;

/**
 * <b>La classe UpdateMotDePasse</b>: 
 * <ul>
 * 	<li>Hérite de la classe HTTPSERVLET</li>
 * 	<li>Servlet qui modifier le mot de passe de l'utilisateur</li>
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
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet</p>
 * 
 * <p>Projet Sujet : L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * 
 * @author Huihui Huang
 */
public class UpdateMotDePasse extends HttpServlet{
/* *********************************** ATTRIBUTS ********************************** */
	
	// Clé de hachage SHA qui identifie de manière unique la classe= gestionnaire de version d'une classe 
	private static final long serialVersionUID = 1L;
	
	public static final String CONF_DAO_FACTORY 	= "daofactory";
    public static final String ATT_USER         	= "utilisateur";
    public static final String ATT_FORM         	= "form";
    public static final String VUE              	= "/Restreint/Modification_mot_de_passe.jsp";
    
    private UtilisateurDao utilisateurDao;
    
    /* ************************************** METHODES ********************************** */
    
    /**
     * Récupération d'une instance de notre DAO Utilisateur
     * 
     * @throws ServletException Si erreur pendant la récupération de l'instance DAO Utilisateur
     * 
     * @see UtilisateurDao
     */
    public void init() throws ServletException {
        this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Obtenir les données du champ email
		request.getAttribute("email");
    	
		// 2. Affichage de la page pour modifier le mot de passe
		this.getServletContext().getRequestDispatcher( VUE ).forward(request, response);
		
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @see RetrouverMotDePasseForm#UpdateMotDePasse(HttpServletRequest)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Préparation de l'objet modification des formulaires
		RetrouverMotDePasseForm passwordForm = new RetrouverMotDePasseForm( utilisateurDao );
		
        // 2. Traitement de la requête et récupération du bean en résultant 
        Utilisateur utilisateur = passwordForm.updateMotDePasse(request);
       
    	
        // 3. Stockage des attributs dans request et transmission à la vue
        request.setAttribute( ATT_FORM, passwordForm );
        request.setAttribute( ATT_USER, utilisateur );
        

        request.getServletContext().getRequestDispatcher( VUE ).forward( request, response ); 

  
	}
}
