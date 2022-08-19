package com.sdzee.beans;
import java.util.Vector;

/**
 * <b>Categorie Bean</b>
 * 
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet et ajout d'explications à partir du tutoriel (source 1)</p>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * 
 * @author Leonard Namolaru
 * 
 */
public class Categorie {
	
	/* ************************ ATTRIBUTS *********************** */
	
	/* Bonne pratique de toujours utiliser les objets Wrapper (les objets « enveloppeurs ») - Long, Byte..
	 * dans les beans dont les propriétés correspondent à des champs d'une base de données.
	 * Dans une base de données les valeurs peuvent être initialisées à NULL, 
	 * alors qu'un type primitif en Java (long, byte..) ne peut pas valoir null.
	 * Les objets « enveloppeurs » peuvent être initialisés à null
	 * 
	 */

	private Long id;
    private Utilisateur utilisateur;
    private String titre;
    private String description;
    private Integer nbGroupes; /* Le nombre de groupes de questions dans la catégorie */
    private Vector<Question> questions;
        
    /* ************************ CONSTRUCTEURS *********************** */
    
    /**
     * Constructeur par défaut 
     * 
     * @see Categorie
     */
    public Categorie() {
    }
    
    /**
     * Constructeur utile pour les tests Unitaires JUNIT 
     * 
     * @param titre - Le titre d'un bean Categorie
     * @param description - La description d'un bean Categorie
     * @param nbGroupes - Le nombre de groupe d'un bean Categorie
     * 
     * @see Categorie
     */
    public Categorie(String titre, String description, Integer nbGroupes) {
    	this.description= description;
    	this.titre= titre;
    	this.nbGroupes= nbGroupes;
    	
    }

    /* ************************ GETS ET SETS ***************************** */
    
	//gets
    
    public Long getId() {
        return id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    
    public String getTitre() {
        return titre;
    }   
    
    public String getDescription() {
        return description;
    }


    public Integer getNbGroupes() {
        return nbGroupes;
    }
    
    public Vector<Question> getQuestions() {
        return questions;
    }
    
	//sets
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setUtilisateur( Utilisateur utilisateur ) {
        this.utilisateur = utilisateur;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }   
    
    public void setDescription(String description) {
        this.description = description;
    }

    public void setNbGroupes(Integer nbGroupes) {
        this.nbGroupes = nbGroupes;
    }
    
    public void setQuestions(Vector<Question> questions) {
        this.questions = questions;
    }
   
}
