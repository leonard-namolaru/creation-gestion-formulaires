package com.sdzee.beans;

import java.util.List;

/**
 * <b>Question Bean </b>
 * 
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet et ajout d'explications à partir du tutoriel (source 1)</p>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * 
 * @author  Leonard Namolaru
 */
public class Question {
	/* ************************ ATTRIBUTS *********************** */
	
	/* Bonne pratique de toujours utiliser les objets Wrapper (les objets « enveloppeurs ») - Long, Byte..
	 * dans les beans dont les propriétés correspondent à des champs d'une base de données.
	 * Dans une base de données les valeurs peuvent être initialisées à NULL, 
	 * alors qu'un type primitif en Java (long, byte..) ne peut pas valoir null.
	 * Les objets « enveloppeurs » peuvent être initialisés à null
	 * 
	 */

    private Long categorieId;
	private Long id;
    private Integer numeroQuestion; 
    private Integer questionGroupe; /* Les questions d'une catégorie peuvent être divisées en plusieurs groupes */
    private Integer type; /* Par exemple: boutons radio. Chaque type de question aura un numéro spécifique. Par exemple - le numéro 1 dans ce champ = boutons radio */
    private Integer obligatoire; /* 0 - Pas obligatoire, 1 - obligatoire */
    private String label; /* Le texte de la question elle-même */
    private List<Proposition> propositions;
    
    /* ************************ CONSTRUCTEURS *********************** */
    
    /**
     * Constructeur par défaut
     * 
     * @see Question
     */
    public Question() {
    
    }
    
    /**
     * Constructeur utile pour les tests Unitaires JUNIT 
     * 
     * @param numeroQuestion - Le numéro de la question d'un bean Question
     * @param questionGroupe - Le groupe d'un bean Question
     * @param type - Le type de question d'un bean Question
     * @param obligatoire - L'obligation de répondre ou non d'un bean Question
     * @param label - Le label d'un bean Question
     * 
     * @see Question
     */
    public Question(Integer numeroQuestion, Integer questionGroupe, Integer type, Integer obligatoire, String label) {
    	this.numeroQuestion= numeroQuestion;
    	this.questionGroupe= questionGroupe;
    	this.type= type;
    	this.obligatoire= obligatoire;
    	this.label= label;
    	
    }

    /* ************************ GETS ET SETS ***************************** */

    //gets
    
    public Long getId() {
        return id;
    }

    public Long getCategorieId() {
        return categorieId;
    }
    
    public Integer getQuestionGroupe() {
        return questionGroupe;
    }

    public Integer getType() {
        return type;
    }    
    
    public Integer getObligatoire() {
        return obligatoire;
    }   

    public String getLabel() {
        return label;
    }   
            
    public List<Proposition> getPropositions() {
        return propositions;
    }
    
	public Integer getNumeroQuestion() {
		return numeroQuestion;
	}
    
	//sets
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setCategorieId(Long categorieId) {
        this.categorieId = categorieId;
    }
    
    public void setQuestionGroupe(Integer questionGroupe) {
        this.questionGroupe = questionGroupe;
    }

    public void setType(Integer type) {
        this.type = type;
    }    
    
    public void setObligatoire(Integer obligatoire) {
        this.obligatoire = obligatoire;
    }   

    public void setLabel(String label) {
        this.label = label;
    }   
            
    public void setPropositions(List<Proposition> propositions) {
        this.propositions = propositions;
    }

	public void setNumeroQuestion(Integer numeroQuestion) {
		this.numeroQuestion = numeroQuestion;
	}
	
}
