package com.sdzee.formsTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import com.sdzee.beans.Utilisateur;
import com.sdzee.dao.UtilisateurDao;
import com.sdzee.dao.UtilisateurDaoImpl;
import com.sdzee.forms.FormValidationException;
import com.sdzee.forms.InscriptionForm;


/**
 * <b> La classe InscriptionFormTest</b>:
 * <ul>
 * 	<li>Test Unitaire de la classe InscriptionForm</li>
 * 	<li>Validation des champs Inscription</li>
 * </ul>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 * 
 * @author Huihui Huang
 *
 */
public class InscriptionFormTest {
	
	private UtilisateurDao utilisateurDao= new UtilisateurDaoImpl();
	private InscriptionForm form= new InscriptionForm(utilisateurDao);
	
	private LocalDate lt = LocalDate.now();
	private Utilisateur utilisateur= new Utilisateur();

	@Test
	public void testValidationEmail() throws FormValidationException {
		form.validationEmail("junit@test.com");
	}

	@Test
	public void testValidationDate() throws FormValidationException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String date= lt.format(formatter);
		form.validationDate(date);
	}
	
	@Test
	public void validationMotsDePasse() throws FormValidationException {
		form.validationMotsDePasse("test", "test");
	}
	
	@Test
	public void validationNomPrenom() throws FormValidationException {
		form.validationNomPrenom("test");
	}

	@Test
	public void testTraiterEmail() {
		form.traiterEmail("Dupont@test.com", utilisateur);
	}

	@Test
	public void testTraiterDate() {
		form.traiterDate("09-04-2020", utilisateur);
	}

	@Test
	public void testTraiterNom() {
		form.traiterNom("Dupont", utilisateur);
	}

	@Test
	public void testTraiterPrenom() {
		form.traiterDate("Marie", utilisateur);
	}

	@Test
	public void testTraiterSexe() {
		form.traiterSexe("Femme", utilisateur);
	}

	@Test
	public void testTraiterMotsDePasse() {
		form.traiterMotsDePasse("Test", "Test", utilisateur);
	}

}
