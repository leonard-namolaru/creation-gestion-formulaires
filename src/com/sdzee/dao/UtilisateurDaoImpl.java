package com.sdzee.dao;


import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sdzee.beans.Utilisateur;
import static com.sdzee.dao.DAOUtilitaire.*;

/**
 * <b>La classe UtilisateurDaoImpl</b>: <br>
 * <ul>
 * 	<li>Classe qui implémentate de l'interface DAO Utilisateur</li>
 * 	<li>Manipulation de la table Utilisateur</li>
 * </ul>
 * 	
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet et ajout d'explications à partir des tutoriels (source 1, source 2)</p>
 * 
 * <p>Projet Sujet : L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * 
 * @author Leonard Namolaru, Huihui Huang
 */
public class UtilisateurDaoImpl implements UtilisateurDao {

	/* *************************** ATTRIBUTS ************************* */

	private static final String SQL_SELECT_PAR_EMAIL 	= "SELECT * FROM Utilisateur WHERE email = ?";
	private static final String SQL_SELECT				= "SELECT * FROM Utilisateur where utilisateur_type=0 order by nom asc LIMIT ? OFFSET ? ";
	private static final String SQL_SELECT_ALL 			= "SELECT * FROM Utilisateur";

	private static final String SQL_COUNT 				= "SELECT count(*) from utilisateur where utilisateur_type=0";
	
	private static final String SQL_INSERT 				= "INSERT INTO Utilisateur (email, mot_de_passe, nom, utilisateur_prenom, utilisateur_date_naissance, utilisateur_sexe, date_inscription) VALUES (?, ?, ?, ?, ?, ?, NOW())";
	
	private static final String SQL_UPDATE 				= "UPDATE utilisateur set mot_de_passe= ?, nom= ?, utilisateur_prenom= ?, utilisateur_date_naissance= ?, utilisateur_sexe= ? where email= ?";
	private static final String SQL_UPDATE_ADMIN		= "UPDATE utilisateur set nom= ?, utilisateur_prenom= ?, utilisateur_date_naissance= ?, utilisateur_sexe= ? where email= ?";
	private static final String SQL_UPDATE_PASSWORD		= "UPDATE utilisateur set mot_de_passe= ? where email= ?";
	
	private static final String SQL_DELETE 				= "DELETE FROM utilisateur WHERE email=?";

	private DAOFactory daoFactory;

	/* ************************** CONSTRUCTEURS ************************* */

	/**
	 * Constructeur: Accès à une instance de DAOFactory: pouvoir appeler la méthode
	 * getConnection() de DAOFactory <br>
	 * 
	 * @param daoFactory - DAOFactory
	 * 
	 * @see UtilisateurDaoImpl()
	 */
	public UtilisateurDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
	/**
	 * Constructeur par défaut
	 * 
	 * @see UtilisateurDaoImpl
	 */
	public UtilisateurDaoImpl() {
	
	}

	/* ************************** METHODES ***************************** */

	
	/**
	 * Méthode qui créer utilisateur lors de son inscription
	 * 
	 * @param utilisateur - Un bean Utilisateur
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see UtilisateurDaoImpl
	 * @see UtilisateurDao#creer(Utilisateur)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Leonard Namolaru
	 */
	public void creer(Utilisateur utilisateur) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
			connexion = daoFactory.getConnection();
			// 2. Initialisation de la requête  
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, utilisateur.getEmail(), utilisateur.getPassword(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getDateNaissance(), utilisateur.getSexe());
			// 3. Exécution de la requête
			// executeUpdate() : l'éxécution d'une instruction menant à la modification de la BD(INSERT, UPDATE, DELETE...)
			int statut = preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException("Echec de la création de l'utilisateur, aucune ligne ajoutée dans la table.");
			}
			// 4. Générer automatiquement un id 
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			// 5. Ecrit l'id généreé dans la colomne ID de la table utilisateur
			if (valeursAutoGenerees.next()) {
				utilisateur.setId(valeursAutoGenerees.getLong(1));
			} else {
				throw new DAOException("Echec de la crétion de l'utilisateur en base, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
		}
	}
	
	
	/**
	 * Méthode qui permet de trouver un utilisateur (la recherche d'un utilisateur, lors de la connexion)
	 * 
	 * @param email	- L'Email d'un bean Utilisateur
	 * 
	 * @return L'utilisateur trouvé
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see UtilisateurDaoImpl
	 * @see UtilisateurDao#trouver(String)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public Utilisateur trouver(String email) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Utilisateur utilisateur = null;

		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
			connexion = daoFactory.getConnection();
			// 2. Initialisation de la requête
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_EMAIL, false, email);
			// 3. Exécution de la requête
			// executeQuery(): exécution d'un SELECT
			resultSet = preparedStatement.executeQuery();
			// 4. Mapping du résultat dans l'objet Utilisateur ou renvoie null
			if (resultSet.next()) {
				utilisateur = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return utilisateur;
	}


	/**
	 * Méthode qui permet la  mise à jour des données personnels de l'utilisateur
	 * 
	 * @param utilisateur - Un bean Utilisateur
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see UtilisateurDaoImpl
	 * @see UtilisateurDao#miseAJour(Utilisateur)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public void miseAJour(Utilisateur utilisateur) throws DAOException {
		Connection connexion = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
			connexion = daoFactory.getConnection();
			// 2. Initialisation de la requête
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, false, utilisateur.getPassword(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getDateNaissance(), utilisateur.getSexe(), utilisateur.getEmail());
			// 3. Exécution de la requête
			// executeUpdate() : l'éxécution d'une instruction menant à la modification de la BD(INSERT, UPDATE, DELETE...)
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
	}
	
	
	/**
	 * Méthode qui permet la mise à jour des données personnels d'un utilisateurs grâce à l'administrateur
	 * 
	 * @param utilisateur - Un bean Utilisateur
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see UtilisateurDaoImpl
	 * @see UtilisateurDao#miseAJourAdmin(Utilisateur)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public void miseAJourAdmin(Utilisateur utilisateur) throws DAOException {
		Connection connexion = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
			connexion = daoFactory.getConnection();
			// 2. Initialisation de la requête
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_ADMIN, false, utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getDateNaissance(), utilisateur.getSexe(), utilisateur.getEmail());
			// 3. Exécution de la requête
			// executeUpdate() : l'éxécution d'une instruction menant à la modification de la BD(INSERT, UPDATE, DELETE...)
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
	}
	
	/**
	 * Méthode qui permet la modification du mot de passe de l'utilisateur
	 * 
	 * @param utilisateur - Un bean Utilisateur
	 * 
	 * @throws DAOException	Si erreurs pendant la requête SQL
	 * 
	 * @see UtilisateurDaoImpl
	 * @see UtilisateurDao#miseAJourPassword(Utilisateur)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public void miseAJourPassword(Utilisateur utilisateur) throws DAOException {
		Connection connexion = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
			connexion = daoFactory.getConnection();
			// 2. Initialisation de la requête
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE_PASSWORD, false, utilisateur.getPassword(), utilisateur.getEmail());
			// 3. Exécution de la requête
			// executeUpdate() : l'éxécution d'une instruction menant à la modification de la BD(INSERT, UPDATE, DELETE...)
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
	}
	

	/**
	 * Méthode qui permet de supprimer un utilisateur
	 * 
	 * @param email - L'Email d'un bean Utilisateur
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see UtilisateurDaoImpl
	 * @see UtilisateurDao#supprimer(String)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public void supprimer(String email) throws DAOException {
		Connection connexion = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
			connexion = daoFactory.getConnection();
			// 2. Initialisation de la requête
			preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE, false, email);
			// 3. Exécution de la requête
			// executeUpdate() : l'éxécution d'une instruction menant à la modification de la BD(INSERT, UPDATE, DELETE...)
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
		
	}
	
	
	/**
	 * Méthode qui permet de calculer le nombre d'utilisateur inscrit
	 * 
	 * @return Le nombre total d'utilisateur inscrit
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see UtilisateurDaoImpl
	 * @see UtilisateurDao#getTotal()
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public int getTotal() throws DAOException {
		int total= 0;
		Connection connexion= null;
		PreparedStatement preparedStatement= null;
		ResultSet resultSet= null;
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
            connexion = daoFactory.getConnection();
            // 2. Initialisation de la requête
            preparedStatement = initialisationRequetePreparee(connexion, SQL_COUNT, false);
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
	 * Méthode qui liste tous les utilisateurs inscrits
	 * 
	 * @param start- Le début de la ligne dans la BDD
	 * @param count - Le nombre de ligne par page
	 * 
	 * @return La liste des utilisateurs inscrits 
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see UtilisateurDaoImpl
	 * @see UtilisateurDao#lister(int, int)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public List<Utilisateur> lister(int start, int count) throws DAOException {
		Connection connexion= null;
		PreparedStatement preparedStatement= null;
		ResultSet resultSet= null;
		List<Utilisateur> utilisateurs= new ArrayList<Utilisateur>();
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
            connexion = daoFactory.getConnection();
            // 2. Initialisation de la requête
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT, false, count, start);
            // 3. Exécution de la requête
            // executeQuery(): exécution d'un SELECT
            resultSet = preparedStatement.executeQuery();
            // 4. Mapping du résultat dans l'objet Utilisateurs ou renvoie null
            while (resultSet.next()) {
                if (resultSet.getInt("utilisateur_type") == 0) {
                	utilisateurs.add( map( resultSet ));
                }
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return utilisateurs;
	}
	
	
	/**
	 * Méthode qui liste tous les utilisateurs inscrits sans limite
	 * 
	 * @return La liste des utilisateurs inscrits 
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see UtilisateurDaoImpl
	 * @see UtilisateurDao#lister(int, int)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public List<Utilisateur> lister() throws DAOException{
		Connection connexion= null;
		PreparedStatement preparedStatement= null;
		ResultSet resultSet= null;
		List<Utilisateur> utilisateurs= new ArrayList<Utilisateur>();
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
            connexion = daoFactory.getConnection();
            // 2. Initialisation de la requête
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_ALL, false);
            // 3. Exécution de la requête
            // executeQuery(): exécution d'un SELECT
            resultSet = preparedStatement.executeQuery();
            // 4. Mapping du résultat dans l'objet Utilisateurs ou renvoie null
            while (resultSet.next()) {
                if (resultSet.getInt("utilisateur_type") == 0) {
                	utilisateurs.add( map( resultSet ));
                }
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return utilisateurs;
	}
		
	
	
	/**
	 * Correspondance(mapping) entre une ligne issue de la table des utilisateurs(Resultset) et un bean Utilisateur
	 * 
	 * @param resultSet	- Les résultats d'une requête
	 * 
	 * @return Un bean Utilisateur
	 * 
	 * @throws SQLException Si erreurs pendant la requête SQL
	 * 
	 * @see UtilisateurDaoImpl
	 */
	private static Utilisateur map( ResultSet resultSet ) throws SQLException {
		
        Utilisateur utilisateur = new Utilisateur();
        
        utilisateur.setId( resultSet.getLong( "id" ) );
        utilisateur.setSexe( resultSet.getInt( "utilisateur_sexe" ) );
        utilisateur.setNom( resultSet.getString( "nom" ) );
        utilisateur.setPrenom( resultSet.getString( "utilisateur_prenom" ) );
        utilisateur.setEmail( resultSet.getString( "email" ) );
        utilisateur.setPassword( resultSet.getString( "mot_de_passe" ) );
        utilisateur.setDateInscription( resultSet.getTimestamp( "date_inscription" ) );
        utilisateur.setDateNaissance(resultSet.getDate("utilisateur_date_naissance").toLocalDate());
        utilisateur.setType(resultSet.getInt( "utilisateur_type" ));
        
        return utilisateur;
    }
}