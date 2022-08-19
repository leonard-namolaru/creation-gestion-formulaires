package com.sdzee.servlets;

import java.io.IOException;


import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdzee.beans.Categorie;
import com.sdzee.beans.Reponse;
import com.sdzee.beans.Utilisateur;
import com.sdzee.dao.CategorieDao;
import com.sdzee.dao.DAOFactory;
import com.sdzee.dao.PropositionDao;
import com.sdzee.dao.QuestionDao;
import com.sdzee.dao.ReponseDao;
import com.sdzee.dao.UtilisateurDao;
import com.sdzee.forms.ReponseForm;

/**
 * <b>La classe resultatAdministrateur</b>: 
 * <ul>
 * 	<li>Hérite de la classe HTTPSERVLET</li>
 * 	<li>Servlet qui affiche tous les résultats des utilisateurs qui ont répondu à des formulaires</li>
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
 * @author Rhidine Andriamihaja, Huihui Huang
 */
public class resultatAdministrateur extends HttpServlet {
	/* *********************************** ATTRIBUTS ********************************** */
	
	// Clé de hachage SHA qui identifie de manière unique la classe= gestionnaire de version d'une classe 
	private static final long serialVersionUID = 1L;
       
	private static final String CONF_DAO_FACTORY	= "daofactory";
    private static final String VUE              	= "/RestreintAdmin/tout_les_resultats.jsp";
    private static final String SCORE				= "score";
    private static final String CATEGORIE			= "categorie";
    private static final String TOTAL				= "total";
    private static final String UTILISATEUR			= "utilisateur";
    
    private PropositionDao propositionDao;
    private CategorieDao categorieDao;
    private ReponseDao reponseDao;
    private UtilisateurDao utilisateurDao;
    private QuestionDao questionDao;
    
	private int start= 0;
	private int count= 10;
	private int suiv;
	private int pre;
	private int dern;
	private int total;
    

	/* ************************************** METHODES ********************************** */
	
	/**
     * Récupération d'une instance des DAO
	 * 
	 * @throws ServletException Si erreur pendant la récupération des instances
	 * 
	 * @see PropositionDao
	 * @see QuestionDao
	 * @see ReponseDao
	 * @see CategorieDao
	 * @see UtilisateurDao
     */
	public void init() throws ServletException {
        this.propositionDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getPropositionDao();
        this.categorieDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCategorieDao();
        this.reponseDao= ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getReponseDao();
        this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
        this.questionDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getQuestionDao();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @see CategorieDao#trouver(Integer)
	 * @see ReponseDao#total(Categorie)
	 * @see PropositionDao#scoreTotal(Categorie)
	 * @see UtilisateurDao#lister()
	 * @see ReponseDao#score(Categorie, int, int)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Obtenir l'id de la categorie
        Integer id= Integer.parseInt(request.getParameter("id"));
        
        // 2. Retrouver la categorie en question
        Categorie categorie = categorieDao.trouver(id);
        
		try {
			start = Integer.parseInt(request.getParameter("start"));
		} catch (NumberFormatException e) {
			// Lorsque le navigateur ne transmet pas le paramètre "start"
		}
		suiv = start + count;
		
		pre= start-count;
		
		total= reponseDao.total(categorie);
		
		if (0 == total % count) {
			dern= total-count;
		} else {
			dern= total - total%count;
		}
		pre = pre < 0 ? 0 : pre;
		suiv = suiv > dern ? dern : suiv; 
        
		// 3. Calcul du score total d'une catégorie
		ReponseForm form = new ReponseForm(questionDao, propositionDao);
        int total= form.calculScoreTotal(categorie);
		
        // 4. Calcul du score des utilisateurs
		List<Reponse> score =reponseDao.score(categorie, start, count);
		
		// 5. Lister tous les utilisateur de la base de données
		List<Utilisateur> utilisateur= utilisateurDao.lister();
		
		// 6. Stockage des attributs dans l'objet Request et transmissin à la vue
		request.setAttribute("suiv", suiv);
		request.setAttribute("pre", pre);
		request.setAttribute("dern", dern);
		request.setAttribute(SCORE, score);
		request.setAttribute(TOTAL, total);
		request.setAttribute(CATEGORIE, categorie);
		request.setAttribute(UTILISATEUR, utilisateur);
		
		request.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
