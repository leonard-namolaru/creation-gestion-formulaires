package com.sdzee.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.sdzee.beans.Utilisateur;
import com.sdzee.dao.DAOException;
import com.sdzee.dao.UtilisateurDao;

/**
 * <b>La classe UpdateForm</b>: <br>
 * <ul>
 * 	<li>Traiter les différents champs spécifiques pour la mise à jour des données de l'utilisateur</li>
 * 	<li>Mise à jour des données de l'utilisateur</li>
 * </ul>
 * 
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet</p>
 * 
 * <p>Projet Sujet : L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * 
 * @author Huihui Huang
 */
public class UpdateUtilisateurForm {
	
	/* ************************** ATTRIBUTS ************************* */
	
	private static final String CHAMP_NOM            = "nom";
    private static final String CHAMP_PRENOM         = "prenom";
    
    private static final String CHAMP_EMAIL			 = "email";
    
    private static final String CHAMP_SEXE           = "sexe";
    
    private static final String CHAMP_DATE_NAISSANCE = "date_naissance";
    
	private UtilisateurDao utilisateurDao;
	private InscriptionForm inscriptionForm= new InscriptionForm(utilisateurDao);
	
    
    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();
    
    /* ************************** CONSTRUCTEURS ************************* */
    
    /**
     * Contructeur
     * 
     * @param utilisateurDao - UtilisateurDao
     * 
     * @see UpdateUtilisateurForm
     */
    public UpdateUtilisateurForm( UtilisateurDao utilisateurDao ) {
        this.utilisateurDao = utilisateurDao;
    }
    
    
    /* ************************** METHODES ************************* */
    
    /**
     * Méthode qui récupère les erreurs
     * 
     * @return L'erreur
     * 
     * @see UpdateUtilisateurForm
     */
    public Map<String, String> getErreurs() {
        return erreurs;
    }
    
    /**
     * Méthode qui récupère les resultats
     * 
     * @return Le resultat
     * 
     * @see UpdateUtilisateurForm
     */
    public String getResultat() {
        return resultat;
    }
    
    /**
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu sinon
     * 
     * @param request - Une requête HTTP qui contient la demande du client
     * @param nomChamp - Le champ spécifié
     * 
     * @see UpdateUtilisateurForm
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {  
    	 /* récupération d'un champ du formulaire */
    	String valeur = request.getParameter( nomChamp );
    	
        //String.trim() - Supprimer les espaces sur les côtés droit et gauche de la chaîne (début et fin de la chaîne).
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
    
    
	/**
	 * Méthode qui permet de Modifier les données personnelles d'un utilisateur via l'administrateur
	 * 
	 * @param request - La requête HTTP qui contient la demande du client
	 * 
	 * @return Un utilisateur
	 * 
	 * @see Utilisateur
	 * @see UpdateUtilisateurForm
	 * @see InscriptionForm#traiterNom(String, Utilisateur)
	 * @see InscriptionForm#traiterPrenom(String, Utilisateur)
	 * @see InscriptionForm#traiterDate(String, Utilisateur)
	 * @see InscriptionForm#traiterSexe(String, Utilisateur)
	 * @see InscriptionForm#getErreurs()
	 */
	public Utilisateur modification( HttpServletRequest request ) {
		String nom = getValeurChamp( request, CHAMP_NOM );
        String prenom = getValeurChamp( request, CHAMP_PRENOM );
        String email = getValeurChamp(request, CHAMP_EMAIL);
        String sexe = getValeurChamp( request, CHAMP_SEXE );
        String dateNaissance = getValeurChamp( request, CHAMP_DATE_NAISSANCE );

        Utilisateur utilisateur = new Utilisateur();
       
        
        try
        {
        	/* On fait appel à l'objet inscriptionForm pour faciliter le travail de traitement des champs*/
            inscriptionForm.traiterNom(nom, utilisateur);
            inscriptionForm.traiterPrenom(prenom, utilisateur);
            inscriptionForm.traiterDate(dateNaissance, utilisateur);
            inscriptionForm.traiterSexe(sexe, utilisateur);
            utilisateur.setEmail(email);
            
            /* Récupérer le contenu da la map d'erreur crée par l'objet métier InscriptionForm */
            erreurs= inscriptionForm.getErreurs();
            
            if ( erreurs.isEmpty() ) 
            	
            {	
            	/* Mise a jour*/
                utilisateurDao.miseAJourAdmin(utilisateur);
                
                resultat = "Succès de la modification.";
            } 
            else 
            {
                resultat = "Échec de la modification.";
            }
        } 
        catch ( DAOException e )
        {
            resultat = "Échec de la modification : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }

        return utilisateur;
	}
}
