package com.sdzee.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.beans.Utilisateur;
import com.sdzee.dao.DAOFactory;
import com.sdzee.dao.UtilisateurDao;
import com.sdzee.forms.UpdateUtilisateurForm;

/**
 * <b>La classe UpdateAdmin</b>: 
 * <ul>
 * 	<li>Hérite de la classe HTTPSERVLET</li>
 * 	<li>Servlet qui fait une mise à jour des données de l'utilisateur via l'administrateur</li>
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
public class UpdateAdmin extends HttpServlet {
	/* *********************************** ATTRIBUTS ********************************** */
	
	// Clé de hachage SHA qui identifie de manière unique la classe= gestionnaire de version d'une classe 
	private static final long serialVersionUID = 1L;
	
	private static final String CONF_DAO_FACTORY 	= "daofactory";
    private static final String ATT_SESSION_USER 	= "sessionUtilisateur";
    private static final String ATT_USER         	= "utilisateur";
    private static final String ATT_FORM         	= "form";
    private static final String VUE              	= "/RestreintAdmin/DonneeUtilisateur.jsp";
	private UtilisateurDao utilisateurDao;

	/* ************************************** METHODES ********************************** */
	
	/**
     * Récupération d'une instance de notre DAO Utilisateur 
     * 
     * @throws ServletException Si erreur pendant la récupération de l'instance DAO Utilisateur
     */
    public void init() throws ServletException {
        this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
    }
    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @see UtilisateurDao#trouver(String)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Obtenir l'email de l'utilisateur
		String email= request.getParameter("email");
		
		// 2. Mettre l'email en session
		request.getSession().setAttribute(ATT_SESSION_USER, email);
		
		// 3. Trouver l'utilisateur via son email
		Utilisateur utilisateur= utilisateurDao.trouver(email);
		
		// Stockage des attributs dans l'objet Request et transmission à la vue
		request.setAttribute(ATT_USER, utilisateur);
		
		this.getServletContext().getRequestDispatcher( VUE ).forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @see UpdateUtilisateurForm#modificationAdmin(HttpServletRequest)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		// 1. Préparation de l'objet modification des formulaires */
		UpdateUtilisateurForm modifForm = new UpdateUtilisateurForm( utilisateurDao );
		
        // 2. Traitement de la requête et récupération du bean en résultant */
        Utilisateur utilisateur = modifForm.modification(request);
       
    	
        // 4. Stockage des attributs dans request et transmission à la vue
        request.setAttribute( ATT_FORM, modifForm );
        request.setAttribute( ATT_USER, utilisateur );
        
        // 5. Si aucune erreurs des champs
        if(modifForm.getErreurs().isEmpty()) {
        	// 6. Création de la session 
        	HttpSession session= request.getSession();
        	// 7. Mettre l'utilisateur dans la session
        	session.setAttribute(ATT_SESSION_USER, utilisateur);
        }
        
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

}
