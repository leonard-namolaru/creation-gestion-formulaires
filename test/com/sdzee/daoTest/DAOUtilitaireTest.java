package com.sdzee.daoTest;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.junit.Test;

import com.sdzee.dao.DAOUtilitaire;

import junit.framework.TestCase;

/**
 * <b> La classe DAOUtilitaireTest</b>:
 * <ul>
 * 	<li>Test Unitaire de la classe DAOUtilitaire</li>
 * </ul>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 * 
 * @author Huihui Huang
 */
public class DAOUtilitaireTest extends TestCase {
	
	private Connection connexion = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	@Test
	public void testFermeturesSilencieusesResultSetStatementConnection() {
		DAOUtilitaire.fermeturesSilencieuses(resultSet, preparedStatement, connexion);
	}

}
