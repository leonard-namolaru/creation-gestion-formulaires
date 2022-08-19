package com.sdzee.formsTest;

import org.junit.Test;

import com.sdzee.dao.UtilisateurDao;
import com.sdzee.dao.UtilisateurDaoImpl;
import com.sdzee.forms.InscriptionForm;

/**
 * <b> La classe methodesCommunTest</b>:
 * <ul>
 * 	<li>Tous les méthodes en commun de tous les classes dans com.sdzee.form</li>
 * </ul>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 * 
 * @author Huihui Huang
 *
 */
public class methodesCommunTest {
	private UtilisateurDao utilisateurDao= new UtilisateurDaoImpl();
	private InscriptionForm form= new InscriptionForm(utilisateurDao);
	
	@Test
	public void testGetErreurs() {
		form.getErreurs();
	}

	@Test
	public void testGetResultat() {
		form.getResultat();
	}

	@Test
	public void testSetErreur() {
		form.setErreur("connexion", "succès");
	}

}
