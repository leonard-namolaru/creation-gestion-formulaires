package com.sdzee.formsTest;

import org.junit.Test;

import com.sdzee.dao.CategorieDao;
import com.sdzee.dao.CategorieDaoImpl;
import com.sdzee.dao.PropositionDao;
import com.sdzee.dao.PropositionDaoImpl;
import com.sdzee.dao.QuestionDao;
import com.sdzee.dao.QuestionDaoImpl;
import com.sdzee.dao.ReponseDao;
import com.sdzee.dao.ReponseDaoImpl;
import com.sdzee.forms.FormValidationException;
import com.sdzee.forms.ReponseForm;

/**
 * <b> La classe ReponseFormTest</b>:
 * <ul>
 * 	<li>Test Unitaire de la classe ReponseForm</li>
 * 	<li>Validation des champs pour répondre à une catégorie</li>
 * </ul>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 * 
 * @author Huihui Huang
 *
 */
public class ReponseFormTest {
	private ReponseDao reponseDao= new ReponseDaoImpl();
	private PropositionDao propositionDao= new PropositionDaoImpl();
	private CategorieDao categorieDao= new CategorieDaoImpl();
	private QuestionDao questionDao = new QuestionDaoImpl();
	private ReponseForm form= new ReponseForm(questionDao, categorieDao, propositionDao, reponseDao); 

	@Test
	public void testValidationTel() throws FormValidationException {
		form.validationTel("0123908080");
	}

	@Test
	public void testValidationEmail() throws FormValidationException {
		form.validationEmail("test@test.com");
	}

	@Test
	public void testValidationDate() throws FormValidationException {
		form.validationDate("2020-09-10");
	}

}
