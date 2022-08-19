package com.sdzee.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdzee.beans.Categorie;
import com.sdzee.beans.Utilisateur;
import com.sdzee.dao.CategorieDao;
import com.sdzee.dao.DAOFactory;
import com.sdzee.dao.PropositionDao;
import com.sdzee.dao.QuestionDao;
import com.sdzee.dao.UtilisateurDao;
import com.sdzee.forms.ReponseForm;

/**
 * <b>La classe resultatUtilisateur</b>: 
 * <ul>
 * 	<li>Hérite de la classe HTTPSERVLET</li>
 * 	<li>Servlet qui affiche les résultats des catégorie que l'utilisateur a répondu</li>
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
public class ResultatUtilisateur extends HttpServlet {
	/* *********************************** ATTRIBUTS ********************************** */
	
	// Clé de hachage SHA qui identifie de manière unique la classe= gestionnaire de version d'une classe 
	private static final long serialVersionUID = 1L;
       
	private static final String CONF_DAO_FACTORY 	= "daofactory";
    private static final String VUE              	= "/Restreint/Resultats_formulaire.jsp";
    private static final String SCORE				= "score";
    private static final String CATEGORIE			= "categorie";
    private static final String TOTAL				= "total";
    private static final String UTILISATEUR			= "utilisateur";
    
    private UtilisateurDao utilisateurDao;
    private PropositionDao propositionDao;
    private CategorieDao categorieDao;
    private QuestionDao questionDao;

    /* ************************************** METHODES ********************************** */
	
	/**
     * Récupération des instances des DAO
     * 
     * @throws ServletException Si erreur pendant la récupération des instances
     * 
     * @see UtilisateurDao
     * @see PropositionDao
     * @see CategorieDao
     * @see QuestionDao
     */
	public void init() throws ServletException {
        this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
        this.propositionDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getPropositionDao();
        this.categorieDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCategorieDao();
        this.questionDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getQuestionDao();  
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @see CategorieDao#trouver(Integer)
	 * @see UtilisateurDao#trouver(String)
	 * @see PropositionDao#scoreObtenu(Categorie, Utilisateur)
	 * @see PropositionDao#scoreTotal(Categorie)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Obtenir l'id de la categorie
        Integer id= Integer.parseInt(request.getParameter("id"));
        
        // 2. Obtenir l'email de l'utilisateur
        String email= request.getParameter("email");
    	
        // 3. Trouver la catégorie via son id
        Categorie categorie= categorieDao.trouver(id);
        
        // 4. Trouver l'utilisateur via son email
    	Utilisateur utilisateur= utilisateurDao.trouver(email);

        // 5. Calcul du score obtenu par l'utilisateur
        int score= propositionDao.scoreObtenu(categorie, utilisateur);
        
        // 6. Calcul du score total d'une catégorie
     	ReponseForm form = new ReponseForm(questionDao, propositionDao);
        int total= form.calculScoreTotal(categorie);
        
        // Stockage des attributs dans l'objet Request et transmission à la vue
        request.setAttribute(CATEGORIE, categorie);
        request.setAttribute(SCORE, score);
        request.setAttribute(TOTAL, total);
        request.setAttribute(UTILISATEUR, utilisateur);
        
        request.getServletContext().getRequestDispatcher(VUE).forward(request, response);
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
