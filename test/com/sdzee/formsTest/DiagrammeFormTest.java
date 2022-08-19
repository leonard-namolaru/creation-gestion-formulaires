package com.sdzee.formsTest;

import org.jfree.data.category.CategoryDataset;

import org.junit.Test;

import com.sdzee.beans.Categorie;
import com.sdzee.beans.Question;
import com.sdzee.dao.CategorieDao;
import com.sdzee.dao.CategorieDaoImpl;
import com.sdzee.dao.PropositionDao;
import com.sdzee.dao.PropositionDaoImpl;
import com.sdzee.dao.QuestionDao;
import com.sdzee.dao.QuestionDaoImpl;
import com.sdzee.forms.DiagrammeForm;


/**
 * <b> La classe DiagrammeFormTest</b>:
 * <ul>
 * 	<li>Test Unitaire de la classe DiagrammeForm</li>
 * </ul>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 * 
 * @author Huihui Huang
 *
 */
public class DiagrammeFormTest {
	private PropositionDao propositionDao= new PropositionDaoImpl();
	private CategorieDao categorieDao= new CategorieDaoImpl();
	private QuestionDao questionDao = new QuestionDaoImpl();
	private DiagrammeForm form= new DiagrammeForm(propositionDao, questionDao);
	
	private Categorie categorie = new Categorie("titre", "description", 1);
	private Question question= new Question (1, 2, 2, 1, "label");

	@Test
	public void testCreateDataset() {
		// 1. Créer la catégorie
		categorieDao.creer(categorie);
		
		// 2. Ecrire l'id de la catégorie dans la question
		question.setCategorieId(categorie.getId());
		
		// 3. Crée la question
		questionDao.creer(question);	
		
		// 4. Créer le diagramme
		CategoryDataset dataset= form.createDataset(categorie, question);
		form.createChart(dataset, question);
		
		// 5. Suppression de la question et la catégorie crée
		questionDao.supprimer(question);
		categorieDao.supprimer(categorie);
	}


}
