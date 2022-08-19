package com.sdzee.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

/**
 * <b>La classe Factory</b>: <br>
 * <ul>
 *	<li>Lire les informations de configuration depuis le fichier properties.</li>
 *	<li>Charger le driver JDBC du SGBD utilisé.</li>
 *	<li>Fournir une connexion à la base de données.</li>
 * </ul>
 * 
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet et ajout d'explications à partir des tutoriels (source 1, source 2)</p>
 * 
 * <p>Projet Sujet : L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * <p>Source2 : openclassrooms.com - Apprenez à programmer en Java - Auteur : Cyrille Herby - License CC BY-NC-SA 2.0</p>
 * 
 * @author  Huihui Huang, Leonard Namolaru
 */
public class DAOFactory {

	/* ************************ ATTRIBUTS *********************** */
	private static final String FICHIER_PROPERTIES 			= "dao.properties";
	private static final String PROPERTY_URL 				= "url";
	private static final String PROPERTY_DRIVER 			= "driver";
	private static final String PROPERTY_NOM_UTILISATEUR 	= "user";
	private static final String PROPERTY_MOT_DE_PASSE 		= "password";

	private String url;
	private String username;
	private String password;

	/* ************************ CONSTRUCTEURS *********************** */
	
	/**
	 * Constructeur: Accès à une instance de DAOFactory: pouvoir appeler la méthode
	 * getConnection() de DAOFactory <br>
	 * 
	 * @param url - L'URL de connexion à une base de données
	 * @param username - L'identifiant de la base de données
	 * @param password - Le mot de passe de la base de données
	 * 
	 * @see DAOFactory
	 */
	public DAOFactory(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Constructeur par défaut
	 * 
	 * @see DAOFactory
	 */
	public DAOFactory() {
		
	}

	/* ************************ METHODES **************************** */

	/**
	 * <p>Méthode chargée de récupérer les informations de connexion à la base de
     * données, charger le driver JDBC.</p>
     * 
     * <p>Instancier la classe DAOFactory que une seul fois (static). </p>
	 * 
	 * @return Instance de la Factory
	 * 
	 * @throws DAOConfigurationException	Si erreurs pendant l'instanciation de la Factory.
	 * 
	 * @see DAOFactory
	 * 
	 */
	public static DAOFactory getInstance() throws DAOConfigurationException {

		/* Initialisation de l'objet PROPERTIES: gére le fichier dao.properties */
		Properties properties = new Properties();

		String url;
		String driver;
		String nomUtilisateur;
		String motDePasse;

		/* 
		 * Ouverture du fichier dao.properties Appel à la méthode getResourceAsStream de
		 * l'objet ClassLoader pour ouvrir le flux dao.properties
		 */
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream(FICHIER_PROPERTIES);

		/* Si fichierProperties= null -> flux non ouvert */
		if (fichierProperties == null) {
			/*
			 * Encapsulation de l'exception dans DAOConfigurationException stipulant que le
			 * fichier n'a pas été trouvé
			 */
			throw new DAOConfigurationException("Le fichier properties " + FICHIER_PROPERTIES + " est introuvable.");
		}

		try {
			/* Chargement des propriétés contenues dans le fichier */
			properties.load(fichierProperties);

			/* Lecture des propriétés contenues dans le fichier */
			url = properties.getProperty(PROPERTY_URL);
			driver = properties.getProperty(PROPERTY_DRIVER);
			nomUtilisateur = properties.getProperty(PROPERTY_NOM_UTILISATEUR);
			motDePasse = properties.getProperty(PROPERTY_MOT_DE_PASSE);
		} catch (IOException e) {
			throw new DAOConfigurationException("Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e);
		}

		/* Chargement du driver POSTGRESQL */
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw new DAOConfigurationException("Le driver est introuvable dans le classpath.", e);
		}

		/* Instanciation du DAOFactory */
		DAOFactory instance = new DAOFactory(url, nomUtilisateur, motDePasse);

		return instance;
	}

	/**
	 * Méthode chargée de fournir une connexion à la base de données
	 * 
	 * @return La connexion à la base de données 
	 * 
	 * @throws SQLException Si non connexion à la base de données
	 * 
	 * @see DAOFactory
	 */
	public Connection getConnection() throws SQLException {
		/* Appel à la méthode getConnection() */
		return DriverManager.getConnection(url, username, password);
	}

	/*
	 * Récupération de l'implémentation des différents DAO 
	 * Fonction principale de la Factory - créer le DAO via le getter 
	 */
	
	/**
	 * Méthode qui récupére l'implémentation de DAO Utilisateur
	 * 
	 * @return L'implémentation de UtilisateurDao
	 * 
	 * @see DAOFactory
	 * @see UtilisateurDaoImpl
	 */
	public UtilisateurDao getUtilisateurDao() {
		// nous passons une instance de la classe (via le mot-clé this) à
		// l'implémentation du DAO lors de sa construction.
		return new UtilisateurDaoImpl(this);
		/*
		 * Le constructeur de UtilisateurDaoImpl : UtilisateurDaoImpl( DAOFactory
		 * daoFactory ) Le constructeur prend en argument un objet de type DAOFactory ->
		 * accès à une instance de DAOFactory -> pouvoir appeler la méthode
		 * getConnection() de DAOFactory dans UtilisateurDaoImpl
		 */
	}
	
	/**
	 * Méthode qui récupére l'implémentation de DAO Categorie
	 * 
	 * @return L'implémentation de CategorieDao
	 * 
	 * @see DAOFactory
	 * @see CategorieDaoImpl
	 */
	public CategorieDao getCategorieDao() {
		// nous passons une instance de la classe (via le mot-clé this) à
		// l'implémentation du DAO lors de sa construction.
		return new CategorieDaoImpl(this);
		/*
		 * Le constructeur de CategorieDaoImpl : CategorieDaoImpl( DAOFactory
		 * daoFactory ) Le constructeur prend en argument un objet de type DAOFactory ->
		 * accès à une instance de DAOFactory -> pouvoir appeler la méthode
		 * getConnection() de DAOFactory dans CategorieDaoImpl
		 */
	}
	
	/**
	 * Méthode qui récupére l'implémentation de DAO Question
	 * 
	 * @return L'implémentation de QuestionDao
	 * 
	 * @see DAOFactory
	 * @see QuestionDaoImpl
	 */
	public QuestionDao getQuestionDao() {
		// nous passons une instance de la classe (via le mot-clé this) à
		// l'implémentation du DAO lors de sa construction.
		return new QuestionDaoImpl(this);
		/*
		 * Le constructeur de QuestionDaoImpl : QuestionDaoImpl( DAOFactory
		 * daoFactory ) Le constructeur prend en argument un objet de type DAOFactory ->
		 * accès à une instance de DAOFactory -> pouvoir appeler la méthode
		 * getConnection() de DAOFactory dans QuestionDaoImpl
		 */
	}
	
	/**
	 * Méthode qui récupére l'implémentation de DAO Propostion
	 * 
	 * @return L'implémentation de PropositionDao
	 * 
	 * @see DAOFactory
	 * @see PropositionDaoImpl
	 */
	public PropositionDao getPropositionDao() {
		// nous passons une instance de la classe (via le mot-clé this) à
		// l'implémentation du DAO lors de sa construction.
		return new PropositionDaoImpl(this);
		/*
		 * Le constructeur de PropositionDaoImpl : PropositionDaoImpl( DAOFactory
		 * daoFactory ) Le constructeur prend en argument un objet de type DAOFactory ->
		 * accès à une instance de DAOFactory -> pouvoir appeler la méthode
		 * getConnection() de DAOFactory dans PropositionDaoImpl
		 */
	}
	
	/**
	 * Méthode qui récupére l'implémentation de DAO Reponse
	 * 
	 * @return L'implémentation de ReponseDao
	 * 
	 * @see DAOFactory
	 * @see ReponseDaoImpl
	 */
	public ReponseDao getReponseDao() {
		// nous passons une instance de la classe (via le mot-clé this) à
		// l'implémentation du DAO lors de sa construction.
		return new ReponseDaoImpl(this);
		/*
		 * Le constructeur de ReponseDaoImpl : ReponseDaoImpl( DAOFactory
		 * daoFactory ) Le constructeur prend en argument un objet de type DAOFactory ->
		 * accès à une instance de DAOFactory -> pouvoir appeler la méthode
		 * getConnection() de DAOFactory dans ReponseDaoImpl
		 */
	}
}