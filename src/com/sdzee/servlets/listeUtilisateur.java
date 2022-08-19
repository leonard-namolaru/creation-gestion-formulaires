package com.sdzee.servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdzee.beans.Utilisateur;
import com.sdzee.dao.DAOFactory;
import com.sdzee.dao.UtilisateurDao;

/**
 * <b>La classe listeUtilisateur</b>: 
 * <ul>
 * 	<li>Hérite de la classe HTTPSERVLET</li>
 * 	<li>Servlet qui affiche dans une liste tous les utilisateurs inscrits</li>
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
public class listeUtilisateur extends HttpServlet {
	/* *********************************** ATTRIBUTS ********************************** */
	
	// Clé de hachage SHA qui identifie de manière unique la classe= gestionnaire de version d'une classe 
	private static final long serialVersionUID = 1L;

	private static final String CONF_DAO_FACTORY 	= "daofactory";
    private static final String ATT_USER 			= "utilisateur";
    private static final String VUE              	= "/RestreintAdmin/liste_des_utilisateurs.jsp";
    
    /* commencement de la page */
	private int start= 0;
	/* Nombre de ligne sur 1 page */
	private int count= 8;
	/* Pour aller à la page suivante */
	private int suiv;
	/* Pour aller à la page précédente */
	private int pre;
	/* Dernière page */
	private int dern;
	/* Total de page */
	private int total;

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
	 * @see UtilisateurDao#lister(int, int)
	 * @see UtilisateurDao#getTotal()
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Paramètre des pages
		try {
			start = Integer.parseInt(request.getParameter("start"));
		} catch (NumberFormatException e) {
			// Lorsque le navigateur ne transmet pas le paramètre "start"
		}
		suiv = start + count;
		
		pre= start-count;
		
		total= utilisateurDao.getTotal();
		
		if (0 == total % count) {
			dern= total-count;
		} else {
			dern= total - total%count;
		}
		
		/* Limite des pages suivantes et précédentes*/
		pre = pre < 0 ? 0 : pre;
		suiv = suiv > dern ? dern : suiv;
		
		// 2. Affichage des utilisateurs en fonction de count 
		List<Utilisateur> utilisateurs= utilisateurDao.lister(start,count);
		
		// 3. Récupère le message sur l'action supprimer
		String message = (String) request.getAttribute("message");
	
		// 4. Stockage des attributs dans l'objet request et transmission à la vue
		request.setAttribute("suiv", suiv);
		request.setAttribute("pre", pre);
		request.setAttribute("dern", dern);
		request.setAttribute("total", total);
		request.setAttribute(ATT_USER, utilisateurs);
		request.setAttribute("message", message);
		
		request.getServletContext().getRequestDispatcher(VUE).forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}



