package com.sdzee.forms;

import org.jfree.chart.ChartColor;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JFrame;

import com.sdzee.beans.Categorie;
import com.sdzee.beans.Proposition;
import com.sdzee.beans.Question;
import com.sdzee.dao.CategorieDao;
import com.sdzee.dao.CategorieDaoImpl;
import com.sdzee.dao.PropositionDao;
import com.sdzee.dao.PropositionDaoImpl;
import com.sdzee.dao.QuestionDao;
import com.sdzee.dao.QuestionDaoImpl;

/**
 * <b>La classe DiagrammeForm</b>: <br>
 * <ul>
 * 	<li>Génération des diagrammes via l'API JFreeChart </li>
 * 	<li>Génération des diagrammes en fonction des données de la base de données</li>
 * 	<li>Les diagrammes sont regroupés par question d'une catégorie</li>
 * 	<li>Affiche le nombres de personnes qui ont répondus à une proposition en pourcentage</li>
 * </ul>
 * 	
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet et ajout d'explications à partir du tutoriel (source 1)</p>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : http://zetcode.com/java/jfreechart/</p>
 * 
 * @author Huihui Huang
 * 
 */
public class DiagrammeForm extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	private PropositionDao propositionDao= new PropositionDaoImpl();
	private CategorieDao categorieDao= new CategorieDaoImpl();
	private QuestionDao questionDao= new QuestionDaoImpl();
	
	/* ********************* CONSTRUCTEURS ******************** */
	
	/**
	 * Constructeur
	 * 
	 * @param propositionDao - PropositionDao
	 * @param questionDao - QuestionDao
	 * 
	 * @see DiagrammeForm
	 */
	public DiagrammeForm(PropositionDao propositionDao, QuestionDao questionDao) {
		this.propositionDao= propositionDao;
		this.questionDao= questionDao;		
	}
	
	
	/* ************************ METHODES **************************** */
	
	/**
	 * Méthode qui met les données de la requête SQL dans les données du diagramme
	 * 
	 * @param categorie - Un bean Categorie
	 * @param question - Un bean Question
	 * 
	 * @return Les données du diagramme
	 * 
	 * @see JFrame
	 * @see DiagrammeForm
	 * @see PropositionDao#pourcentageGroupByQuestion(Categorie, Question)
	 * @see PropositionDao
	 */
	public CategoryDataset createDataset(Categorie categorie, Question question) {
		// 1. Trouver tous les propositions d'une catégorie, regroupé par question
		List<Proposition> propositions= propositionDao.pourcentageGroupByQuestion(categorie, question);
			
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		// 2. Parcourir les propositions
		for (Proposition proposition:propositions) {
			// Ajout des données de la rêquete SQL dans le diagramme 
			dataset.addValue(proposition.getPourcentage(), "Nombre de personne répondu (%)", proposition.getLabel()+" ("+proposition.getPourcentage()+"%)");
		}
		
        return dataset;
        
	}
	
	/**
	 * Méthode qui définit les paramètres du diagrammes (titres, Orientation...)
	 * 
	 * @param dataset - CategoryDataset
	 * @param question - Un bean Question
	 * 
	 * @return Les paramètres du diagramme
	 * 
	 * @see JFrame
	 */
	public JFreeChart createChart(CategoryDataset dataset, Question question){
		JFreeChart barChart = ChartFactory.createBarChart(
				"Question : "+question.getLabel(), //Titre du diagramme
                "Propositions", //Label de l'axe horizontale
                "Nombre de personne répondu (%)", //Label de l'axe verticale
                dataset, //Data
                PlotOrientation.HORIZONTAL, //Orientation
                false, //Créer une légende ?
        		false, //Créer un info-bulles ?
        		false); // Créer un URL ?
		
		 CategoryPlot plot = barChart.getCategoryPlot(); 
		 BarRenderer br = (BarRenderer) plot.getRenderer();
		 NumberAxis rangeAxe = (NumberAxis) plot.getRangeAxis();
		 CategoryAxis axe = plot.getDomainAxis();
		 
		 // Mettre les valeurs de l'axe horizonlale fixe
	     rangeAxe.setRange(0.0, 100.0);
	     rangeAxe.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
	     
	     // Nombre de ligne maximum sur l'axe verticale (Label)
	     axe.setMaximumCategoryLabelLines(4);
	     
	     // Largeur de chaque ligne
	     axe.setMaximumCategoryLabelWidthRatio(0.3f);
	     
	     // Réglez la distance de l'extrémité gauche du diagramme
	     axe.setUpperMargin(0.2); 
	     
	     // Réglez la distance de l'extrémité droite du diagramme
	     axe.setLowerMargin(0.1); 
		 
		 // Couleur des bars
		 Color color = new Color(0.0f, 0.5f, 1.0f, 0.5f); 
		 br.setSeriesPaint(0, color);
		 
		 // Ajout des valeurs sur les bars du diagramme
		 br.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		 
		 // Rendre visible les valeurs
		 br.setBaseItemLabelsVisible(true);     
		 
		 // Mise en page des valeurs
		 br.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.INSIDE3, TextAnchor.CENTER_RIGHT, TextAnchor.CENTER, 0.0));
		 
		 // Couleur de l'arrière plan du diagramme
		 plot.setBackgroundPaint(ChartColor.white);
		 // Couleur des lignes du diagramme
		 plot.setRangeGridlinePaint(ChartColor.black);
		 
		 plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		 
		
		 return barChart;
		 
	}
	
	
	/**
	 * Méthode qui génére une liste de diagramme 
	 * 
	 * @param request - Une requête HTTP qui contient la demande du client
	 * 
	 * @return La liste de diagramme
	 * 
	 * @see #createChart(CategoryDataset, Question)
	 * @see #createDataset(Categorie, Question)
	 * @see CategorieDao#trouver(Integer)
	 * @see QuestionDao#lister(Categorie)
	 */
	public List<String> genereDiagramme (HttpServletRequest request) {
		// 1. Obtenir l'id de la categorie
		Integer id= Integer.parseInt(request.getParameter("id"));
    	
		// 2. Trouver la catégorie via son id
        Categorie categorie= categorieDao.trouver(id);
        
        // 3. Lister les questions de cette catégorie
   	 	List<Question> questions= questionDao.lister(categorie);
   	 
   	 	List<String> images= new ArrayList<String>();
   	 
   	 	// 4. Parcourir les questions de la catégorie
   	 	for (Question question: questions) {
   	 		// Si le type de question est égale à 1(Radio) ou 2(CheckBox) ou 3(Select)
			if (question.getType().equals(1) || question.getType().equals(2) || question.getType().equals(3)) {
				// 5. Mettre les données dans le diagramme
				CategoryDataset dataset= createDataset(categorie, question);
				// 6. Mise en page du diagramme
				JFreeChart chart= createChart(dataset, question);
				String fileName = null;
				
				try {
					// 7. Conversion du diagramme généré en PNG
					fileName = ServletUtilities.saveChartAsPNG(chart, 1100, 700, request.getSession());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				// 8. Génération d'un URL du diagramme
				String imageURL=request.getContextPath() + "/DisplayChart?filename="+fileName;
				
				// 9. Ajout de l'image dans la liste
				images.add(imageURL);
			}
			
   	 	}
   	 	
   	 	// 10. Ajout de la categorie dans request
   	 	request.setAttribute("categorie", categorie);
   	 	
   	 	return images;
	}
	

}
