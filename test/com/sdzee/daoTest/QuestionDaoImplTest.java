package com.sdzee.daoTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.sdzee.beans.Categorie;
import com.sdzee.beans.Question;
import com.sdzee.dao.CategorieDao;
import com.sdzee.dao.CategorieDaoImpl;
import com.sdzee.dao.QuestionDao;
import com.sdzee.dao.QuestionDaoImpl;

/**
 * <b> La classe QuestionDaoImplTest</b>:
 * <ul>
 * 	<li>Test Unitaire de la classe QuestionDaoImpl</li>
 * </ul>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 * 
 * @author Huihui Huang
 */
@FixMethodOrder(MethodSorters.JVM) // Ordre d'éxécution des méthodes par ordre d'emplacement
public class QuestionDaoImplTest {

	private CategorieDao categorieDao= new CategorieDaoImpl();
	private QuestionDao questionDao = new QuestionDaoImpl();
	
	private Categorie categorie = new Categorie("titre", "description", 1);
	private Question question= new Question (1, 2, 2, 1, "label");
	private List<Question> questions= new ArrayList<Question>();

	@Test
	public void test() {
		// 1. Création de la catégorie
		categorieDao.creer(categorie);
		
		// 2. Ecrire l'id de la catégorie dans la question
		question.setCategorieId(categorie.getId());
		
		// 3. Crée la question
		questionDao.creer(question);	
		
		// 4. Trouver la question
		question= questionDao.trouver(question.getId().intValue());
		questions.add(question);
		
		// 8. Mise à jour d'une liste de questions
		questionDao.updateQuestions(questions);
		
		// 9. Suppression de la question et la catégorie crée
		questionDao.supprimer(question);
		categorieDao.supprimer(categorie);
	}
	

	@Test
	public void testListerCategorie() {
		questionDao.lister(categorie);
	}

	@Test
	public void testListerCategorieInteger() {
		questionDao.lister(categorie, 1);
	}

}
