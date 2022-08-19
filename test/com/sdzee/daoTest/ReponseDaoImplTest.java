package com.sdzee.daoTest;

import java.sql.Timestamp;

import java.time.LocalDate;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.sdzee.beans.Categorie;
import com.sdzee.beans.Proposition;
import com.sdzee.beans.Question;
import com.sdzee.beans.Reponse;
import com.sdzee.beans.Utilisateur;
import com.sdzee.dao.CategorieDao;
import com.sdzee.dao.CategorieDaoImpl;
import com.sdzee.dao.PropositionDao;
import com.sdzee.dao.PropositionDaoImpl;
import com.sdzee.dao.QuestionDao;
import com.sdzee.dao.QuestionDaoImpl;
import com.sdzee.dao.ReponseDao;
import com.sdzee.dao.ReponseDaoImpl;

/**
 * <b> La classe ReponseDaoImplTest</b>:
 * <ul>
 * 	<li>Test Unitaire de la classe ReponseDaoImpl</li>
 * </ul>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 * 
 * @author Huihui Huang
 */
@FixMethodOrder(MethodSorters.JVM) // Ordre d'éxécution des méthodes par ordre d'emplacement
public class ReponseDaoImplTest {
	
	private PropositionDao propositionDao= new PropositionDaoImpl();
	private CategorieDao categorieDao= new CategorieDaoImpl();
	private QuestionDao questionDao = new QuestionDaoImpl();
	private ReponseDao reponseDao = new ReponseDaoImpl();
    
	private Proposition proposition = new Proposition( (long) 1, "label", 13); 
	private Question question= new Question (1, 2, 2, 1, "label");
	private Categorie categorie = new Categorie("titre", "description", 1);
	private Reponse reponse= new Reponse("testJunit@test.com");
	
	private LocalDate lt = LocalDate.now();
	
	private  Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    
	private  Utilisateur utilisateur= new Utilisateur("testJunit@test.com", 1, "test", "Test", "Junit", lt, timestamp, 0);
	
	@Test 
	public void test() {
		// 1. Création de la catégorie et de la question
		categorieDao.creer(categorie);
		
		question.setCategorieId(categorie.getId());
		questionDao.creer(question);
		Long questionId= question.getId();
		
		// 2. Ecrire l'id de la question dans la proposition
		proposition.setQuestionId(questionId);
		
		// 3. Création de la proposition
		propositionDao.creer(proposition);
		
		// 4. Création de la reponse
		reponse.setPropositionId(proposition.getId());
		reponse.setQuestionId(question.getId());
		reponse.setValeurReponse(proposition.getLabel());
		reponse.setCategorieId(categorie.getId());
		
		// 5. Création de la reponse
		reponseDao.creer(reponse);
			
		
		// 6. Suppression de la reponse, proposition, question et catégorie crées
		reponseDao.supprimerReponse(reponse);
		propositionDao.supprimer(proposition);
		questionDao.supprimer(question);
		categorieDao.supprimer(categorie);
		
	}

	@Test
	public void testScore() {
		reponseDao.score(categorie, 0, 1);
	}
	
	@Test 
	public void testLister() {
		reponseDao.lister();
	}
	
	@Test
	public void testListerCategorie() {
		reponseDao.listerCategorie(utilisateur);
	}
	
	@Test
	public void testTotal() {
		reponseDao.total(categorie);
	}
	
	@Test 
	public void testTrouverProp() {
		reponseDao.trouver(proposition);
	}
	
	@Test 
	public void testTrouverQuestion() {
		reponseDao.trouver(question);
	}
	
	@Test 
	public void testTrouverAll() {
		reponseDao.trouverAll(utilisateur, categorie);
	}
	
	@Test 
	public void testsupprimerReponseUser() {
		reponseDao.supprimerReponseUser(utilisateur);
	}
}
