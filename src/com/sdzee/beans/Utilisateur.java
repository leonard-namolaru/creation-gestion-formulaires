package com.sdzee.beans;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * 
 * <b>Utilisateur Bean</b>
 * 
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet et ajout d'explications à partir du tutoriel (source 1)</p>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * 
 * @author  Huihui Huang, Leonard Namolaru
 */
public class Utilisateur {
	
	/* ************************ ATTRIBUTS *********************** */
	
	/* Bonne pratique de toujours utiliser les objets Wrapper (les objets « enveloppeurs ») - Long, Byte..
	 * dans les beans dont les propriétés correspondent à des champs d'une base de données.
	 * Dans une base de données les valeurs peuvent être initialisées à NULL, 
	 * alors qu'un type primitif en Java (long, byte..) ne peut pas valoir null.
	 * Les objets « enveloppeurs » peuvent être initialisés à null
	 * 
	 */
	
	private Long      id;
    private Integer	  type; /* 0 - Utilisateur normal, 1 - Administrateur */
    private Integer   sexe; /* 0 - Homme, 1 - Femme */
   
    private String    nom;
    private String    prenom;
    private String    email;
    private String    password;
    
    private Timestamp dateInscription;
    private LocalDate dateNaissance;
   
    
    /* ************************ CONSTRUCTEURS *********************** */
    
    /**
     * Constructeur par défaut
     * 
     * @see Utilisateur
     */
    public Utilisateur() {
    }
    
    /**
     * Constructeur utile pour les tests Unitaires JUNIT 
     * 
     * @param email - L'email d'un bean Utilisateur
     * @param sexe - Le sexe d'un bean Utilisateur
     * @param password - Le mot de passe d'un bean Utilisateur
     * @param nom - Le nom d'un bean Utilisateur
     * @param prenom - Le prénom d'un bean Utilisateur
     * @param dateNaissance - La date de naissance d'un bean Utilisateur
     * @param dateInscription - La date d'inscription d'un bean Utilisateur
     * @param type - Le rôle d'un bean Utilisateur
     * 
     * @see Utilisateur
     */
    public Utilisateur(String email,Integer sexe, String password, String nom, String prenom, LocalDate dateNaissance, Timestamp dateInscription, Integer	type) {
    	this.email= email;
    	this.sexe= sexe;
    	this.password= password;
    	this.nom= nom;
    	this.prenom= prenom;
    	this.dateNaissance= dateNaissance;
    	this.type= type;
    	this.dateInscription= dateInscription;
    	
    }
    
    /* ************************ GETS ET SETS ***************************** */

    // GETS
	public Long getId() {
		return id;
	}

	public Integer getType() {
		return type;
	}

	public Integer getSexe() {
		return sexe;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	//SETS
	public Timestamp getDateInscription() {
		return dateInscription;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setSexe(Integer sexe) {
		this.sexe = sexe;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDateInscription(Timestamp dateInscription) {
		this.dateInscription = dateInscription;
	}

	public void setDateNaissance(LocalDate dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
    

    
      
 

}
