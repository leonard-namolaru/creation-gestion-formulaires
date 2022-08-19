package com.sdzee.beans;

/**
 * <b>Reponse Bean</b>
 * 
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet et ajout d'explications à partir du tutoriel (source 1)</p>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * 
 * @author Huihui Huang
 */
public class Reponse {
	/* ************************ ATTRIBUTS *********************** */
	
	/* Bonne pratique de toujours utiliser les objets Wrapper (les objets « enveloppeurs ») - Long, Byte..
	 * dans les beans dont les propriétés correspondent à des champs d'une base de données.
	 * Dans une base de données les valeurs peuvent être initialisées à NULL, 
	 * alors qu'un type primitif en Java (long, byte..) ne peut pas valoir null.
	 * Les objets « enveloppeurs » peuvent être initialisés à null
	 * 
	 */
	
	private Long reponseLogId;
	private String utilisateurEmail;
	private Long questionId;
	private Long propositionId;
	private String valeurReponse;
	private Long categorieId;
	private int scoreTotal;
	
	/* ************************ CONSTRUCTEURS *********************** */
    
	/**
     * Constructeur par défaut
     * 
     * @see Reponse
     */
	public Reponse() {
    	
    }
    
    /**
     * Constructeur utile pour les tests Unitaires JUNIT 
     * 
     * @param utilisateurEmail - L'email d'un bean Reponse
     * 
     * @see Reponse
     */
    public Reponse(String utilisateurEmail) {
    	this.utilisateurEmail= utilisateurEmail;    	
    }
	  
	/* ************************ GETS ET SETS ***************************** */
	
	//GETS
	   public Long getReponseLogId() {
		return reponseLogId;
	}
	
	public String getUtilisateurEmail() {
		return utilisateurEmail;
	}
	
	public Long getQuestionId() {
		return questionId;
	}
	
	public Long getPropositionId() {
		return propositionId;
	}
	
	public String getValeurReponse() {
		return valeurReponse;
	}
	
	public Long getCategorieId() {
		return categorieId;
	}
	
	public int getScoreTotal() {
		return scoreTotal;
	}
	
	
	//SETS
	
	public void setReponseLogId(Long reponseLogId) {
		this.reponseLogId = reponseLogId;
	}
	
	public void setUtilisateurEmail(String utilisateurEmail) {
		this.utilisateurEmail = utilisateurEmail;
	}
	
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	
	public void setPropositionId(Long propositionId) {
		this.propositionId = propositionId;
	}
	
	public void setValeurReponse(String valeurReponse) {
		this.valeurReponse = valeurReponse;
	}

	public void setCategorieId(Long categorieId) {
		this.categorieId = categorieId;
	}


	public void setScoreTotal(int scoreTotal) {
		this.scoreTotal = scoreTotal;
	}


}