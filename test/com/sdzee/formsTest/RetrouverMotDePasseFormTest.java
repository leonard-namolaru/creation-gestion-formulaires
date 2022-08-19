package com.sdzee.formsTest;

import javax.mail.Session;

import org.junit.Test;

import com.sdzee.dao.UtilisateurDao;
import com.sdzee.forms.RetrouverMotDePasseForm;

import junit.framework.TestCase;


/**
 * <b> La classe RetrouverMotDePasseFormTest</b>:
 * <ul>
 * 	<li>Test Unitaire de la classe RetrouverMotDePasseForm</li>
 * 	<li>Validation des champs pour retrouver le mot de passe de l'utilisateur</li>
 * </ul>
 * 
 * <p> Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes </p>
 * 
 * @author Huihui Huang
 *
 */
public class RetrouverMotDePasseFormTest extends TestCase {
	private UtilisateurDao utilisateurDao;
	private RetrouverMotDePasseForm password= new RetrouverMotDePasseForm(utilisateurDao);
	
	@Test
	public void testSendEmail() throws Exception {
		password.sendEmail("hhh190400@gmail.com", "bonjour");
	}
	
	@Test
	public void testSetContenu() {
		password.setContenu("hhh190400@gmail.com", "lien", "l2n1descartes@gmail.com");
	}
	
	@Test 
	public void testcreateMimeMessage() throws Exception {
		Session session = null;
		RetrouverMotDePasseForm.createMimeMessage(session, "l2n1descartes@gmail.co", "hhh190400@gmail.com", "contenu");
	}

}
