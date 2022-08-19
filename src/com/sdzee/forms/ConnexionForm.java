package com.sdzee.forms;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.sdzee.beans.Utilisateur;
import com.sdzee.dao.*;

/**
 * <b>La classe ConnexionForm</b> : <br>
 * <ul>
 * 	<li>Traiter la connexion pour que l'utilisateur soit capable de se connecter après s'être inscrit </li>
 * </ul>
 * 	
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet et ajout d'explications à partir du tutoriel (source 1)</p>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * 
 * @author Huihui Huang
 */
public final class ConnexionForm {
	
	/* ************************************ ATTRIBUTS ************************************ */
	private static final String CHAMP_EMAIL			= "connexion_email";
	private static final String CHAMP_PASS			= "connexion_mot_de_passe";
	private static final String ALGO_CHIFFREMENT	="SHA-256";
	
	
	private UtilisateurDao utilisateurDao;
	
	private String resultat;
	private Map<String, String> erreurs= new HashMap<String, String>();
	
	/* ************************************* CONSTRUCTEURS ****************************** */
	
	/**
	 * Constructeur
	 * 
	 * @param utilisateurDao - UtilisateurDao
	 * 
	 * @see ConnexionForm
	 */
	public ConnexionForm(UtilisateurDao utilisateurDao) {
		this.utilisateurDao= utilisateurDao;
	}
	
	
	/* ************************************** METHODES ********************************** */
	
	/**
	 * Méthode qui permet la connexion d'un utilisateur
	 * 
	 * @param request - Une Requête HTTP qui contient la demande du client
	 * 
	 * @return Un bean Utilisateur
	 * 
	 * @see ConnexionForm
	 * @see #traiterEmailUtilisateur(String, Utilisateur)
	 * @see #traiterMotDePasseUtilisateur(String, Utilisateur)
	 * @see UtilisateurDao#trouver(String)
	 * @see #getValeurChamp(HttpServletRequest, String)
	 * @see #erreurs
	 * @see Utilisateur
	 * 
	 */
	public Utilisateur connexion(HttpServletRequest request) {
		// 1. Récupération des champs du formulaire 
        String connexion_email = getValeurChamp( request, CHAMP_EMAIL );
        String connexion_mot_de_passe = getValeurChamp( request, CHAMP_PASS );
        
        Utilisateur utilisateur = new Utilisateur();
        
        // Validation des données par rapport à l'utilisateur de référence en base 
    	Utilisateur utilisateurReference= null;
        
        try {
        	// 2. Traiter les champs en question 
        	traiterEmailUtilisateur(connexion_email, utilisateur);
        	traiterMotDePasseUtilisateur(connexion_mot_de_passe, utilisateur);
        
        
        	// 3. Si le champs n'est pas vide
        	if (!erreurs.containsKey(CHAMP_EMAIL)) {
        	/* Trouver l'utilisateur en question grâce à son email*/
        	utilisateurReference= utilisateurDao.trouver(connexion_email);
        	}
        
        	// 4. Si l'utilisateur n'est pas trouvé: générer une erreur 
        	if (utilisateurReference == null) {
        		setErreur(CHAMP_EMAIL, "Utilisateur introuvable.");
        	} else if (!comparerMotDePasse(connexion_mot_de_passe, utilisateurReference.getPassword())) {
        		setErreur(CHAMP_PASS, "Mot de passe incorrect.");
        	}
        
        	// 5. Si aucune erreurs 
        	if (erreurs.isEmpty()) {
        		resultat="Succès de la connexion.";
        	} else {
        		resultat="Échec de la connexion.";
        	}
        } catch (DAOException e){
        	resultat= "Échec de la connexion : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
        }
     
		return utilisateurReference;
  
    }
	
	
	/**
	 * Méthode qui permet la validation du champ email que l'utilisateur a saisie
	 * 
	 * @param email - L'email saisi par l'utilisateur
	 * @param utilisateur - Un bean Utilisateur
	 * 
	 * @see ConnexionForm
	 * @see #validationEmail(String)
	 * @see Utilisateur
	 * @see #setErreur(String, String)
	 */
	public void traiterEmailUtilisateur (String email, Utilisateur utilisateur) {
		try {
            validationEmail( email );
        } catch ( Exception e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }
        utilisateur.setEmail( email );
	}
	
	
	/**
	 * Méthode qui permet la validation du mot de passe que l'utilisateur a saisie 
	 * 
	 * @param motDePasse - Le mot de passe saisi
	 * @param utilisateur - Un bean Utilisateur 
	 * 
	 * @see ConnexionForm
	 * @see #validationMotDePasse(String)
	 * @see #setErreur(String, String)
	 * @see Utilisateur
	 */
	public void traiterMotDePasseUtilisateur (String motDePasse, Utilisateur utilisateur) {
		try {
            validationMotDePasse( motDePasse);
        } catch ( Exception e ) {
            setErreur( CHAMP_PASS, e.getMessage() );
        }
        utilisateur.setPassword(motDePasse);
	}
	
	
	/**
     * Méthode qui permet de Valider l'adresse email saisie
     * 
     * @param email - L'email saisi 
     * 
     * @see FormValisationException
     * @see ConnexionForm
     * 
     * @throws FormValidationException Si erreurs du email saisi
     */
    private void validationEmail( String email ) throws FormValidationException {
        if ( email != null) {
        	if (!email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
        		throw new FormValidationException( "Merci de saisir une adresse mail valide." );
        	}
        } else {
        	throw new FormValidationException("Merci de saisir une adresse mail");
        }
    }
    
    
    /**
     * Méthode qui permet de valider le mot de passe saisi
     * 
     * @param motDePasse - Le mot de passe saisi
     * 
     * @see ConnexionForm
     * 
     * @throws FormValidationException Si erreurs du mot de passe saisi
     */
    private void validationMotDePasse( String motDePasse ) throws FormValidationException {
        if ( motDePasse != null ) {
            if ( motDePasse.length() < 3 ) {
                throw new FormValidationException( "Le mot de passe doit contenir au moins 3 caractères." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir votre mot de passe." );
        }
    }
    
    
    /**
     * Méthode qui permet la Vérification du MDP avec Jasypt
     * 
     * @param password - Le mot de passe saisi
     * @param encryptedPassword - Le mot de passe haché
     * 
     * @see ConnexionForm
     * 
     * @return Un boolean si mot de passe à été haché
     */
    private boolean comparerMotDePasse(String password, String encryptedPassword) {
    	/* Utilisation de la bibliothèque Jasypt pour chiffrer le mot de passe efficacement.
         * 
         * L'algorithme SHA-256 est ici utilisé, avec par défaut un salage
         * aléatoire et un grand nombre d'itérations de la fonction de hashage.
         * 
         * La String retournée est de longueur 56 et contient le hash en Base64. */
    	
    	// Ici nous devons vérifier le mot de passe par rapport à la base de données
    	ConfigurablePasswordEncryptor passwordEncryptor= new ConfigurablePasswordEncryptor();
    	passwordEncryptor.setAlgorithm(ALGO_CHIFFREMENT);
    	passwordEncryptor.setPlainDigest(false);
    	
    	return passwordEncryptor.checkPassword(password, encryptedPassword);
    
    }
    
    
    /**
     * Méthode qui ajoute un message correspondant au champ spécifié à la map des erreurs
     * 
     * @param champ - Le champ spécifié
     * @param message - Le message d'erreur
     * 
     * @see ConnexionForm
     * @see #erreurs
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }
    
    /**
     * Méthode qui récupère les erreurs
     * 
     * @return L'erreur
     * 
     * @see InscriptionForm
     */
    public Map<String, String> getErreurs() {
        return erreurs;
    }
   
    /**
     * Méthode qui récupère les resultats
     * 
     * @return Le resultat
     * 
     * @see InscriptionForm
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
     * @see ConnexionForm
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
    
    
}
	

