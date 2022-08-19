package com.sdzee.servlets;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.beans.Categorie;
import com.sdzee.beans.Proposition;
import com.sdzee.beans.Question;
import com.sdzee.dao.CategorieDao;
import com.sdzee.dao.DAOFactory;
import com.sdzee.dao.PropositionDao;
import com.sdzee.dao.QuestionDao;
import com.sdzee.forms.ParametresCategorieForm;

/**
 * <b>La classe UpdateCategorie</b>: 
 * <ul>
 * 	<li>Hérite de la classe HTTPSERVLET</li>
 * 	<li>Servlet qui fait la mise à jour de la catégorie</li>
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
public class UpdateCategorie extends HttpServlet {
	
	/* *********************************** ATTRIBUTS ********************************** */
	
	// Clé de hachage SHA qui identifie de manière unique la classe= gestionnaire de version d'une classe 
	private static final long serialVersionUID = 1L;
	
	private static final String CONF_DAO_FACTORY	= "daofactory";
	private static final String ATT_FORM         	= "form";
	private static final String ATT_QUESTION		= "question";
	private static final String ATT_CATEGORIE       = "categorie";
	private static final String ATT_PROPOSITION  	= "propositions";
	private static final String VUE              	= "/RestreintAdmin/MiseAJour_categorie.jsp";

	
	private CategorieDao categorieDao;
    private QuestionDao questionDao;
    private PropositionDao propositionDao;
       
	/* ************************************** METHODES ********************************** */
	
	/**
     * Récupération d'une instance des DAO
     * 
     * @throws ServletException Si erreur pendant la récupération des instances
     * 
     * @see PropositionDao
     * @see QuestionDao
     * @see CategorieDao
     */
    public void init() throws ServletException {
        this.categorieDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCategorieDao();
        this.questionDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getQuestionDao();
        this.propositionDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getPropositionDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * @see CategorieDao#trouver(Integer)
	 * @see QuestionDao#lister(Categorie)
	 * @see PropositionDao#lister(Integer)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Obtenir l'id de la categorie et de la question
        Integer id= Integer.parseInt(request.getParameter("id"));
        // 2. Retrouver la categorie en question
        Categorie categorie = categorieDao.trouver(id);
        
        // 3. Retrouver les questions de cette catégorie
        List<Question> questions = questionDao.lister(categorie);
        
        // 4. Retrouver les propositions de cette catégorie
        List<Proposition> propositions = propositionDao.lister(id);
        

        
        HttpSession session= request.getSession();
        // 4.Stockage de la catégorie dans request et transmission à la vue
        session.setAttribute(ATT_CATEGORIE, categorie);
        session.setAttribute(ATT_QUESTION, questions);
        session.setAttribute(ATT_PROPOSITION, propositions);
        
        
        // 5. Direction à la page pour répondre à le formulaire
        this.getServletContext().getRequestDispatcher( VUE).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @see CategorieDao#trouver(Integer)
	 * @see QuestionDao#lister(Categorie)
	 * @see PropositionDao#lister(Integer)
	 * @see UpdateCategorieForm#updateParametresCategorie(HttpServletRequest)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. Préparation de l'objet modification des formulaires 
		ParametresCategorieForm form= new ParametresCategorieForm(categorieDao, questionDao, propositionDao);
		
        // 2. Obtenir l'id de la categorie et de la question
        Integer id= Integer.parseInt(request.getParameter("id"));
        
        // 3. Retrouver la categorie en question
        Categorie categorie = categorieDao.trouver(id);

        // 4. Traitement de la requête et récupération du bean en résultant 
        categorie = form.updateParametresCategorie(request, categorie);
        
        // 5. Retrouver les questions de cette catégorie
        List<Question> questions = questionDao.lister(categorie);        
        
        // 6. Retrouver les propositions de cette catégorie
        List<Proposition> propositions = propositionDao.lister(id);
        
	
        // Stockage des attributs dans request et transmission à la vue
        request.setAttribute(ATT_FORM, form );
        request.setAttribute(ATT_CATEGORIE, categorie );
        request.setAttribute(ATT_QUESTION, questions);
        request.setAttribute(ATT_PROPOSITION, propositions);

     		
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	}

}
