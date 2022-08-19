package com.sdzee.servlets;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sdzee.dao.DAOFactory;
import com.sdzee.dao.PropositionDao;
import com.sdzee.dao.QuestionDao;
import com.sdzee.forms.DiagrammeForm;

/** 
 * <b>La classe DiagrammeAdmin</b>: 
 * <ul>
 * 	<li>Hérite de la classe HTTPSERVLET</li>
 * 	<li>Génération des diagrammes sur la page JSP</li>
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
public class DiagrammeAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    private static final String CONF_DAO_FACTORY 	= "daofactory";
    private static final String VUE              	= "/RestreintAdmin/Diagramme.jsp";
    
    private QuestionDao questionDao;
    private PropositionDao propositionDao;
   
	/* ************************************** METHODES ********************************** */
	    
    /**
     * Récupération des instances des DAO
     * 
     * @throws ServletException Si erreur pendant la récupération des instances
     * 
     * @see PropositionDao
     * @see QuestionDao
     */
     public void init() throws ServletException {
    	 this.propositionDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getPropositionDao();
    	 this.questionDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getQuestionDao();
     }
     
     /**
 	  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
 	  * @see DiagrammeForm#genereDiagramme(HttpServletRequest)
 	  */
     public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {          		
    	 DiagrammeForm form= new DiagrammeForm(propositionDao, questionDao);
    	 
    	 // Génération des diagrammes
    	 List<String> images= form.genereDiagramme(request);
    	     	 
    	 // Ajout des diagrammes dans request et session
    	 HttpSession session= request.getSession();
    	 session.setAttribute("images", images);    	 
    	 
    	 request.getRequestDispatcher(VUE).forward(request, response);


 	}

     
     /**
 	  * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
 	  */
     public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 doGet(request, response);
     }

}
