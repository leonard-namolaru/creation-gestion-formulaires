package com.sdzee.beans;

import java.util.List;

/**
 * <b>Propostion Bean</b>
 * 
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet et ajout d'explications à partir du tutoriel (source 1)</p>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * 
 * @author  Leonard Namolaru
 */
public class Proposition {

	/* ************************ ATTRIBUTS *********************** */
	
	/* Bonne pratique de toujours utiliser les objets Wrapper (les objets « enveloppeurs ») - Long, Byte..
	 * dans les beans dont les propriétés correspondent à des champs d'une base de données.
	 * Dans une base de données les valeurs peuvent être initialisées à NULL, 
	 * alors qu'un type primitif en Java (long, byte..) ne peut pas valoir null.
	 * Les objets « enveloppeurs » peuvent être initialisés à null
	 * 
	 */

    private Long questionId;
	private Long id;
    private String label; /* Le texte de la proposition / réponse / option elle-même */
    private Integer score; /* Les questions d'une catégorie peuvent être divisées en plusieurs groupes */
    private List<Reponse> reponse;
    private Double pourcentage;
    
    /* ************************ CONSTRUCTEURS *********************** */
    
    /**
     * Constructeur par défaut
     * 
     * @see Proposition
     */
    public Proposition() {
    }
    
    /**
     * Constructeurs utiles pour les tests Unitaires JUNIT 
     * 
     * @param questionId - ID d'un bean Proposition
     * @param label - Le label d'un bean Proposition
     * @param score - Le score d'un bean Proposition
     * 
     * @see Proposition
     */
    public Proposition(Long questionId, String label, Integer score) {
    	this.questionId= questionId;
    	this.label= label;
    	this.score= score;
    	
    }
    /* ************************ GETS ET SETS ***************************** */

    //gets
    
    public Long getId() {
        return id;
    }

    public Long getQuestionId() {
        return questionId;
    }
    
    public String getLabel() {
        return label;
    }   
    
    public Integer getScore() {
        return score;
    }
    
    public List<Reponse> getReponse() {
		return reponse;
	}
    
    public Double getPourcentage() {
		return pourcentage;
	}
    

	//sets
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
    
    public void setLabel(String label) {
        this.label = label;
    }   

    public void setScore(Integer score) {
        this.score = score;
    }


	public void setReponse(List<Reponse> reponse) {
		this.reponse = reponse;
	}

	public void setPourcentage(Double pourcentage) {
		this.pourcentage = pourcentage;
	}
    

}
