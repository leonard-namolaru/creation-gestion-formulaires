package com.sdzee.dao;

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
import com.sdzee.beans.Utilisateur;
import com.sdzee.beans.Proposition;
import static com.sdzee.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.sdzee.dao.DAOUtilitaire.initialisationRequetePreparee;

/**
 * <b>La classe PropositionDaoImpl</b>: <br>
 * <ul>
 * 	<li>Classe qui implémente de l'interface DAO Proposition</li>
 * 	<li>Manipulation de la table l2n1_proposition</li>
 * </ul>
 * 
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet et ajout d'explications à partir des tutoriels (source 1, source 2)</p>
 * 
 * <p>Projet Sujet : L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * <p>Source2 : openclassrooms.com - Apprenez à programmer en Java - Auteur : Cyrille Herby - License CC BY-NC-SA 2.0</p>
 * 
 * @author  Leonard Namolaru, Huihui Huang, Rhidine Andriamihaja
 */
public class PropositionDaoImpl implements PropositionDao {
	
	/* ************************** ATTRIBUTS ************************* */
	
	private static final String SQL_SELECT_PAR_ID 			= "SELECT * FROM l2n1_propositions WHERE question_id IN (SELECT question_id FROM l2n1_questions WHERE categorie_id = ?)";
	private static final String SQL_SELECT_PAR_QUESTION_ID 	= "SELECT * FROM l2n1_propositions WHERE question_id = ?";
	private static final String SQL_SELECT 					= "SELECT * FROM l2n1_propositions WHERE proposition_id = ?";
	
	private static final String SQL_INSERT 					= "INSERT INTO l2n1_propositions (question_id, proposition_label, proposition_score) VALUES (?, ?, ?)";
	
	private static final String SQL_UPDATE					= "UPDATE l2n1_propositions SET question_id = ?, proposition_label= ?, proposition_score= ? WHERE proposition_id = ?";
	
	private static final String SQL_COUNT_SCORE				= "SELECT SUM(proposition_score) from l2n1_propositions WHERE proposition_id IN (SELECT proposition_id FROM l2n1_reponses WHERE categorie_id = ? and utilisateur_email= ?)";
	
	private static final String SQL_SCORE_UNIQUE 			= "SELECT SUM(proposition_score) from (SELECT MAX(proposition_score) AS proposition_score from l2n1_propositions where question_id= ?) as proposition";
	
	private static final String SQL_SCORE_MULTIPLE			= "SELECT SUM(proposition_score) from l2n1_propositions where question_id= ?";
	
	private static final String SQL_DELETE_PROPOSITION 		= "DELETE FROM l2n1_propositions WHERE proposition_id= ?";
	
	private static final String SQL_POURCENTAGE 			= "SELECT l2n1_propositions.question_id, proposition_label, ROUND((COUNT(distinct utilisateur_email))*100/ NULLIF((SELECT COUNT(distinct utilisateur_email) "
																+ "FROM l2n1_reponses WHERE categorie_id= ?), 0), 2) as pourcentage from l2n1_reponses RIGHT JOIN (SELECT * FROM l2n1_propositions WHERE question_id= ?) "
																+ "AS l2n1_propositions ON l2n1_reponses.proposition_id= l2n1_propositions.proposition_id GROUP BY l2n1_propositions.question_id, proposition_label order by pourcentage desc";

	private DAOFactory daoFactory;

	/* ************************** CONSTRUCTEURS ************************* */

	/**
	 * Constructeur: Accès à une instance de DAOFactory: pouvoir appeler la méthode
	 * getConnection() de DAOFactory <br>
	 * 
	 * @param daoFactory - DAOFactory
	 * 
	 * @see PropositionDaoImpl()
	 */
	public PropositionDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	/**
	 * Constructeur par défaut
	 * 
	 * @see PropositionDaoImpl
	 */
	public PropositionDaoImpl() {
		
	}

	/* ************************** METHODES ************************* */
	
	
	/** 
	 * Méthode qui créer une liste de proposition en fonction de la catégorie dans la base de données
	 * 
	 * @param categorie - Un bean Categorie
	 * 
	 * @throws DAOException Si erreurs lors de la requête SQL
	 * 
	 * @see PropositionDaoImpl
	 * @see PropositionDao#creer(Categorie)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author  Leonard Namolaru
	 */
	public void creer(Categorie categorie) throws DAOException {
		Vector<Question> questions = categorie.getQuestions();
		
		for(Question question : questions) {
			Long questionId = question.getId();
			List<Proposition> propositions = question.getPropositions();
			
			for(Proposition proposition : propositions) {				
				proposition.setQuestionId( questionId );
				creer(proposition);		
			}
		}
	}
		
	
	/**
	 * Méthode qui créer une proposition dans la Base de données
	 * 
	 * @param proposition - Un bean Proposition
	 * 
	 * @throws DAOException Si erreurs lors de la requête SQL
	 * 
	 * @see PropositionDaoImpl
	 * @see PropositionDao#creer(Proposition)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author  Leonard Namolaru
	 */
	public void creer(Proposition proposition) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
			connexion = daoFactory.getConnection();
			// 2. Initialisation de la requête
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, proposition.getQuestionId(), proposition.getLabel(), proposition.getScore() );
			// 3. Exécution de la requête
         	// executeUpdate() : l'éxécution d'une instruction menant à la modification de la BD(INSERT, UPDATE, DELETE...)
			int statut = preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException("Echec de la création de la proposition, aucune ligne ajoutée dans la table.");
			}
			// 4. Générer automatiquement un id
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			// 5. Ecrit l'id généreé dans la colomne ID de la table l2n1_proposition
			if (valeursAutoGenerees.next()) {
				proposition.setId(valeursAutoGenerees.getLong(1));
			} else {
				throw new DAOException("Echec de la crétion de la proposition dans la base de donnees, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
		}
	}
	
	
	/**
	 * Méthode pour trouver la liste de proposition d'une catégorie
	 * 
	 * @param id - ID de la catégorie 
	 * 
	 * @return Liste des propositions
	 * 
	 * @throws DAOException Si erreurs pendant la requêtes SQL
	 * 
	 * @see PropositionDaoImpl
	 * @see PropositionDao#lister(Integer)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	 public List<Proposition> lister (Integer id) throws DAOException {
		 Connection connexion= null;
		 PreparedStatement preparedStatement= null;
		 ResultSet resultSet= null;
		 List<Proposition> propositions= new ArrayList<Proposition>();
			
		 try {
			 daoFactory= DAOFactory.getInstance();
			 // 1. Connection à la base de données
			 connexion = daoFactory.getConnection();
			 // 2. Initialisation de la requête
			 preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_ID, false, id);
			 // 3. Exécution de la requête
			 // executeQuery(): exécution d'un SELECT
			 resultSet = preparedStatement.executeQuery();
			 // 4. Mapping du résultat dans l'objet Proposition ou renvoie null
			 while (resultSet.next()) {
				 propositions.add( map( resultSet ));
			 }
		 } catch ( SQLException e ) {
	            throw new DAOException( e );
	     } finally {
	    	 fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	     }

		 return propositions;
	 }
	

	 /**
	  * Méthode pour trouver les propostions d'une question
	  * 
	  * @param question - Un bean Question
	  * 
	  * @return La liste des propositions
	  * 
	  * @throws DAOException Si erreurs pendant la requête SQL
	  * 
	  * @see PropositionDaoImpl
	  * @see PropositionDao#listerQuestionId(Question)
	  * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	  * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	  * @see DAOFactory#getInstance()
	  * @see DAOFactory#getConnection()
	  * 
	  * @author  Leonard Namolaru
	  */
	 public List<Proposition> listerQuestionId(Question question) {
		 Connection connexion= null;
		 PreparedStatement preparedStatement= null;
		 ResultSet resultSet= null;
		 List<Proposition> propositions= new ArrayList<Proposition>();
			
		 try {
			 daoFactory= DAOFactory.getInstance();
			 // 1. Connection à la base de données
			 connexion = daoFactory.getConnection();
			 // 2. Initialisation de la requête
			 preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_QUESTION_ID, false, question.getId());
			 // 3. Exécution de la requête
			 // executeQuery(): exécution d'un SELECT
			 resultSet = preparedStatement.executeQuery();
			 // 4. Mapping du résultat dans l'objet Proposition ou renvoie null
			 while (resultSet.next()) {
				 propositions.add( map( resultSet ));
			 }
		 } catch ( SQLException e ) {
	            throw new DAOException( e );
	     } finally {
	    	 fermeturesSilencieuses( resultSet, preparedStatement, connexion );
	     }

		 return propositions;
	}
	 
	 
	 /**
	  * Méthode qui retrouve une proposition par son id
	  * 
	  * @param id- ID Proposition
	  * 
	  * @return La proposition
	  * 
	  * @throws DAOException Si erreurs pendant la requête SQL
	  * 
	  * @see PropositionDaoImpl
	  * @see PropositionDao#trouver(Integer)
	  * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	  * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	  * @see DAOFactory#getInstance()
	  * @see DAOFactory#getConnection()
	  * 
	  * @author Huihui Huang
	  */
	 public Proposition trouver(Integer id) throws DAOException {
		 Connection connexion = null;
		 PreparedStatement preparedStatement = null;
		 ResultSet resultSet = null;
		 Proposition proposition= null;

		 try {
			 daoFactory= DAOFactory.getInstance();
			 // 1. Connection à la base de données
			 connexion = daoFactory.getConnection();
			 // 2. Initialisation de la requête
			 preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT, false, id);
			 // 3. Exécution de la requête
			 // executeQuery(): exécution d'un SELECT
			 resultSet = preparedStatement.executeQuery();
			 // 4. Mapping du résultat dans l'objet Proposition ou renvoie null
			 if (resultSet.next()) {
				 proposition = map(resultSet);
			 }
		 } catch (SQLException e) {
			 throw new DAOException(e);
		 } finally {
			 fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		 }

		 return proposition;
	}
	
	
	/**
	 * Méthode qui supprime une proposition d'une catégorie
	 * 
	 * @param proposition - Un bean Proposition
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see PropositionDaoImpl
	 * @see PropositionDao#supprimer(Proposition)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public void supprimer (Proposition proposition) throws DAOException {
		Connection connexion = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
			connexion = daoFactory.getConnection();
			// 2. Initialisation de la requête
			preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE_PROPOSITION, false, proposition.getId());
			// 3. Exécution de la requête
			// executeUpdate() : l'éxécution d'une instruction menant à la modification de la BD(INSERT, UPDATE, DELETE...)
			int statut= preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException("Echec de la suppresion de la propositions.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
	}
	
	
	/**
	 * Méthode qui fait la mise à jour de tous les propositions d'une catégorie
	 * 
	 * @param categorie - Une categorie
	 * @param request - Une requête HTTP qui contient la demande du client
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see PropositionDaoImpl
	 * @see PropositionDao#update(HttpServletRequest, Categorie)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection() 
	 * 
	 * @author  Leonard Namolaru, Huihui Huang
	 */
	public void update (HttpServletRequest request, Categorie categorie) throws DAOException{
		Connection connexion = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		int counterQuestion = 1;
		
		for(Question question : categorie.getQuestions() )
		{
			 
			int counterProposition = 1;

			for (Proposition proposition: question.getPropositions() ) {
				 String propositionId = getValeurChamp( request, "question" + counterQuestion + "proposition" + counterProposition + "_id" );
				 boolean isPropositionNew = (propositionId != null) ? false : true;
				 
				 proposition.setQuestionId( question.getId() );

				 if( isPropositionNew )
				 {
					 creer( proposition );
				 }
				 else
				 {
					 	proposition.setId( (long) Integer.parseInt(propositionId) );
						try {
							daoFactory= DAOFactory.getInstance();
							// 1. Connection à la base de données
							connexion = daoFactory.getConnection();
							// 2. Initialisation de la requête
							preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, false ,proposition.getQuestionId(), proposition.getLabel(), proposition.getScore(), proposition.getId());
							// 3. Exécution de la requête
							// executeUpdate() : l'éxécution d'une instruction menant à la modification de la BD(INSERT, UPDATE, DELETE...)
							int statut = preparedStatement.executeUpdate();
							if (statut == 0) {
								throw new DAOException("Echec de la la MAJ de la proposition.");
							}

						} catch (SQLException e) {
							throw new DAOException(e);
						} finally {
							fermeturesSilencieuses(resultSet, preparedStatement, connexion);
						}
						
				 }
				 
					counterProposition++;
			}
			
			 counterQuestion++;
		}		
	}


	/**
	 * Méthode qui calcul le score d'un utilisateur qui a répondu à une catégorie
	 * 
	 * @param categorie - Un bean Categorie
	 * @param utilisateur - Un bean Utilisateur
	 * 
	 * @return Le total du score obtenu
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see PropositionDaoImpl
	 * @see PropositionDao#scoreObtenu(Categorie, Utilisateur)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public int scoreObtenu (Categorie categorie, Utilisateur utilisateur) throws DAOException{
		int score= 0;
		Connection connexion= null;
		PreparedStatement preparedStatement= null;
		ResultSet resultSet= null;
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
            connexion = daoFactory.getConnection();
            // 2. Initialisation de la requête
            preparedStatement = initialisationRequetePreparee(connexion, SQL_COUNT_SCORE, false, categorie.getId(), utilisateur.getEmail());
            // 3. Exécution de la requête
            // executeQuery(): exécution d'un SELECT
            resultSet = preparedStatement.executeQuery();
            // 4. Retourne le score obtenu d'un utilisateur dans la base de données 
            while ( resultSet.next() ) {
               score= resultSet.getInt(1);
            }
            
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
		
		return score;
		
	}
	
	/**
	 * Méthode qui calcule le score d'une question à choix unique
	 * 
	 * @param question - Un bean Question
	 * 
	 * @return Le score max d'une question
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see PropositionDaoImpl
	 * @see PropositionDao#scoreTotalQUnique(Question)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public int scoreTotalQUnique(Question question) throws DAOException {
		int total= 0;
		Connection connexion= null;
		PreparedStatement preparedStatement= null;
		ResultSet resultSet= null;
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
            connexion = daoFactory.getConnection();
            // 2. Initialisation de la requête
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SCORE_UNIQUE, false, question.getId());
            // 3. Exécution de la requête
            // executeQuery(): exécution d'un SELECT
            resultSet = preparedStatement.executeQuery();
            // 4. Retourne le score total d'une catégorie dans la base de données 
            while ( resultSet.next() ) {
               total= resultSet.getInt(1);
            }
            
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
		
		return total;
	}
	
	/**
	 * Méthode qui calcule le score d'une question à choix multiple
	 * 
	 * @param question - Un bean Question
	 * 
	 * @return Le score max d'une question
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see PropositionDaoImpl
	 * @see PropositionDao#scoreTotalQMultiple(Question)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public int scoreTotalQMultiple(Question question) throws DAOException {
		int total= 0;
		Connection connexion= null;
		PreparedStatement preparedStatement= null;
		ResultSet resultSet= null;
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
            connexion = daoFactory.getConnection();
            // 2. Initialisation de la requête
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SCORE_MULTIPLE, false, question.getId());
            // 3. Exécution de la requête
            // executeQuery(): exécution d'un SELECT
            resultSet = preparedStatement.executeQuery();
            // 4. Retourne le score total d'une catégorie dans la base de données 
            while ( resultSet.next() ) {
               total= resultSet.getInt(1);
            }
            
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
		
		return total;
	}
	
	
	/**
	 * Méthode qui calcule le pourcentage d'utilisateurs qui ont répondus à les propositions d'une catégorie et de question
	 * 
	 * @param categorie - Un bean Categorie
	 * @param question - Un bean Question
	 * 
	 * @return La liste des beans Proposition
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see PropositionDaoImpl
	 * @see PropositionDao#pourcentageGroupByQuestion(Categorie, Question)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public List<Proposition> pourcentageGroupByQuestion(Categorie categorie, Question question) throws DAOException {
		Connection connexion= null;
		PreparedStatement preparedStatement= null;
		ResultSet resultSet= null;
		List<Proposition> groupe= new ArrayList<Proposition> ();	
		
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
            connexion = daoFactory.getConnection();
            // 2. Initialisation de la requête
            preparedStatement = initialisationRequetePreparee(connexion, SQL_POURCENTAGE, false, categorie.getId(), question.getId());
            // 3. Exécution de la requête
            // executeQuery(): exécution d'un SELECT
            resultSet = preparedStatement.executeQuery();
            // 4. Retourne le score obtenu d'un utilisateur dans la base de données 
            while ( resultSet.next() ) {
            	Proposition proposition= new Proposition();
            	proposition.setQuestionId(resultSet.getLong(1));
            	proposition.setLabel(resultSet.getString(2));
            	proposition.setPourcentage(resultSet.getDouble(3));
            	groupe.add(proposition);
            	
            }
                
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
		
		return groupe;
	}
	
	
	/**
	 * Méthode utilitaire qui retourne null si un champ est vide, et son contenu sinon
	 * 
	 * @param request - Une requête HTTP qui contient la demande du client
	 * @param nomChamp - Le champ spécifié
	 * 
	 * @see PropositionDaoImpl
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
	 * Méthode qui permet la correspondance(mapping) entre une ligne issue de la table des propositionsResultset) et un bean Proposition
	 * 
	 * @param resultSet	- Les résultats d'une requête
	 * 
	 * @return Un bean Proposition
	 * 
	 * @throws SQLException Si erreurs pendant la requête SQL
	 * 
	 * @see PropositionDaoImpl
	 */
	private static Proposition map( ResultSet resultSet ) throws SQLException {
    	
        Proposition proposition = new Proposition();
        
        proposition.setId( resultSet.getLong( "proposition_id" ) );
        proposition.setQuestionId( resultSet.getLong( "question_id" ) );
        proposition.setLabel( resultSet.getString( "proposition_label" ) );
        proposition.setScore( resultSet.getInt( "proposition_score" ) ); 
        return proposition;
	
	}

	
}