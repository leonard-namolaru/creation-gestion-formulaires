package com.sdzee.config;

import javax.servlet.ServletContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.sdzee.dao.DAOFactory;

/**
 * <b>La classe InitialisationDaoFactory</b>: <br>
 * <ul>
 * 	<li>Classe qui implémente de l'interface ServletContextListener </li>
 *</ul>
 * 
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet et ajout d'explications à partir du tutoriel (source 1)</p>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * 
 * @author  Huihui Huang, Leonard Namolaru
 */

public class InitialisationDaoFactory implements ServletContextListener {
	
	/* *************************** ATTRIBUTS ************************* */
    private static final String ATT_DAO_FACTORY = "daofactory";

    private DAOFactory daoFactory;
    
    
    /* ************************** METHODES ***************************** */
    
    /**
     * Méthode lancée au démarrage de l'application
     * 
     * @see ServletContextListener
     * @see InitialisationDaoFactory
     * @see DAOFactory
     * @see DAOFactory#getInstance()
     */
    public void contextInitialized( ServletContextEvent event ) {
        /* Récupération du ServletContext lors du chargement de l'application */
        ServletContext servletContext = event.getServletContext();
        /* Instanciation de notre DAOFactory */
        this.daoFactory = DAOFactory.getInstance();
        /* Enregistrement dans un attribut ayant pour portée toute l'application */
        servletContext.setAttribute( ATT_DAO_FACTORY, this.daoFactory );
    }

    
    /**
     * Méthode lancée à la fermeture de l'application
     * 
     * @see ServletContextListener
     * @see InitialisationDaoFactory
     */
    public void contextDestroyed( ServletContextEvent event ) {
        /* Rien à réaliser lors de la fermeture de l'application... */
    }
}