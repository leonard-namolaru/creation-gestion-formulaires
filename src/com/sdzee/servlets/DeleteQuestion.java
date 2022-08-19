package com.sdzee.servlets;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdzee.beans.Categorie;
import com.sdzee.beans.Proposition;
import com.sdzee.beans.Question;
import com.sdzee.beans.Reponse;
import com.sdzee.dao.CategorieDao;
import com.sdzee.dao.DAOException;
import com.sdzee.dao.DAOFactory;
import com.sdzee.dao.PropositionDao;
import com.sdzee.dao.QuestionDao;
import com.sdzee.dao.ReponseDao;

/**
 * <b>La classe DeleteCategorie</b>: 
 * <ul>
 * 	<li>Hérite de la classe HTTPSERVLET</li>
 * 	<li>Servlet qui supprime une question d'une catégorie</li>
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
 * @author Huihui Huang, Leonard Namolaru
 */
public class DeleteQuestion extends HttpServlet {
	
	// Clé de hachage SHA qui identifie de manière unique la classe= gestionnaire de version d'une classe 
	private static final long serialVersionUID = 1L;
		
	private static final String CONF_DAO_FACTORY = "daofactory";
	   
    private QuestionDao questionDao;
    private PropositionDao propositionDao;
    private ReponseDao reponseDao;
    private CategorieDao categorieDao;
	      
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
	 */
	public void init() throws ServletException {
        this.questionDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getQuestionDao();
        this.propositionDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getPropositionDao();
        this.reponseDao= ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getReponseDao();
        this.categorieDao = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY )).getCategorieDao();
	}
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @see CategorieDao#trouver(Integer)
	 * @see QuestionDao#trouver(Integer)
	 * @see QuestionDao#supprimer(Categorie, Question, CategorieDao)
	 * @see PropositionDao#supprimer(Proposition)
	 * @see PropositionDao#listerQuestionId(Question)
	 * @see ReponseDao#supprimerReponse(Reponse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Obtenir l'ID de la catégorie et question
		Integer id= Integer.parseInt(request.getParameter("id"));
		Integer idCategorie= Integer.parseInt(request.getParameter("idCategorie"));;
		
		// 2. Trouver la question via son id
		Question question= questionDao.trouver(id);
		
		// 3. Trouver la catégorie via son id
		Categorie categorie= categorieDao.trouver(idCategorie);
		
		String resultat;
		
		try {	
			if (question != null) {	
				
				// 4. Lister les propositions de la question
				List<Proposition> propositions = propositionDao.listerQuestionId(question);
				// 5. Lister les réponses de la question
				List<Reponse> reponses = reponseDao.trouver(question);
				
				// 6. Supprimer les réponses 
				for (Reponse r : reponses) 
					reponseDao.supprimerReponse(r);

				// 7. Supprimer les propositions
				for (Proposition proposition: propositions) 
					propositionDao.supprimer(proposition);
				
				// 8. Supprimer la quesiton
				questionDao.supprimer(categorie, question, categorieDao);
				
				resultat="<article class=\"succes\">Succès de la suppression de la question.</article>";
			} else {
				resultat="<article class=\"erreur\">Échec de la suppression de la question.</article>";
			}

		}  catch ( DAOException e ) {
			resultat = "<article class=\"erreur\">Échec de la suppresion de la question : une erreur imprévue est survenue, cela semble être un problème de communication avec la base de données du site. Merci de réessayer dans quelques instants.</article>";
			e.printStackTrace();
		}
		
		// Stockage des attributs dans l'objet Request et transmission à la vue	
		request.setAttribute("message", resultat);
		
		// 9. Reste sur la page
		request.getRequestDispatcher("/mise-a-jour-categorie?id="+categorie.getId()).forward(request, response);
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}


}
