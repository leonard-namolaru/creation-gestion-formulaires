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
import com.sdzee.beans.Reponse;
import com.sdzee.beans.Utilisateur;
import com.sdzee.dao.DAOFactory;
import com.sdzee.forms.ReponseForm;
import com.sdzee.dao.CategorieDao;
import com.sdzee.dao.QuestionDao;
import com.sdzee.dao.ReponseDao;

import com.sdzee.dao.PropositionDao;

/**
 * <b>La classe RepondreQuestions</b>: 
 * <ul>
 * 	<li>Hérite de la classe HTTPSERVLET</li>
 *	<li>Servlet qui permet à l'utilisateur de répondre aux questions</li>
 * </ul>
 * 
 * <p>La classe HttpServlet : une classe abstraite (= on ne pourra pas l'utiliser telle quelle et il sera nécessaire de passer par une servlet qui en hérite). </p>
 * 
 * La classe propose les méthodes Java nécessaires au traitement des requêtes et réponses HTTP  :<br>
 * <ul>
 *        <li>doGet() pour gérer la méthode GET.</li>
 *        <li>doPost() pour gérer la méthode POST.</li>
 *         <li>doHead() pour gérer la méthode HEAD.</li>
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

public class RepondreQuestions extends HttpServlet{
	/* *********************************** ATTRIBUTS ********************************** */
		
	// Clé de hachage SHA qui identifie de manière unique la classe= gestionnaire de version d'une classe 
	private static final long serialVersionUID = 1L;
		
    private static final String CONF_DAO_FACTORY = "daofactory";
    private static final String ATT_CATEGORIE    = "categorie";
    private static final String ATT_QUESTION     = "questions";
    private static final String ATT_PROPOSITION  = "propositions";
    private static final String ATT_SESSION_USER = "sessionUtilisateur";
    private static final String ATT_REPONSE		 = "reponses";
	private static final String ATT_FORM         = "form";
    private static final String VUE              = "/Restreint/Repondre_aux_questions.jsp";

    private CategorieDao categorieDao;
    private QuestionDao questionDao;
    private PropositionDao propositionDao;
    private ReponseDao reponseDao;
	
	/* ************************************** METHODES ********************************** */
	    
    /**
     * Récupération des instances des DAO
     * 
     * @throws ServletException Si erreur pendant la récupération des instances
     * 
     * @see PropositionDao
	 * @see QuestionDao
	 * @see ReponseDao
	 * @see CategorieDao
     */
     public void init() throws ServletException {
       this.categorieDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCategorieDao();
       this.questionDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getQuestionDao();
       this.propositionDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getPropositionDao();
       this.reponseDao= ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getReponseDao();
     }
	    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     * @see CategorieDao#trouver(Integer)
     * @see QuestionDao#lister(Categorie)
     * @see PropositionDao#lister(Integer)
     * @see ReponseDao#trouverAll(Utilisateur, Categorie)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            // 1. Obtenir l'id de la categorie et de la question
            Integer id= Integer.parseInt(request.getParameter("id"));
            // 2. Retrouver la categorie en question
            Categorie categorie = categorieDao.trouver(id);
            
            // 3. Retrouver les questions de cette catégorie
            List<Question> question = questionDao.lister(categorie);
            
            // 4. Retrouver les propositions de cette catégorie 
            List<Proposition> propositions = propositionDao.lister(id);
            
            // 5. Retrouver l'utilisateur dans la session
            HttpSession session= request.getSession();
            Utilisateur utilisateur= (Utilisateur) session.getAttribute(ATT_SESSION_USER);
            
            // 6. Retrouver les réponses d'une catégorie de l'utilisateur
            List<Reponse> reponses = reponseDao.trouverAll(utilisateur, categorie);
            
            // 7.Stockage des attributs dans request et transmission à la vue
            session.setAttribute(ATT_CATEGORIE, categorie);
            session.setAttribute(ATT_QUESTION, question);
            session.setAttribute(ATT_PROPOSITION, propositions);     
            request.setAttribute("reponses", reponses);
            
            // 8. Direction à la page pour répondre à le formulaire
            this.getServletContext().getRequestDispatcher( VUE).forward( request, response );
            
    }
	        
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @see ReponseForm#enregistrementReponses(HttpServletRequest, Integer, Utilisateur)
	 */
	protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{ 		
		// 1. Préparation de l'objet formulaire
		ReponseForm form = new ReponseForm(questionDao, categorieDao, propositionDao, reponseDao);
		
		// 2. Obtenir l'id de la categorie
        Integer categorieId= Integer.parseInt(request.getParameter("id"));
        
        // 3. Récupération de l'objet utilisateur correspondant dans la session 
		HttpSession session= request.getSession();
        Utilisateur utilisateur= (Utilisateur) session.getAttribute(ATT_SESSION_USER);
        
        List<Reponse> reponses = form.enregistrementReponses(request, categorieId, utilisateur);
       
        // Stockage des attributs dans request et transmission à la vue
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_REPONSE, reponses);
                
        // Si l'utilisateur a répondu correctement à la catégorie alors direction à la page du score qu'il a obtenu
        if (form.getErreurs().isEmpty()) {
        	request.getRequestDispatcher("/mes-resultats?id="+categorieId+"&email="+utilisateur.getEmail()).forward(request, response);
        // Sinon, rester sur la page pour corriger les erreurs
        } else {
        	this.getServletContext().getRequestDispatcher(VUE).forward( request, response );
        }
	}
	
}