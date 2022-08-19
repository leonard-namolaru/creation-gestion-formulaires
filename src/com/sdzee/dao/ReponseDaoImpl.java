package com.sdzee.dao;

import static com.sdzee.dao.DAOUtilitaire.fermeturesSilencieuses;


import static com.sdzee.dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.sdzee.beans.Categorie;
import com.sdzee.beans.Proposition;
import com.sdzee.beans.Question;
import com.sdzee.beans.Reponse;
import com.sdzee.beans.Utilisateur;

/**
 * <b>La classe ReponseDaoImpl</b>: <br>
 * <ul>
 * 	<li>Classe qui implémentate de l'interface DAO Reponse</li>
 * 	<li>Manipulation de la table l2n1_reponses</li>
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
public class ReponseDaoImpl implements ReponseDao {
	
	/* *************************** ATTRIBUTS ************************* */
	private static final String SQL_INSERT 					= "INSERT INTO l2n1_reponses (utilisateur_email, question_id, proposition_id, reponse_valeur, categorie_id) VALUES (? , ? , ?, ?, ?)";
	
	private static final String SQL_SELECT_PROPOSITION_ID	= "SELECT * FROM l2n1_reponses WHERE proposition_id= ?";
	private static final String SQL_SELECT_QUESTION_ID		= "SELECT * FROM l2n1_reponses WHERE question_id = ?";
	private static final String SQL_SELECT_CATEGORIE 		= "SELECT DISTINCT categorie_id FROM l2n1_reponses WHERE utilisateur_email= ?";
	private static final String SQL_SELECT_ALL		 		= "SELECT * FROM l2n1_reponses WHERE utilisateur_email= ? and categorie_id= ?";
	private static final String SQL_SELECT 					= "SELECT distinct categorie_id from l2n1_reponses";
	
	private static final String SQL_LIST_SCORE 				= "SELECT utilisateur_email, SUM(proposition_score) FROM l2n1_reponses, l2n1_propositions WHERE l2n1_reponses.proposition_id= l2n1_propositions.proposition_id AND l2n1_propositions.proposition_id IN "
																+ "(SELECT proposition_id FROM l2n1_reponses WHERE categorie_id = ?) GROUP BY utilisateur_email LIMIT ? OFFSET ?";
	
	private static final String SQL_DELETE_REPONSE			= "DELETE FROM l2n1_reponses WHERE reponse_log_id= ?";
	private static final String SQL_DELETE_USER				= "DELETE FROM l2n1_reponses WHERE utilisateur_email= ?";
	
	private static final String SQL_COUNT					= "SELECT COUNT(distinct utilisateur_email) fROM l2n1_reponses WHERE categorie_id= ?";
	
	private DAOFactory daoFactory;

	/* ************************** CONSTRUCTEURS ************************* */

	/**
	 * Constructeur: Accès à une instance de DAOFactory: pouvoir appeler la méthode
	 * getConnection() de DAOFactory <br>
	 * 
	 * @param daoFactory - DAOFactory
	 * 
	 * @see ReponseDaoImpl()
	 */
	public ReponseDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	/**
	 * Constructeur par défaut
	 * 
	 * @see ReponseDaoImpl
	 */
	public ReponseDaoImpl() {
	
	}

	/* ************************** METHODES ************************* */
	
	
	/**
	 * Méthode qui vérifie si l'utilisateur a déjà répondu à une catégorie: si les réponses existes alors ils sont écrasées
	 * 
	 * @param reponses - Une liste de beans Reponse
	 * @param categorie - Un bean Categorie
	 * @param utilisateur - Un bean Utilisateur
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDaoImpl
	 * @see ReponseDao#enregistrementReponses(List, Categorie, Utilisateur)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang, Leonard Namolaru
	 */
	public void enregistrementReponses(List<Reponse> reponses, Categorie categorie, Utilisateur utilisateur) throws DAOException {
		/* Avant d'insérer les réponses de l'utilisateur dans la base de données, 
		 * il est nécessaire de vérifier si l'utilisateur a déjà répondu à cette catégorie.
		 * Dans ce cas, les nouvelles réponses doivent "écraser" les anciennes réponses.
		 * Cela se fait en essayant de trouver dans la base de données des réponses de l'utilisateur pour catégorie.
		 * Si des réponses sont trouvées, elles sont supprimées de la base de données avant d'insérer les nouvelles réponses.
		 */
		List<Reponse> anciennesReponses = trouverAll(utilisateur, categorie);
		for(Reponse reponse : anciennesReponses) {
			supprimerReponse(reponse);
		}
		
		for(Reponse reponse : reponses) {
			creer(reponse);		
		}
	}

	/**
	 * Méthode qui créer une réponse
	 *  
	 * @param reponse - Un bean Reponse
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDaoImpl
	 * @see ReponseDao#creer(Reponse)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang, Leonard Namolaru
	 */
	public void creer(Reponse reponse) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
			connexion = daoFactory.getConnection();
			// 2. Initialisation de la requête
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, reponse.getUtilisateurEmail(), reponse.getQuestionId(), reponse.getPropositionId(), reponse.getValeurReponse(), reponse.getCategorieId());
			// 3. Exécution de la requête
			// executeUpdate() : l'éxécution d'une instruction menant à la modification de la BD(INSERT, UPDATE, DELETE...)
			int statut = preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException("Echec de la création de la réponse, aucune ligne ajoutée dans la table.");
			}
			// 4. Générer automatiquement un id
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			// 5. Ecrit l'id généreé dans la colomne ID de la table l2n1_reponse
			if (valeursAutoGenerees.next()) {
				reponse.setReponseLogId(valeursAutoGenerees.getLong(1));
			} else {
				throw new DAOException("Echec de la création de la réponse dans la base de donnees, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
		}
	}
	
	
	/**
	 * Méthode qui supprime une réponse
	 * 
	 * @param reponse - Un bean Reponse
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDaoImpl
	 * @see ReponseDao#supprimerReponse(Reponse)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public void supprimerReponse(Reponse reponse) throws DAOException {
		Connection connexion = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
			connexion = daoFactory.getConnection();
			// 2. Initialisation de la requête
			preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE_REPONSE, false, reponse.getReponseLogId());
			// 3. Exécution de la requête
			// executeUpdate() : l'éxécution d'une instruction menant à la modification de la BD(INSERT, UPDATE, DELETE...)
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
	}
	
	
	/**
	 * Méthode qui affiche tous les réponse des catégorie que l'utilisateur à répondu
	 * 
	 * @param utilisateur- Un bean Utilisateur
	 * 
	 * @return La liste des beans Reponse
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDaoImpl
	 * @see ReponseDao#listerCategorie(Utilisateur)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public List<Reponse> listerCategorie(Utilisateur utilisateur) throws DAOException {
		Connection connexion= null;
		PreparedStatement preparedStatement= null;
		ResultSet resultSet= null;
		List<Reponse> reponses= new ArrayList<Reponse>();
		
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
            connexion = daoFactory.getConnection();
            // 2. Initialisation de la requête
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_CATEGORIE, false, utilisateur.getEmail());
            // 3. Exécution de la requête
            // executeQuery(): exécution d'un SELECT
            resultSet = preparedStatement.executeQuery();
            // 4. Mapping du résultat dans l'objet Reponse ou renvoie null
            while (resultSet.next()) {
            	Reponse reponse= new Reponse();
            	reponse.setCategorieId(resultSet.getLong("categorie_id"));
                reponses.add(reponse);
            }
            
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
		
		return reponses;
	}
	
	
	/**
	 * Méthode qui calcul le score d'une catégorie regroupé par utilisateur
	 * 
	 * @param categorie - Un bean Categorie
	 * @param start- Le début de la ligne dans la BDD
	 * @param count - Le nombre de ligne par page
	 * 
	 * @return La liste d'utilisateur accompagné de son score
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDaoImpl
	 * @see ReponseDao#score(Categorie, int, int)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang, Rhidine Andriamihaja
	 */
	public List<Reponse> score(Categorie categorie, int start, int count) throws DAOException {
		Connection connexion= null;
		PreparedStatement preparedStatement= null;
		ResultSet resultSet= null;
		List<Reponse> score= new ArrayList<Reponse>();
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
            connexion = daoFactory.getConnection();
            // 2. Initialisation de la requête
            preparedStatement = initialisationRequetePreparee(connexion, SQL_LIST_SCORE, false, categorie.getId(), count, start);
            // 3. Exécution de la requête
            // executeQuery(): exécution d'un SELECT
            resultSet = preparedStatement.executeQuery();
            // 4. Mapping du résultat dans l'objet Reponse ou renvoie null
            while (resultSet.next()) {
            	Reponse reponse= new Reponse();
            	reponse.setUtilisateurEmail(resultSet.getString(1));
            	reponse.setScoreTotal(resultSet.getInt(2));
            	score.add(reponse);
           	 	
            }
            
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
		
		return score;
	}
	
	
	/**
	 * Méthode qui permet de trouver la liste de réponse d'une proposition
	 * 
	 * @param proposition - Un bean Proposition
	 * 
	 * @return La liste de réponse
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDaoImpl
	 * @see ReponseDao#trouver(Proposition)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public List<Reponse> trouver(Proposition proposition) throws DAOException {
		Connection connexion= null;
		PreparedStatement preparedStatement= null;
		ResultSet resultSet= null;
		List<Reponse> reponse= new ArrayList<Reponse>();
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
            connexion = daoFactory.getConnection();
            // 2. Initialisation de la requête
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PROPOSITION_ID, false, proposition.getId());
            // 3. Exécution de la requête
            // executeQuery(): exécution d'un SELECT
            resultSet = preparedStatement.executeQuery();
            // 4. Mapping du résultat dans l'objet Reponse ou renvoie null
            while (resultSet.next()) {
                	reponse.add( map( resultSet ));
            }
            
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
		
		return reponse;
	}
	
	
	/**
	 * Méthode qui permet de trouver la liste de réponse d'une question
	 * 
	 * @param question - Un bean question
	 * 
	 * @return La liste des beans Reponse
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDaoImpl
	 * @see ReponseDao#trouver(Question)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public List<Reponse> trouver(Question question) throws DAOException {
		Connection connexion= null;
		PreparedStatement preparedStatement= null;
		ResultSet resultSet= null;
		List<Reponse> reponse= new ArrayList<Reponse>();
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
            connexion = daoFactory.getConnection();
            // 2. Initialisation de la requête
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_QUESTION_ID, false, question.getId());
            // 3. Exécution de la requête
            // executeQuery(): exécution d'un SELECT
            resultSet = preparedStatement.executeQuery();
            // 4. Mapping du résultat dans l'objet Reponse ou renvoie null
            while (resultSet.next()) {
                	reponse.add( map( resultSet ));
            }
            
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
		
		return reponse;
	}


	/**
	 * Méthode qui compte le nombre d'utilisateur qui ont répondu à une catégorie spécifique
	 * 
	 * @param categorie - Un bean Categorie
	 * 
	 * @return Le nombre total d'utilisateur
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDaoImpl
	 * @see ReponseDao#total(Categorie)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang, Rhidine Andriamihaja
	 */
	public int total(Categorie categorie) throws DAOException{
		int total= 0;
		Connection connexion= null;
		PreparedStatement preparedStatement= null;
		ResultSet resultSet= null;
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
            connexion = daoFactory.getConnection();
            // 2. Initialisation de la requête
            preparedStatement = initialisationRequetePreparee(connexion, SQL_COUNT, false, categorie.getId());
            // 3. Exécution de la requête
            // executeQuery(): exécution d'un SELECT
            resultSet = preparedStatement.executeQuery();
            // 4. Retourne le nombre total d'utilisateur dans la base de données 
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
	 * Méthode qui trouve la liste de réponse en fonction de l'utilisateur et de la catégorie
	 * 
	 * @param utilisateur - Un bean Utilisateur
	 * @param categorie - un bean Categorie
	 *  
	 * @return La liste des beans Reponse
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDaoImpl
	 * @see ReponseDao#trouverAll(Utilisateur, Categorie)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public List<Reponse> trouverAll(Utilisateur utilisateur, Categorie categorie) throws DAOException {
		Connection connexion= null;
		PreparedStatement preparedStatement= null;
		ResultSet resultSet= null;
		List<Reponse> reponses= new ArrayList<Reponse>();
		
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
            connexion = daoFactory.getConnection();
            // 2. Initialisation de la requête
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_ALL, false, utilisateur.getEmail(), categorie.getId());
            // 3. Exécution de la requête
            // executeQuery(): exécution d'un SELECT
            resultSet = preparedStatement.executeQuery();
            // 4. Mapping du résultat dans l'objet Reponse ou renvoie null
            while (resultSet.next()) {
            	reponses.add(map(resultSet));
            }
            
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }
		
		return reponses;
	}
	
	
	/**
	 * Méthode qui supprime la liste de réponse d'un utilisateur
	 * 
	 * @param utilisateur - Un bean Utilisateur
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDaoImpl
	 * @see ReponseDao#supprimerReponseUser(Utilisateur)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public void supprimerReponseUser(Utilisateur utilisateur) throws DAOException {
		Connection connexion = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
			connexion = daoFactory.getConnection();
			// 2. Initialisation de la requête
			preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE_USER, false, utilisateur.getEmail());
			// 3. Exécution de la requête
			// executeUpdate() : l'éxécution d'une instruction menant à la modification de la BD(INSERT, UPDATE, DELETE...)
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
	}
	
	
	/**
	 * Méthode qui liste tous les réponses de la base de données
	 * 
	 * @return La liste des beans Reponse
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDaoImpl
	 * @see ReponseDao#lister()
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public List<Reponse> lister() throws DAOException {
		Connection connexion= null;
		PreparedStatement preparedStatement= null;
		ResultSet resultSet= null;
		List<Reponse> reponses= new ArrayList<Reponse>();
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
            connexion = daoFactory.getConnection();
            // 2. Initialisation de la requête
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT, false);
            // 3. Exécution de la requête
            // executeQuery(): exécution d'un SELECT
            resultSet = preparedStatement.executeQuery();
            // 4. Mapping du résultat dans l'objet Utilisateurs ou renvoie null
            while (resultSet.next()) {
            	Reponse reponse= new Reponse();
                reponse.setCategorieId(resultSet.getLong(1));
                reponses.add(reponse);
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return reponses;
	}
	
	
	/**
	 * Correspondance(mapping) entre une ligne issue de la table des l2n1_reponse(Resultset) et un bean reponse
	 * 
	 * @param resultSet	- Les résultats d'une requête
	 * 
	 * @return Un bean Reponse
	 * 
	 * @throws SQLException Si erreurs pendant la requête SQL
	 * 
	 * @see ReponseDaoImpl
	 */
	private static Reponse map( ResultSet resultSet ) throws SQLException {
		
        Reponse reponse= new Reponse();
        
        reponse.setReponseLogId(resultSet.getLong("reponse_log_id"));
        reponse.setCategorieId(resultSet.getLong("categorie_id"));
        reponse.setPropositionId(resultSet.getLong("proposition_id"));
        reponse.setQuestionId(resultSet.getLong("question_id"));
        reponse.setUtilisateurEmail(resultSet.getString("utilisateur_email"));
        reponse.setValeurReponse(resultSet.getString("reponse_valeur"));
        
        return reponse;
    }
	
}
