package com.sdzee.servlets;

import java.io.IOException;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.beans.Categorie;
import com.sdzee.beans.Reponse;
import com.sdzee.beans.Utilisateur;
import com.sdzee.dao.CategorieDao;
import com.sdzee.dao.DAOFactory;
import com.sdzee.dao.ReponseDao;
import com.sdzee.dao.UtilisateurDao;

/**
 * <b>La classe AffichageCtagorie</b>: 
 * <ul>
 * 	<li>Hérite de la classe HTTPSERVLET</li>
 * 	<li>Servlet qui affiche tous les catégorie dans la base de données</li>
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
public class AfficheCategorie extends HttpServlet {
	/* *********************************** ATTRIBUTS ********************************** */
	
	// Clé de hachage SHA qui identifie de manière unique la classe= gestionnaire de version d'une classe 
	private static final long serialVersionUID = 1L;
	
	private static final String CONF_DAO_FACTORY	= "daofactory";
	private static final String ATT_CATEGORIE       = "categorie";
	private static final String ATT_REPONSE			= "reponses";
	private static final String ATT_SESSION_USER 	= "sessionUtilisateur";
	private static final String VUE              	= "/Restreint/Utilisateur_connecte.jsp";
    
	private CategorieDao categorieDao;
	private ReponseDao reponseDao;
	private UtilisateurDao utilisateurDao;
	
	/* commencement de la page */
	private int start= 0;
	/* Nombre de ligne sur 1 page */
	private int count = 3;
	/* Pour aller à la page suivante */
	private int suiv;
	/* Pour aller à la page précédente */
	private int pre;
	/* Dernière page */
	private int dern;
	/* Total de page */
	private int total;
	
	/* ************************************** METHODES ********************************** */
	
	/**
     * Récupération d'une instance des DAO
     * 
     * @throws ServletException Si erreur pendant la récupération des instances
     * 
     * @see CategorieDao
     * @see ReponseDao
     * @see UtilisateurDao
     */
    public void init() throws ServletException {
        this.categorieDao = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY )).getCategorieDao();
        this.reponseDao= ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getReponseDao();
        this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
    }
    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @see Categorie
	 * @see CategorieDao#lister()
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
		
		total= categorieDao.getTotal();
				
		if (0 == total % count) {
					dern= total-count;
		} else {
			dern= total - total%count;
		}
				
		/* Limite des pages suivantes et précédentes*/
		pre = pre < 0 ? 0 : pre;
		suiv = suiv > dern ? dern : suiv;
			
		// 1. Récupération de l'objet utilisateur correspondant dans la session 
        HttpSession session= request.getSession();
        Utilisateur utilisateur= (Utilisateur) session.getAttribute(ATT_SESSION_USER);
        utilisateur= utilisateurDao.trouver(utilisateur.getEmail());
		
        
		// 3. Affichage des catégorie existante dans la BDD 
		List<Categorie> categorie= categorieDao.lister(start, count);
		
		// 2. Lister les catégorie en fonction de l'utilisateur
        List <Reponse> reponseCategorie= reponseDao.listerCategorie(utilisateur);
	
		// 4. Stockage des attributs dans l'objet request et transmission à la vue
		request.setAttribute("suiv", suiv);
		request.setAttribute("pre", pre);
		request.setAttribute("total", total);
		request.setAttribute("dern", dern);
		request.setAttribute("courant", start);
		
		request.setAttribute(ATT_CATEGORIE, categorie);
		request.setAttribute(ATT_REPONSE, reponseCategorie);
		
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
