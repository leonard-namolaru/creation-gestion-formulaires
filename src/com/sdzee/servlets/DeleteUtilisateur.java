package com.sdzee.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdzee.beans.Utilisateur;
import com.sdzee.dao.DAOException;
import com.sdzee.dao.DAOFactory;
import com.sdzee.dao.ReponseDao;
import com.sdzee.dao.UtilisateurDao;

/**
 * <b>La classe DeleteUtilisateur</b>: 
 * <ul>
 * 	<li>Hérite de la classe HTTPSERVLET</li>
 * 	<li>Servlet qui suppreme un utilisateur via administrateur</li>
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
public class DeleteUtilisateur extends HttpServlet {
	/* *********************************** ATTRIBUTS ********************************** */
	
	// Clé de hachage SHA qui identifie de manière unique la classe= gestionnaire de version d'une classe 
	private static final long serialVersionUID = 1L;
	
	private static final String CONF_DAO_FACTORY = "daofactory";
	
    private UtilisateurDao utilisateurDao;
    private ReponseDao reponseDao;
      
    /* ************************************** METHODES ********************************** */
	
    /**
     * Récupération d'une instance des DAO
     * 
     * @throws ServletException Si erreur pendant la récupération des instances
     * 
     * @see UtilisateurDao
     * @see ReponseDao
     */
    public void init() throws ServletException {
        this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
        this.reponseDao= ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getReponseDao();
    }

    
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @see UtilisateurDao#supprimer(String)
	 * @see UtilisateurDao#trouver(String)
	 * @see ReponseDao#supprimerReponseUser(Utilisateur)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Obtenir les données du champ email 
		String email= request.getParameter("email");
		
		// 2. Trouver l'utilisateur en question
		Utilisateur utilisateur= utilisateurDao.trouver(email);
		String resultat;
		
		try
        {	
			if (email != null) {
				// 3. Supprimer l'utilisateur grâce à son email
				utilisateurDao.supprimer(email);
				
				//4. Supprimer les réponses que l'utilisateur a répondu aussi
				reponseDao.supprimerReponseUser(utilisateur);
				
				resultat="<article class=\"succes\">Succès de la suppression.</article>";
			} else {
				resultat="<article class=\"erreur\">Échec de la suppression.</article>";
			}

        } 
        catch ( DAOException e )
        {
            resultat = "<article class=\"erreur\">Échec de la suppresion de la question : une erreur imprévue est survenue, cela semble être un problème de communication avec la base de données du site. Merci de réessayer dans quelques instants.</article>";
            e.printStackTrace();
        }
		
		// Stockage des attributs dans l'objet Request et transmission à la vue
		request.setAttribute("message", resultat);
		
		// 3. redirection à la page des listes des utilisateurs
		request.getServletContext().getRequestDispatcher("/list-utilisateur").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
