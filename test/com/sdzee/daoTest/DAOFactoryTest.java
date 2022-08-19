/**
 * 
 */
package com.sdzee.daoTest;

import junit.framework.TestCase;

import java.sql.SQLException;

import org.junit.Test;

import com.sdzee.dao.*;

/**
 * <b> La classe DAOFactoryTest</b>:
 * <ul>
 * 	<li>Test Unitaire de la classe DAOFactory</li>
 * </ul>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 * 
 * @author Huihui Huang
 */
public class DAOFactoryTest extends TestCase {
	
	private DAOFactory daoFactory;
	
	@Test
	public void testGetInstance() throws SQLException {
		daoFactory= DAOFactory.getInstance();
	}

	@Test
	public void testGetConnection() throws SQLException {
		daoFactory= DAOFactory.getInstance();
		daoFactory.getConnection();
	}

}
