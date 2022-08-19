package com.sdzee.daoTest;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.sdzee.beans.Categorie;
import com.sdzee.beans.Proposition;
import com.sdzee.beans.Question;
import com.sdzee.beans.Utilisateur;
import com.sdzee.dao.CategorieDao;
import com.sdzee.dao.CategorieDaoImpl;
import com.sdzee.dao.PropositionDao;
import com.sdzee.dao.PropositionDaoImpl;
import com.sdzee.dao.QuestionDao;
import com.sdzee.dao.QuestionDaoImpl;

/**
 * <b> La classe PropositionDaoImplTest</b>:
 * <ul>
 * 	<li>Test Unitaire de la classe PropositionDaoImpl</li>
 * </ul>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 * 
 * @author Huihui Huang
 */
@FixMethodOrder(MethodSorters.DEFAULT) // Ordre d'éxécution des méthodes en fonction de l'alphabet
public class PropositionDaoImplTest {

	private PropositionDao propositionDao= new PropositionDaoImpl();
	private CategorieDao categorieDao= new CategorieDaoImpl();
	private QuestionDao questionDao = new QuestionDaoImpl();
    
	private Proposition proposition = new Proposition( (long) 1, "label", 13); 
	private Question question= new Question (1, 2, 2, 1, "label");
	private Categorie categorie = new Categorie("titre", "description", 1);
	
	private LocalDate lt = LocalDate.now();
	private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	private Utilisateur utilisateur= new Utilisateur("testJunit@test.com", 1, "test", "Test", "Junit", lt, timestamp, 0);

	@Test
	public void testACreerProposition() {
		// 1. Création de la catégorie et de la question
		categorieDao.creer(categorie);
		
		question.setCategorieId(categorie.getId());
		questionDao.creer(question);
		
		// 2. Ecrire l'id de la question dans la proposition
		proposition.setQuestionId(question.getId());
		// 3. Création de la proposition
		propositionDao.creer(proposition);
		
		// 4. Trouver la proposition
		propositionDao.trouver(proposition.getId().intValue());
	
		
		// 5. Lister les propositions de la catégorie
		propositionDao.lister(categorie.getId().intValue());
		
		// 6. Suppression de la proposition, question et catégorie crée
		propositionDao.supprimer(proposition);
		questionDao.supprimer(question);
		categorieDao.supprimer(categorie);
		
	}

	@Test
	public void testListerQuestionId() {
		propositionDao.listerQuestionId(question);
	}

	@Test
	public void testScoreObtenu() {
		propositionDao.scoreObtenu(categorie, utilisateur);
	}

	@Test
	public void testScoreTotalQUnique() {
		propositionDao.scoreTotalQUnique(question);
	}

	@Test
	public void testScoreTotalQMultiple() {
		propositionDao.scoreTotalQMultiple(question);
	}

	@Test
	public void testPourcentageGroupByQuestion() {
		propositionDao.pourcentageGroupByQuestion(categorie, question);
	}

}
