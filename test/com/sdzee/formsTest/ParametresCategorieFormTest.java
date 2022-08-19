package com.sdzee.formsTest;

import org.junit.Test;

import com.sdzee.beans.Categorie;
import com.sdzee.dao.CategorieDao;
import com.sdzee.dao.CategorieDaoImpl;
import com.sdzee.dao.PropositionDao;
import com.sdzee.dao.PropositionDaoImpl;
import com.sdzee.dao.QuestionDao;
import com.sdzee.dao.QuestionDaoImpl;
import com.sdzee.forms.FormValidationException;
import com.sdzee.forms.ParametresCategorieForm;

/**
 * <b> La classe ParametresCategorieFormTest</b>:
 * <ul>
 * 	<li>Test Unitaire de la classe ParametresCategorieForm</li>
 * </ul>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 * 
 * @author Huihui Huang
 *
 */
public class ParametresCategorieFormTest {
	
	private PropositionDao propositionDao= new PropositionDaoImpl();
	private CategorieDao categorieDao= new CategorieDaoImpl();
	private QuestionDao questionDao = new QuestionDaoImpl();
	
	private Categorie categorie = new Categorie("titre", "description", 1);
	
	private ParametresCategorieForm form= new ParametresCategorieForm(categorieDao, questionDao, propositionDao);

	@Test
	public void testValidationChampTexte() throws FormValidationException {
		form.validationChampTexte("test", 1);
	}

	@Test
	public void testTraiterTitre() {
		categorieDao.creer(categorie);
		form.traiterTitre("titre", categorie);
		categorieDao.supprimer(categorie);
	}

	@Test
	public void testTraiterDescription() {
		form.traiterDescription("description", categorie);
	}

}
