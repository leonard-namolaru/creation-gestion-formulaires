package com.sdzee.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdzee.beans.Categorie;
import com.sdzee.dao.DAOFactory;
import com.sdzee.forms.ParametresCategorieForm;
import com.sdzee.dao.CategorieDao;
import com.sdzee.dao.QuestionDao;
import com.sdzee.dao.PropositionDao;

/**
 * <b>La classe ParametresCategorie</b>: 
 * <ul>
 * 	<li>Hérite de la classe HTTPSERVLET</li>
 * 	<li>Servlet qui permet de paramètrer une catégorie</li>
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
 * @author Leonard Namolaru
 */
public class ParametresCategorie extends HttpServlet{
	/* *********************************** ATTRIBUTS ********************************** */
		
	// Clé de hachage SHA qui identifie de manière unique la classe= gestionnaire de version d'une classe 
	private static final long serialVersionUID = 1L;
		
	private static final String CONF_DAO_FACTORY = "daofactory";
	private static final String ATT_CATEGORIE    = "categorie";
	private static final String ATT_FORM         = "form";
	private static final String VUE              = "/RestreintAdmin/parametres_categorie.jsp";
	
	private CategorieDao categorieDao;
	private QuestionDao questionDao;
	private PropositionDao propositionDao;
	     
	/* ************************************** METHODES ********************************** */
	    
	/**
     * Récupération d'une instance des DAO
     * 
     * @throws ServletException Si erreur pendant la récupération des instances
     * 
     * @see CategorieDao
     * @see PropositionDao
     * @see QuestionDao
     */
	public void init() throws ServletException {
		this.categorieDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getCategorieDao();
		this.questionDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getQuestionDao();
		this.propositionDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getPropositionDao();
	}

	    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{ 
	        
	    /* Affichage de la page */
		this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
		
		/* this.getServletContext() : public ServletContext getServletContext()
		 * Depuis notre instance de servlet (this), 
		 * nous appelons la méthode getServletContext(). 
		 * Celle-ci nous retourne alors un objet ServletContext, 
		 * qui fait référence au contexte commun à toute l'application : 
		 * celui-ci contient un ensemble de méthodes qui permettent à une servlet de communiquer
		 * avec le conteneur de servlet.
		 */
		
		/* getRequestDispatcher() : RequestDispatcher getRequestDispatcher(String path)
		 * La méthode permettant de manipuler une ressource, 
		 * que nous appliquons à notre page JSP.
		 * 
		 * Elle retourne un objet RequestDispatcher,
		 * qui agit ici comme une enveloppe autour de notre page JSP -> pierre angulaire de votre servlet
		 * c'est grâce à lui que notre servlet est capable de faire suivre nos objets requête et réponse à une vue. 
		 * Il est impératif d'y préciser le chemin complet vers la JSP, en commençant obligatoirement par un /
		 */

		/* forward() - void forward(ServletRequest request, ServletResponse response) 
		 * réexpédier la paire requête/réponse HTTP vers notre page JSP via sa méthode forward()
		 * 
		 * JavaDoc : Forwards a request from a servlet to another resource (servlet, JSP file, or HTML file) on the server. This method allows one servlet to do preliminary processing of a request and another resource to generate the response. 
		 */
	}
	        
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 * @see ParametresCategorieForm#enregistrementParametresCategorie(HttpServletRequest)
	 */
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{ 
    	//throws : ce mot clé permet de signaler qu'un morceau de code, une méthode, une classe… est potentiellement dangereux. Il est suivi du nom de la classe qui va gérer l'exception.

    	/* Préparation de l'objet formulaire */
 	    ParametresCategorieForm form = new ParametresCategorieForm( categorieDao, questionDao, propositionDao );

 	     /* Traitement de la requête et récupération du bean en résultant */
 	     Categorie categorie = form.enregistrementParametresCategorie( request );

 	      /* La méthode setAttribute() de l'objet requête : enregistrer un attribut 
 	       * Cette méthode prend en paramètre le nom de l'attribut suivi de l'objet lui-même */
 	        request.setAttribute( ATT_FORM, form );//stockage du formulaire dans l'objet request
 	        request.setAttribute( ATT_CATEGORIE, categorie );//stockage du bean dans l'objet request

 	        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
 	}
		
}