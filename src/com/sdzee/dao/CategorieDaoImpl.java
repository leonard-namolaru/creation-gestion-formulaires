package com.sdzee.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.sdzee.dao.DAOUtilitaire.fermeturesSilencieuses;
import static com.sdzee.dao.DAOUtilitaire.initialisationRequetePreparee;
import com.sdzee.beans.Categorie;

/**
 * <b>La classe CategorieDaoImpl</b>: <br>
 * <ul>
 * 	<li>Classe qui implémentate de l'interface DAO Categorie</li>
 * 	<li>Manipulation de la table l2n1_categories</li>
 * </ul>
 * 
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet et ajout d'explications à partir des tutoriels (source 1, source 2)</p>
 * 
 * <p>Projet Sujet : L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * <p>Source2 : openclassrooms.com - Apprenez à programmer en Java - Auteur : Cyrille Herby - License CC BY-NC-SA 2.0</p>
 * 
 * @author  Leonard Namolaru, Huihui Huang
 */
public class CategorieDaoImpl implements CategorieDao {
	/* *************************** ATTRIBUTS ************************* */

	private static final String SQL_SELECT_PAR_ID 	= "SELECT * FROM l2n1_categories WHERE categorie_id = ?";
	private static final String SQL_COUNT			= "SELECT count(*) FROM l2n1_categories";
	private static final String SQL_SELECT 			= "SELECT * FROM l2n1_categories order by categorie_titre asc LIMIT ? OFFSET ?";
	private static final String SQL_INSERT 			= "INSERT INTO l2n1_categories (categorie_titre, categorie_desc, categorie_nb_groupes) VALUES (?, ?, ?)";
	private static final String SQL_UPDATE 			= "UPDATE l2n1_categories set categorie_titre = ?, categorie_desc = ?, categorie_nb_groupes = ? where categorie_id = ?";
	private static final String SQL_DELETE 			= "DELETE FROM l2n1_categories WHERE categorie_id=?";

	
	private DAOFactory daoFactory;
	

	/* ************************** CONSTRUCTEURS ************************* */

	/**
	 * Constructeur: Accès à une instance de DAOFactory: pouvoir appeler la méthode
	 * getConnection() de DAOFactory <br>
	 * 
	 * @param daoFactory - DAOFactory
	 * 
	 * @see CategorieDaoImpl
	 */
	public CategorieDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	/**
	 * Constructeur par défaut
	 * 
	 * @see CategorieDaoImpl
	 */
	public CategorieDaoImpl() {
	}

	/* ************************** METHODES ************************* */

	/**
	 * Méthode qui créer une catégorie dans la BDD
	 * 
	 * @param categorie - Un bean Categorie
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see CategorieDaoImpl
	 * @see CategorieDao#creer(Categorie)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Leonard Namolaru
	 */
	public void creer(Categorie categorie) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet valeursAutoGenerees = null;

		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
			connexion = daoFactory.getConnection();
			// 2. Initialisation de la requête
			preparedStatement = initialisationRequetePreparee(connexion, SQL_INSERT, true, categorie.getTitre(), categorie.getDescription(), categorie.getNbGroupes() );
			// 3. Exécution de la requête
			// executeUpdate() : l'éxécution d'une instruction menant à la modification de la BD(INSERT, UPDATE, DELETE...)
			int statut = preparedStatement.executeUpdate();
			
			if (statut == 0) {
				throw new DAOException("Echec de la création de le catégorie, aucune ligne ajoutée dans la table.");
			}
			
			// 4. Générer automatiquement un id
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			// 5. Ecrit l'id généreé dans la colomne ID de la table categorie
			if (valeursAutoGenerees.next()) {
				categorie.setId(valeursAutoGenerees.getLong(1));
			} else {
				throw new DAOException("Echec de la création de le catégorie dans la base de donnees, aucun ID auto-généré retourné.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(valeursAutoGenerees, preparedStatement, connexion);
		}
	}
	
	
	/**
	 * Méthode abstraite qui liste tous les catégories existantes de la base de données
	 * 
	 * @return La liste des Categories existants dans la base de données
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see CategorieDaoImpl
	 * @see CategorieDao#lister(int, int)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public List<Categorie> lister(int start, int count) throws DAOException {
		Connection connexion= null;
		PreparedStatement preparedStatement= null;
		ResultSet resultSet= null;
		List<Categorie> categories= new ArrayList<Categorie>();
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
            connexion = daoFactory.getConnection();
            // 2. Initialisation de la requête
            preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT, false, count, start);
            // 3. Exécution de la requête
            // executeQuery(): exécution d'un SELECT
            resultSet = preparedStatement.executeQuery();
            // 4. Mapping du résultat dans l'objet Categorie ou renvoie null
            while ( resultSet.next() ) {
                categories.add( map( resultSet ) );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return categories;
	}
	
	
	/**
	 * Méthode qui calcule le nombre total de catégorie existante
	 * 
	 * @return Le nombre total de Categorie dans la base de données
	 * 
	 * @throws DAOException Si erreurs pendant la requête
	 * 
	 * @see CategorieDaoImpl
	 * @see CategorieDao#getTotal()
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
            // 4. Retourne le nombre total de proposition dans la base de données
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
	 * Méthode qui permet de retrouver une catégorie dans la base de données
	 * 
	 * @param id - ID d'un bean Categorie
	 * 
	 * @return Un bean Categorie
	 * 
	 * @throws DAOException Si erreurs pendant la requête SQL
	 * 
	 * @see CategorieDaoImpl
	 * @see CategorieDao#trouver(Integer)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public Categorie trouver(Integer id) throws DAOException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Categorie categorie = null;

		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
			connexion = daoFactory.getConnection();
			// 2. Initialisation de la requête
			preparedStatement = initialisationRequetePreparee(connexion, SQL_SELECT_PAR_ID, false, id);
			// 3. Exécution de la requête
			// executeQuery(): exécution d'un SELECT
			resultSet = preparedStatement.executeQuery();
			// 4. Mapping du résultat dans l'objet Categorie ou renvoie null
			if (resultSet.next()) {
				categorie = map(resultSet);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}

		return categorie;
	}
	
	
	/**
	 * Méthode qui supprimer une catégorie de la base de données
	 * 
	 * @param categorie - Un bean Categorie
	 * 
	 * @throws DAOException Si erreurs pendant la requête
	 * 
	 * @see CategorieDaoImpl
	 * @see CategorieDao#supprimer(Categorie)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang
	 */
	public void supprimer (Categorie categorie) throws DAOException {
		Connection connexion = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		
		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
			connexion = daoFactory.getConnection();
			// 2. Initialisation de la requête
			preparedStatement = initialisationRequetePreparee(connexion, SQL_DELETE, false, categorie.getId());
			// 3. Exécution de la requête
			// executeUpdate() : l'éxécution d'une instruction menant à la modification de la BD(INSERT, UPDATE, DELETE...)
			int statut= preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException("Echec de la suppression de la catégorie.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
	}
	
	
	/**
	 * Méthode qui fait la mise à jour d'un bean Categorie dans la base de données
	 * 
	 * @param categorie - Un bean Categorie
	 * 
	 * @throws DAOException Si erreurs pendant la requête
	 * 
	 * @see CategorieDaoImpl
	 * @see CategorieDao#update(Categorie)
	 * @see DAOUtilitaire#initialisationRequetePreparee(Connection, String, boolean, Object...)
	 * @see DAOUtilitaire#fermeturesSilencieuses(ResultSet, java.sql.Statement, Connection)
	 * @see DAOFactory#getInstance()
	 * @see DAOFactory#getConnection()
	 * 
	 * @author Huihui Huang, Leonard Namolaru
	 */
	public void update (Categorie categorie) throws DAOException {
		Connection connexion = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		try {
			daoFactory= DAOFactory.getInstance();
			// 1. Connection à la base de données
			connexion = daoFactory.getConnection();
			// 2. Initialisation de la requête
			preparedStatement = initialisationRequetePreparee(connexion, SQL_UPDATE, false, categorie.getTitre(), categorie.getDescription(), categorie.getNbGroupes(), categorie.getId());
			// 3. Exécution de la requête
			// executeUpdate() : l'éxécution d'une instruction menant à la modification de la BD(INSERT, UPDATE, DELETE...)
			int statut= preparedStatement.executeUpdate();
			if (statut == 0) {
				throw new DAOException("Echec de la mise à jour de la catégorie.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			fermeturesSilencieuses(resultSet, preparedStatement, connexion);
		}
	}
	
	
	/**
	 * Méthode qui permet une correspondance(mapping) entre une ligne issue de la table des utilisateurs(Resultset) et un bean Categorie
	 * 
	 * @param resultSet - Les résultats d'une requête
	 * 
	 * @return Un bean Categorie
	 * 
	 * @throws SQLException Si erreurs pendant la requête SQL
	 * 
	 * @see CateforieDaoImpl
	 */
	private static Categorie map( ResultSet resultSet ) throws SQLException {
    	/* La méthode prend en argument un ResultSet dont le curseur a déjà été correctement positionné, 
    	et place chaque champ lu dans la propriété correspondante du nouveau bean créé */
		
        Categorie categorie = new Categorie();
        
        categorie.setId( resultSet.getLong( "categorie_id" ) );
        categorie.setTitre( resultSet.getString( "categorie_titre" ) );
        categorie.setDescription( resultSet.getString( "categorie_desc" ) );
        categorie.setNbGroupes( resultSet.getInt( "categorie_nb_groupes" ) ); /* Le nombre de groupes de questions dans la catégorie */
        
        return categorie;
    }
}