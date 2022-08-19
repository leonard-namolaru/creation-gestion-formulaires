package com.sdzee.daoTest;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;


import com.sdzee.beans.Utilisateur;
import com.sdzee.dao.UtilisateurDao;
import com.sdzee.dao.UtilisateurDaoImpl;


/**
 * <b> La classe UtilisateurDaoImplTest</b>:
 * <ul>
 * 	<li>Test de la classe UtilisateurDaoImpl</li>
 * </ul>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 * 
 * @author Huihui Huang
 */
@FixMethodOrder(MethodSorters.DEFAULT) // Ordre d'éxécution des méthodes en fonction de l'alphabet
public class UtilisateurDaoImplTest {

	private UtilisateurDao dao= new UtilisateurDaoImpl();
	
	private LocalDate lt = LocalDate.now();
	
	private  Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    
	private  Utilisateur utilisateur= new Utilisateur("testJunit@test.com", 1, "test", "Test", "Junit", lt, timestamp, 0);
	
	@Test
	public void testACreer() {
		dao.creer(utilisateur);
	}

	@Test
	public void testBTrouver() {
		utilisateur= dao.trouver(utilisateur.getEmail());

	}
	
	@Test
	public void testCLister() {
		dao.lister(0, 1);

	}
	
	@Test
	public void testDMiseAJour() {
		dao.miseAJour(utilisateur);
	}
	
	@Test
	public void testEMiseAJourAdmin() {
		dao.miseAJourAdmin(utilisateur);
	}

	@Test
	public void testFMiseAJourPassword() {
		dao.miseAJourPassword(utilisateur);
	}
	
	@Test
	public void testGGetTotal() {
		dao.getTotal();
	}
	
	@Test
	public void testHSupprimer() {
		dao.supprimer(utilisateur.getEmail());
	}
	
}
