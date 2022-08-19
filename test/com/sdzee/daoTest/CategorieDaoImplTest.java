package com.sdzee.daoTest;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.sdzee.beans.Categorie;
import com.sdzee.dao.CategorieDao;
import com.sdzee.dao.CategorieDaoImpl;

/**
 * <b> La classe CategorieDaoImplTest</b>:
 * <ul>
 * 	<li>Test Unitaire de la classe CategorieDaoImpl</li>
 * </ul>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 * 
 * @author Huihui Huang
 */
@FixMethodOrder(MethodSorters.JVM) // Ordre d'éxécution des méthodes par ordre d'emplacement
public class CategorieDaoImplTest {
	
	private CategorieDao dao= new CategorieDaoImpl();
    
	private Categorie categorie = new Categorie("titre", "description", 1);

	@Test
	public void test() {
		// 1. Création
		dao.creer(categorie);
		// 2. Retrouver 
		dao.trouver(categorie.getId().intValue());
		// 3. Mise à jour
		dao.update(categorie);
		// 4. Suppression
		dao.supprimer(categorie);
	}

	@Test
	public void testLister() {
		dao.lister(0, 5);
	}

	@Test
	public void testGetTotal() {
		dao.getTotal();
	}

}
