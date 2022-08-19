package com.sdzee.formsTest;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.junit.Test;

import com.sdzee.beans.Utilisateur;
import com.sdzee.dao.UtilisateurDao;
import com.sdzee.forms.ConnexionForm;


/**
 * <b> La classe ConnexionFormTest</b>:
 * <ul>
 * 	<li>Test Unitaire de la classe ConnexionForm</li>
 * 	<li>Validation des champs Connexion</li>
 * </ul>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 * 
 * @author Huihui Huang
 *
 */
public class ConnexionFormTest {
	
	private UtilisateurDao utilisateurDao;
	private ConnexionForm form= new ConnexionForm(utilisateurDao);
	
	private LocalDate lt = LocalDate.now();
	private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	private Utilisateur utilisateur= new Utilisateur("testJunit@test.com", 1, "test", "Test", "Junit", lt, timestamp, 0);
	
	@Test
	public void testTraiterEmailUtilisateur() {
		form.traiterEmailUtilisateur(utilisateur.getEmail(), utilisateur);
	}

	@Test
	public void testTraiterMotDePasseUtilisateur() {
		form.traiterMotDePasseUtilisateur(utilisateur.getPassword(), utilisateur);
	}

}
