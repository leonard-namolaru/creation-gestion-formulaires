package com.sdzee.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * <b>La classe RestrictionFilter</b>: <br>
 * <ul>
 * 	<li>Implémente de la classe Filter</li>
 *	<li>Restreint l'accés à les pages utilisateurs et administraturs</li>
 * </ul>
 * 
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet et ajout d'explications à partir du tutoriel (source 1)</p>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * 
 * @author Huihui Huang
 *
 */
public class RestrictionFilter implements Filter {
	
	
	public static final String VUE			            = "/index.jsp";
	public static final String ATT_SESSION_UTILISATEUR	= "sessionUtilisateur";
	public static final String ATT_SESSION_ADMIN		= "sessionAdmin";
	
	
	@Override
	public void init(FilterConfig config) throws ServletException {
	}
	

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/* ********************** METHODES **************************** */
	
	/**
	 * Méthode qui restreint certaines pages 
	 * 
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		/* Cast de l'objet request et response */
		HttpServletRequest request= (HttpServletRequest) req;
		HttpServletResponse response= (HttpServletResponse) resp;
		
		/* Récupération de la session depuis la requête */
		HttpSession session= request.getSession();
		
        
        /* Non-filtrage des ressource statiques */
        String chemin = request.getRequestURI().substring( request.getContextPath().length() );
        if (chemin.startsWith( "/CSS" ) ||chemin.startsWith( "/JS" ) ||chemin.startsWith("/index.jsp") || chemin.startsWith("/resetMotDePasse") ||chemin.startsWith("/connexion") || chemin.startsWith("/page-inscription")||chemin.startsWith("/motdepasse-oublie")) {
        	/* Poursuite de la requête en cours */
            chain.doFilter( request, response);
            return;
        }
        
        /* Restriction d'accès à la ressource si l'utilisateur ou l'administrateur n'a pas d'objet en session */
        if ( session.getAttribute( ATT_SESSION_ADMIN ) == null && session.getAttribute( ATT_SESSION_UTILISATEUR ) == null) {
        	/* Redirection vers la page public */
        	response.sendRedirect(request.getContextPath()+ VUE);
        } else {
        	/* Affichage de la page restreinte*/
        	chain.doFilter(req, resp);
        }

	}


}
