package com.sdzee.dao;

import static com.sdzee.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.sdzee.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.sdzee.beans.Categorie;
import com.sdzee.beans.Question;

/**
 * <b>La classe CategorieDaoImpl</b>: <br>
 * <ul>
 * 	<li>Classe qui implémentate de l'interface DAO Question</li>
 * 	<li>Manipulation de la table l2n1_questions</li>
 * </ul>
 * 
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet et ajout d'explications à partir des tutoriels (source 1, source 2)</p>
 * 
 * <p>Projet Sujet : L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * <p>Source2 : openclassrooms.com - Apprenez à programmer en Java - Auteur : Cyrille Herby - License CC BY-NC-SA 2.0</p>
 * 
 * @author Leonard Namolaru, Huihui Huang
 */
public class QuestionDaoImpl implements QuestionDao {
	
	/* *************************** ATTRIBUTS ************************* */

	private static final String SQL_INSERT 						= "INSERT INTO l2n1_questions (categorie_id, question_groupe, question_label, question_type, question_obligatoire) VALUES (?, ?, ?, ?, ?)";
	
	private static final String SQL_SELECT 						= "SELECT * FROM l2n1_questions where categorie_id= ?";
	private static final String SQL_SELECT_BY_NUMERO_GROUPE 	= "SELECT * FROM l2n1_questions where categorie_id = ? AND question_groupe = ?";
	private static final String SQL_SELECT_QUESTION			 	= "SELECT * FROM l2n1_questions where question_id= ?";
	
	private static final String SQL_DELETE 						= "DELETE FROM l2n1_questions WHERE question_id= ?";
	
	private static final String SQL_UPDATE 						= "UPDATE l2n1_questions set question_label= ?, question_type= ?, question_obligatoire= ? where question_id = ?";
	private static final String SQL_UPDATE_GROUPE 				= "UPDATE l2n1_questions set question_groupe = ? where question_id = ?";
	
	private DAOFactory daoFactory;

	/* ************************** CONSTRUCTEURS ************************* */

	/**
	 * Constructeur: Accès à une instance de DAOFactory: pouvoir appeler la méthode
	 * getConnection() de DAOFactory <br>
	 * 
	 * @param daoFactory - DAOFactory
	 * 
	 * @see QuestionDaoImpl()
	 */
	public QuestionDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	/**
	 * Constructeur par défaut
	 * 
	 * @see QuestionDaoImpl
	 */
	public QuestionDaoImpl() {
		
	}

	/* ************************** METHODES ************************* */
	
	/**
	 * Méthode qui créer une liste de question par catégorie
	 * 
	 * @param categorie - Un bean Categorie
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see QuestionDaoImpl
	 * @see QuestionDao#creer(Categorie)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Leonard Namolaru
	 */
	public void creer(Categorie categorie) throws DAOException {
		Vector<Question> questions = categorie.getQuestions();
		Long categorieId = categorie.getId();
		
		for(Question question : questions) {
			question.setCategorieId(categorieId);
			creer(question);		
		}
	}
		
	/**
	 * Méthode qui créer une question
	 * 
	 * @param question - Un bean Question
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see QuestionDaoImpl
	 * @see QuestionDao#creer(Question)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Leonard Namolaru
	 */
	public void creer(Question question) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
			connexion = daoFactory.getConnection();
			// 2. Initialisation de la requête
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, question.getCategorieId(), question.getQuestionGroupe(), question.getLabel(), question.getType(), question.getObligatoire() );
			// 3. Exécution de la requête
			// executeUpdate() : l'éxécution d'une instruction menant à la modification de la BD(INSERT, UPDATE, DELETE...)
			int statut = preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException("Echec de la création de la question, aucune ligne ajoutée dans la table.");
			}
			// 4. Générer automatiquement un id
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			// 5. Ecrit l'id généreé dans la colomne ID de la table l2n1_question
			if (valeursAutoGenerees.next()) {
				question.setId(valeursAutoGenerees.getLong(1));
			} else {
				throw new DAOException("Echec de la crétion de la question dans la base de donnees, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
		}
	}
	
	/**
	 * Méthode pour retrouver les question existantes dans la BDD
	 * 
	 * @param categorie - Un bean Categorie 
	 * 
	 * @return La liste des beans Question
	 * 
	 * @throws DAOException Si erreurs pendant la requêtes SQL
	 * 
	 * @see QuestionDao#lister(Categorie)
	 * @see QuestionDaoImpl
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	 public List<Question> lister(Categorie categorie) throws DAOException {
		 Connection connexion= null;
		 PreparedStatement preparedStatement= null;
		 ResultSet resultSet= null;
		 List<Question> questions= new ArrayList<Question>();
			
		 try {
			 daoFactory= DAOFactory.getInstance();
			 // 1. Connection à la base de données
			 connexion = daoFactory.getConnection();
			 // 2. Initialisation de la requête
			 preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT, false, categorie.getId());
			 // 3. Exécution de la requête
			 // executeQuery(): exécution d'un SELECT
			 resultSet = preparedStatement.executeQuery();
			 // 4. Mapping du résultat dans l'objet Question ou renvoie null
			 while (resultSet.next()) {
				 questions.add( map( resultSet ));
			 }
			 
		 } catch ( SQLException e ) {
	            throw new DAOException( e );
	     } finally {
	    	 fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	     }

		 return questions;
	 }
	 
	 
	 /**
	  * Méthode qui liste la liste de question d'une catégorie par groupe 
	  * 
	  * @param categorie - Un bean Categorie
	  * @param numeroGroupe - Le numéro de groupe 
	  * 
	  * @return La liste de question
	  * 
	  * @throws DAOException Si erreurs pendant la requêtes SQL
	  * 
	  * @see QuestionDao#lister(Categorie, Integer)
	  * @see QuestionDaoImpl
	  * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	  * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	  * @see DAOFactory#getInstance()
	  * @see DAOFactory#getConnection()
	  * 
	  * @author Leonard Namolaru
	  */
	 public List<Question> lister(Categorie categorie, Integer numeroGroupe) throws DAOException {
		 Connection connexion= null;
		 PreparedStatement preparedStatement= null;
		 ResultSet resultSet= null;
		 List<Question> questions= new ArrayList<Question>();
			
		 try {
			 daoFactory= DAOFactory.getInstance();
			 // 1. Connection à la base de données
			 connexion = daoFactory.getConnection();
			 // 2. Initialisation de la requête
			 preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_BY_NUMERO_GROUPE, false, categorie.getId(), numeroGroupe);
			 // 3. Exécution de la requête
			 // executeQuery(): exécution d'un SELECT
			 resultSet = preparedStatement.executeQuery();
			 // 4. Mapping du résultat dans l'objet Question ou renvoie null
			 while (resultSet.next()) {
				 questions.add( map( resultSet ));
			 }
			 
		 } catch ( SQLException e ) {
	            throw new DAOException( e );
	     } finally {
	    	 fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	     }

		 return questions;
	 }

	 
	 /**
	  * Méthode pour retrouver les question existantes dans la BDD
	  * 
	  * @param id - ID d'un bean Question
	  * 
	  * @return Liste des questions dans la catégorie 
	  * 
	  * @throws DAOException Si erreurs pendant la requêtes SQL
	  * 
	  * @see QuestionDao#trouver(Integer)
	  * @see QuestionDaoImpl
	  * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	  * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	  * @see DAOFactory#getInstance()
	  * @see DAOFactory#getConnection()
	  * 
	  * @author Huihui Huang
	  */
	 public Question trouver(Integer id) throws DAOException {
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 Question question= null;

		 try {
			 daoFactory= DAOFactory.getInstance();
			 // 1. Connection à la base de données
			 connexion = daoFactory.getConnection();
			 // 2. Initialisation de la requête
			 preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_QUESTION, false, id);
			 // 3. Exécution de la requête
			 // executeQuery(): exécution d'un SELECT
			 resultSet = preparedStatement.executeQuery();
			 // 4. Mapping du résultat dans l'objet Question ou renvoie null
			 if (resultSet.next()) {
				 question = map(resultSet);
			 }
		 } catch (SQLException e) {
			 throw new DAOException(e);
		 } finally {
			 fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		 }

		 return question;
	 }
	 
	 
	 /**
	  * Méthode qui supprime une question 
	  * 
	  * @param question - Un bean Question
	  * 
	  * @throws DAOException Si erreurs pendant la requête SQL
	  * 
	  * @see QuestionDao#supprimer(Question)
	  * @see QuestionDaoImpl
	  * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	  * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	  * @see DAOFactory#getInstance()
	  * @see DAOFactory#getConnection()
	  * 
	  * @author Huihui Huang
	  */
     public void supprimer (Question question) throws DAOException {

         Connection connexion = null;
         ResultSet resultSet = null;
         PreparedStatement preparedStatement = null;

         try {

                 daoFactory= DAOFactory.getInstance();
                 // 1. Connection à la base de données
                 connexion = daoFactory.getConnection();

                 // 2. Initialisation de la requête
                 preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE, false, question.getId());

                 // 3. Exécution de la requête
                 // executeUpdate() : l'éxécution d'une instruction menant à la modification de la BD(INSERT, UPDATE, DELETE...)
                 int statut= preparedStatement.executeUpdate();
                 if (statut == 0) {
                	 throw new DAOException("Echec de la suppresion de la question.");
                 }

         } catch (SQLException e) {
                 e.printStackTrace();
                 throw new DAOException(e);
         } finally {
                 fermeturesSilencieuses(resultSet, preparedStatement, connexion);
         }

     }

     
     /**
	  * Méthode qui supprime une question d'une catégorie (Le groupe en plus)
	  * 
	  * @param categorie - Un bean Categorie
	  * @param question - Un bean Question
	  * @param categorieDao - CategorieDao
	  * 
	  * @throws DAOException Si erreurs pendant la requête SQL
	  * 
	  * @see QuestionDao#supprimer(Categorie, Question, CategorieDao)
	  * @see QuestionDaoImpl
	  * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	  * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	  * @see DAOFactory#getInstance()
	  * @see DAOFactory#getConnection()
	  * 
	  * @author Leonard Namolaru
	  */
	 public void supprimer (Categorie categorie, Question question, CategorieDao categorieDao) throws DAOException {
		Connection connexion = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		Integer numeroGroupe = question.getQuestionGroupe();
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
			connexion = daoFactory.getConnection();
			// 2. Initialisation de la requête
			preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE, false, question.getId());
			// 3. Exécution de la requête
			// executeUpdate() : l'éxécution d'une instruction menant à la modification de la BD(INSERT, UPDATE, DELETE...)
			int statut= preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException("Echec de la suppresion de la question.");
			}
			
			List<Question> questions = lister(categorie);
			boolean check = false;
			for(Question q : questions) {
				if(q.getQuestionGroupe() == numeroGroupe)
					check = true;
			}
			
			if(check == false) {
				for(Question q : questions) {
					if(q.getQuestionGroupe() > numeroGroupe)
						q.setQuestionGroupe( q.getQuestionGroupe() - 1 );
				}				
				categorie.setNbGroupes( categorie.getNbGroupes() - 1);
				categorieDao.update(categorie);
				updateQuestions(questions);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
	 }
	 
	 
	 /**
	  * Méthode qui fait la mise à jour d'une question 
	  * 
	  * @param request - Une Requête HTTP qui contient la demande du client
	  * @param questions - La liste de question
	  * @param categorie - Une catégorie
	  * 
	  * @throws DAOException Si erreurs pendant la requête SQL
	  * 
	  * @see QuestionDao#update(HttpServletRequest, List, Categorie)
	  * @see QuestionDaoImpl
	  * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	  * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	  * @see DAOFactory#getInstance()
	  * @see DAOFactory#getConnection()
	  * 
	  * @author Leonard Namolaru
	  */
	 public void update (HttpServletRequest request, List<Question> questions, Categorie categorie) throws DAOException {
		Connection connexion = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		int counter = 1;
		
		for (Question question: questions) {
			 String questionId = getValeurChamp( request, "question" + counter + "_id" );
			 boolean isNew = (questionId != null) ? false : true;
			 question.setCategorieId( categorie.getId() );
			 
			 if( isNew )
			 {
				 creer(question);
			 }
			 else
			 {
				 question.setId( (long) Integer.parseInt(questionId)  );
				 try {
						daoFactory = DAOFactory.getInstance();
						// 1. Connection à la base de données
						connexion = daoFactory.getConnection();
						// 2. Initialisation de la requête
						preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, false, question.getLabel(), question.getType(), question.getObligatoire(), question.getId());
						// 3. Exécution de la requête
						// executeUpdate() : l'éxécution d'une instruction menant à la modification de la BD(INSERT, UPDATE, DELETE...)
						int statut = preparedStatement.executeUpdate();
						if (statut == 0) {
							throw new DAOException("Echec de la la MAJ de la question.");
						}

					} catch (SQLException e) {
						throw new DAOException(e);
					} finally {
						fermeturesSilencieuses(resultSet, preparedStatement, connexion);
					}
			 }
							
			counter++;
		}
	 }
	 
	 
	 /**
	  * Méthode abstraite qui fait la mise à jour d'une liste de question
	  * 
	  * @param questions - Une liste de beans Question
	  * 
	  * @throws DAOException Si erreurs pendant la requête SQL
	  * 
	  * @see QuestionDao#updateQuestions(List)
	  * @see QuestionDaoImpl
	  * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	  * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	  * @see DAOFactory#getInstance()
	  * @see DAOFactory#getConnection()
	  * 
	  * @author Leonard Namolaru
	  */
	 public void updateQuestions(List<Question> questions) throws DAOException {
		Connection connexion = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		for(Question question : questions){
			try {
				daoFactory= DAOFactory.getInstance();
				// 1. Connection à la base de données
				connexion = daoFactory.getConnection();
				// 2. Initialisation de la requête
				preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_GROUPE, false, question.getQuestionGroupe(), question.getId());
				// 3. Exécution de la requête
				// executeUpdate() : l'éxécution d'une instruction menant à la modification de la BD(INSERT, UPDATE, DELETE...)
				int statut= preparedStatement.executeUpdate();
				if (statut == 0) {
					throw new DAOException("Echec de la mise à jour de la question.");
				}
			} catch (SQLException e) {
				throw new DAOException(e);
			} finally {
				fermeturesSilencieuses(resultSet, preparedStatement, connexion);
			}
		}
	}
	 
	 /**
	  * Méthode utilitaire qui retourne null si un champ est vide, et son contenu sinon
	  * 
	  * @param request - Une requête HTTP qui contient la demande du client
	  * @param nomChamp - Le champ spécifié
	  * 
	  * @see QuestionDaoImpl
	  */
	 private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
	        
		 /*Appel à la méthode request.getParameter() : String getParameter(String name)
		  * récupération d'un champ du formulaire */
		 String valeur = request.getParameter( nomChamp );
	    	
		 //String.trim() - Supprimer les espaces sur les côtés droit et gauche de la chaîne (début et fin de la chaîne).
		 if ( valeur == null || valeur.trim().length() == 0 ) {
			 return null;
		 } else {
			 return valeur;
		 }
	 }

	 
	/**
	 * Méthode qui permet la correspondance(mapping) entre une ligne issue de la table des utilisateurs(Resultset) et un bean Categorie
	 * 
	 * @param resultSet	- Les résultats d'une requête
	 * 
	 * @return Un bean Question
	 * 
	 * @throws SQLException Si erreurs pendant la requête SQL
	 * 
	 * @see QuestionDaoImpl
	 */
	private static Question map( ResultSet resultSet ) throws SQLException {
    	/*La méthode prend en argument un ResultSet dont le curseur a déjà été correctement positionné, 
    	et place chaque champ lu dans la propriété correspondante du nouveau bean créé */
		
        Question question = new Question();
        
        question.setId( resultSet.getLong( "question_id" ) );
        question.setCategorieId( resultSet.getLong( "categorie_id" ) );
        question.setQuestionGroupe( resultSet.getInt( "question_groupe" ) ); /* Les questions d'une catégorie peuvent être divisées en plusieurs groupes */
        question.setLabel( resultSet.getString( "question_label" ) ); /* Le texte de la question elle-même */
        question.setType( resultSet.getInt( "question_type" ) ); /* Par exemple: boutons radio. Chaque type de question aura un numéro spécifique. Par exemple - le numéro 1 dans ce champ = boutons radio */
        question.setObligatoire( resultSet.getInt( "question_obligatoire" ) ); /* 0 - Pas obligatoire, 1 - obligatoire */
       
        return question;
        
    }
}