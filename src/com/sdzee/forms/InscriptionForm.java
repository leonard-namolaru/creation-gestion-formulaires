package com.sdzee.forms;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//Utilisation de la bibliothèque Jasypt pour chiffrer le mot de passe efficacement.
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.sdzee.beans.Utilisateur;
import com.sdzee.dao.DAOException;
import com.sdzee.dao.UtilisateurDao;

/**
 * <b>La classe InscriptionForm</b>: <br>
 * <ul>
 * 	<li>Traiter les champs spécifiques pour l'inscription</li>
 * 	<li>Permettre l'inscription d'un utilisateur</li>
 * </ul>
 * 
 * <p>Travail_effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet et ajout d'explications à partir des tutoriels (source 1, source 2)</p>
 * 
 * <p>Projet Sujet : L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * <p>Source2 : openclassrooms.com - Apprenez à programmer en Java - Auteur : Cyrille Herby - License CC BY-NC-SA 2.0</p>
 * <p>Source3 openclassrooms.com - Java et les collections - Auteur : Cyrille Herby - License CC BY-NC-SA 2.0<p>
 * 
 * @author Leonard Namolaru
 */
public final class InscriptionForm {
	/* ************************************ ATTRIBUTS ************************************ */
    private static final String ALGO_CHIFFREMENT 	 	= "SHA-256";
    
    private static final String CHAMP_NOM            	= "nom";
    private static final String CHAMP_PRENOM         	= "prenom";
    private static final String CHAMP_EMAIL          	= "email";
    private static final String CHAMP_PASS           	= "motdepasse";
    private static final String CHAMP_CONF           	= "confirmation";
    
    private static final String CHAMP_SEXE           	= "sexe";
    private static final String CHAMP_SEXE_HOMME     	= "0";
    
    private static final String CHAMP_DATE_NAISSANCE	= "date_naissance";
    
    /* Longueur minimale : nom, prenom, email, motdepasse, confirmation */
    private static final int CHAINE_LONGUEUR_MINIMALE 	= 3;
    
    /* La chaîne resultat - contenant le statut final de la validation des champs */
    private String resultat;
    
    /* 
     * Les collections de type Map<K,V> stockent leurs contenus sous la forme clé-valeur
     * Le langage propose trois implémentations de bases
     * HashMap<K,V> : implémentation utilisant une table de hachage pour stocker ses éléments, mais cet objet n'est pas thread-safe 
     * */
    private Map<String, String> erreurs = new HashMap<String, String>();
    
    private UtilisateurDao      utilisateurDao;

    /* ************************************* CONSTRUCTEURS ****************************** */
    
    /**
     * Constructeur
     * 
     * @param utilisateurDao - UtilisateurDao
     * 
     * @see InscriptionForm
     */
    public InscriptionForm( UtilisateurDao utilisateurDao ) {
        this.utilisateurDao = utilisateurDao;
    }
    
    /* ************************************** METHODES ********************************** */
    
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
     * Méthode qui ajoute un message correspondant au champ spécifié à la map des erreurs
     * 
     * @param nomChamp - Le champ spécifié
     * @param request - La requête HTTP qui contient la demande du client
     * 
     * @return La valeur du champ
     * 
     * @see InscriptionForm
     * @see #erreurs
     */
    public static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        
    	/*Appel à la méthode request.getParameter() : String getParameter(String name)
    	 * récupération d'un champ du formulaire */
    	String valeur = request.getParameter( nomChamp );
    	
        //String.trim() - Supprimer les espaces sur les côtés droit et gauche de la chaîne (début et fin de la chaîne).
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
    
    
    /**
     * Méthode qui ajoute un message correspondant au champ spécifié à la map des erreurs
     * 
     * @param champ - Le champ spécifié
     * @param message - Le message d'erreur
     * 
     * @see InscriptionForm
     * @see #erreurs
     */
    public void setErreur( String champ, String message ) {
    	
    	/* put(K key, V value)
    	 * Ajoute la clé et la valeur dans la collection en retournant la valeur insérée. 
    	 * Si la clé existe déjà, sa valeur sera écrasée par celle passée en paramètre de la méthode. */
        erreurs.put( champ, message );
    }
    
    
  /**
   * Méthode chargée de valider nom et prenom
   * 
   * @param chaine - La chaine saisi
   * 
   * @see InscriptionForm
   * 
   * @throws FormValidationException Si erreurs de la chaine 
   * 
   */
    public void validationNomPrenom( String chaine ) 
      throws FormValidationException {//throws : ce mot clé permet de signaler qu'un morceau de code, une méthode, une classe… est potentiellement dangereux et qu'il faut utiliser un bloc try{…}catch{…}. Il est suivi du nom de la classe qui va gérer l'exception.
    	
    	if ( chaine != null ) 
    	{
            if ( chaine.length() < CHAINE_LONGUEUR_MINIMALE ) 
               throw new FormValidationException( "Le nom et le prenom de l'utilisateur doivent contenir au moins " + CHAINE_LONGUEUR_MINIMALE + " caractères." );                
        } 
    	else //chaine == null
    	{
        	//throw : permet de lever une exception manuellement en instanciant un objet de type Exception (ou un objet hérité).
            throw new FormValidationException( "Le nom et le prenom de l'utilisateur doivent contenir au moins " + CHAINE_LONGUEUR_MINIMALE + " caractères." );                
        }

    }
    
    /**
     * Méthode chargée de valider l'adresse mail
     * 
     * @param email - L'email saisi
     * 
     * @see InscriptionForm 
     * 
     * @throws FormValidationException Si erreurs du email saisi
     */
    public void validationEmail( String email ) 
     throws FormValidationException {//throws : ce mot clé permet de signaler qu'un morceau de code, une méthode, une classe… est potentiellement dangereux et qu'il faut utiliser un bloc try{…}catch{…}. Il est suivi du nom de la classe qui va gérer l'exception.
        
    	if ( email != null ) 
    	{
            if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) 
            {
            	//throw : permet de lever une exception manuellement en instanciant un objet de type Exception (ou un objet hérité).
                throw new FormValidationException( "Merci de saisir une adresse mail valide." );
                
            } 
            else if ( utilisateurDao.trouver( email ) != null ) {
                throw new FormValidationException( "Cette adresse email est déjà  utilisée, merci d'en choisir une autre." );
            }
        } 
    	else //email == null
    	{
        	//throw : permet de lever une exception manuellement en instanciant un objet de type Exception (ou un objet hérité).
            throw new FormValidationException( "Merci de saisir une adresse mail." );
        }
    }
    
    /**
     * Méthode chargée de valider la date de naissace
     * 
     * @param date - La date de naissance saisi
     * 
     * @see InscriptionForm
     * 
     * @throws FormValidationException Si erreurs de la date de naissance saisi
     */
    public void validationDate( String date ) 
     throws FormValidationException {//throws : ce mot clé permet de signaler qu'un morceau de code, une méthode, une classe… est potentiellement dangereux et qu'il faut utiliser un bloc try{…}catch{…}. Il est suivi du nom de la classe qui va gérer l'exception.
        
    	if ( date != null ) 
    	{
            if ( !date.matches( "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|[3][01])" ) ) //date format : yyyy-MM-dd 
            {
            	//throw : permet de lever une exception manuellement en instanciant un objet de type Exception (ou un objet hérité).
                throw new FormValidationException( "Merci de saisir une date valide (yyyy-MM-dd)." );
                
            } 
        } 
    	else //date == null
    	{
        	//throw : permet de lever une exception manuellement en instanciant un objet de type Exception (ou un objet hérité).
            throw new FormValidationException( "Merci de saisir une date de naissance (yyyy-MM-dd)." );
        }
    }

    
    /**
     * Méthode de chargée de valider les mots de passe
     * 
     * @param motDePasse - Le mot de passe saisi
     * @param confirmation - La confirmation du mot de passe saisi
     * 
     * @see InscriptionForm
     * 
     * @throws FormValidationException Si erreurs du mot de passe et de la confirmation saisi
     */
    public void validationMotsDePasse( String motDePasse, String confirmation ) 
     throws FormValidationException { //throws : ce mot clé permet de signaler qu'un morceau de code, une méthode, une classe… est potentiellement dangereux et qu'il faut utiliser un bloc try{…}catch{…}. Il est suivi du nom de la classe qui va gérer l'exception.
        
    	if ( motDePasse != null && confirmation != null ) 
    	{
            if ( !motDePasse.equals( confirmation ) ) 
            {
            	//throw : permet de lever une exception manuellement en instanciant un objet de type Exception (ou un objet hérité).
                throw new FormValidationException( "Les mots de passe entrés sont différents, merci de les saisir à nouveau." );
            
            } 
            else //motDePasse.equals( confirmation ) == true
            {
            	//throw : permet de lever une exception manuellement en instanciant un objet de type Exception (ou un objet hérité).
            	if ( motDePasse.length() < CHAINE_LONGUEUR_MINIMALE )
            		throw new FormValidationException( "Les mots de passe doivent contenir au moins " + CHAINE_LONGUEUR_MINIMALE + " caractères." );
            }
        } 
    	else // (motDePasse == null || confirmation == null)
    	{
        	//throw : permet de lever une exception manuellement en instanciant un objet de type Exception (ou un objet hérité).
            throw new FormValidationException( "Merci de saisir et confirmer votre mot de passe." );
        }
    }
    
    /**
     * Méthode qui traite l'email
     * 
     * @param email - L'email saisi
     * @param utilisateur - Un bean Utilisateur
     * 
     * @see InscriptionForm
     * @see #validationEmail(String)
     * @see #setErreur(String, String)
     */
    public void traiterEmail( String email, Utilisateur utilisateur ) {
        
    	try {
            validationEmail( email ); // Lorsqu'une ligne de code lève une exception, l'instruction dans le bloc try est interrompue et le programme se rend dans le bloc catch correspondant à l'exception levée.
        } catch ( FormValidationException e ) { //Le paramètre de la clause catch - permet de connaître le type d'exception qui doit être capturé.
        	
        	/* private void setErreur( String champ, String message )
        	 * Ajoute un message correspondant au champ spécifié à la map des erreurs. */
            setErreur( CHAMP_EMAIL, e.getMessage() );//L'exception étant capturée, l'instruction du bloc catch s'exécute.
        }
        utilisateur.setEmail( email );//initialisation de la propriete email du bean
    }
    
    /**
     * Méthode qui traite une date 
     * 
     * @param date - La date saisi
     * @param utilisateur - Un bean Utilisateur
     * 
     * @see InscriptionForm
     * @see #validationDate(String)
     * @see #setErreur(String, String)
     */
    public void traiterDate( String date, Utilisateur utilisateur ) {
        
    	try {
            validationDate( date ); // Lorsqu'une ligne de code lève une exception, l'instruction dans le bloc try est interrompue et le programme se rend dans le bloc catch correspondant à l'exception levée.
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            utilisateur.setDateNaissance( LocalDate.parse(date, formatter) );//initialisation de la propriete dateNaissance du bean
    	} catch ( FormValidationException e ) { //Le paramètre de la clause catch - permet de connaître le type d'exception qui doit être capturé.
        	
        	/* private void setErreur( String champ, String message )
        	 * Ajoute un message correspondant au champ spécifié à la map des erreurs. */
            setErreur( CHAMP_DATE_NAISSANCE, e.getMessage() );//L'exception étant capturée, l'instruction du bloc catch s'exécute.
        }
    	
    }
    
    /**
     * Méthode qui traite le nom
     * 
     * @param nom - Le nom saisi
     * @param utilisateur - Un bean Utilisateur
     * 
     * @see InscriptionForm
     * @see #validationNomPrenom(String)
     * @see #setErreur(String, String)
     */
    public void traiterNom( String nom, Utilisateur utilisateur ) {
        
    	try {
            validationNomPrenom( nom ); // Lorsqu'une ligne de code lève une exception, l'instruction dans le bloc try est interrompue et le programme se rend dans le bloc catch correspondant à l'exception levée.
        } catch ( FormValidationException e ) { //Le paramètre de la clause catch - permet de connaître le type d'exception qui doit être capturé.
        	
        	/* private void setErreur( String champ, String message )
        	 * Ajoute un message correspondant au champ spécifié à la map des erreurs. */
            setErreur( CHAMP_NOM + "_" + CHAMP_PRENOM, e.getMessage() );//L'exception étant capturée, l'instruction du bloc catch s'exécute.
        }
        utilisateur.setNom( nom );//initialisation de la propriete nom du bean
    }
    
    /**
     * Méthode qui traite le prenom
     * 
     * @param prenom - Le prenom saisi
     * @param utilisateur - Un bean Utilisateur
     * 
     * @see InscriptionForm
     * @see #validationNomPrenom(String)
     * @see #setErreur(String, String)
     */
    public void traiterPrenom( String prenom, Utilisateur utilisateur ) {
        
    	try {
            validationNomPrenom( prenom ); // Lorsqu'une ligne de code lève une exception, l'instruction dans le bloc try est interrompue et le programme se rend dans le bloc catch correspondant à l'exception levée.
        } catch ( FormValidationException e ) { //Le paramètre de la clause catch - permet de connaître le type d'exception qui doit être capturé.
        	
        	/* private void setErreur( String champ, String message )
        	 * Ajoute un message correspondant au champ spécifié à la map des erreurs. */
            setErreur( CHAMP_NOM + "_" + CHAMP_PRENOM, e.getMessage() );//L'exception étant capturée, l'instruction du bloc catch s'exécute.
        }
        utilisateur.setPrenom( prenom );
    }
    
    /**
     * Méthode qui traite le sexe
     * 
     * @param sexe - Le sexe coché
     * @param utilisateur - Un bean Utilisateur
     * 
     * @see InscriptionForm
     * @see #setErreur(String, String)
     */
    public void traiterSexe( String sexe, Utilisateur utilisateur ) {
    	      	
      if (sexe != null)
      {
    	  if ( CHAMP_SEXE_HOMME.equals( sexe ) )
              utilisateur.setSexe( 0 );//initialisation de la propriete sexe du bean
          else
              utilisateur.setSexe( 1 );//initialisation de la propriete sexe du bean
	
      }
      else
      {
          setErreur( CHAMP_SEXE, "Le champ sexe est obligatoire." );
      }
   	
    }
    
    /**
     * Méthode qui traite le mot de passe
     * 
     * @param motDePasse - Le mot de passe saisi
     * @param confirmation - La confirmation du mot de passe saisi
     * @param utilisateur - Un bean Utilisateur
     * 
     * @see InscriptionForm
     * @see #validationMotsDePasse(String, String)
     * @see #setErreur(String, String)
     */
    public void traiterMotsDePasse( String motDePasse, String confirmation, Utilisateur utilisateur ) {
        
    	try {
            validationMotsDePasse( motDePasse, confirmation );// Lorsqu'une ligne de code lève une exception, l'instruction dans le bloc try est interrompue et le programme se rend dans le bloc catch correspondant à l'exception levée.
        } catch ( FormValidationException e ) { //Le paramètre de la clause catch - permet de connaître le type d'exception qui doit être capturé.
        	
        	/* private void setErreur( String champ, String message )
        	 * Ajoute un message correspondant au champ spécifié à la map des erreurs. */
            setErreur( CHAMP_PASS, e.getMessage() );
            setErreur( CHAMP_CONF, null );
        }

       /* Utilisation de la bibliothèque Jasypt pour chiffrer le mot de passe efficacement.
        * 
        * L'algorithme SHA-256 est ici utilisé, avec par défaut un salage
        * aléatoire et un grand nombre d'itérations de la fonction de hashage.
        * 
        * La String retournée est de longueur 56 et contient le hash en Base64. */
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( ALGO_CHIFFREMENT );
        passwordEncryptor.setPlainDigest( false );
        String motDePasseChiffre = passwordEncryptor.encryptPassword( motDePasse );

        utilisateur.setPassword( motDePasseChiffre );//initialisation de la propriété motDePasse du bean
    }


    /**
     * La méthode qui inscrit un utilisateur sur le site
     * 
     * @param request - Une requête HTTP qui contient la demande du client
     * 
     * @return Un bean Utilisateur
     * 
     * @see InscriptionForm
     * @see #getValeurChamp(HttpServletRequest, String)
     * @see #traiterDate(String, Utilisateur)
     * @see #traiterEmail(String, Utilisateur)
     * @see #traiterMotsDePasse(String, String, Utilisateur)
     * @see #traiterNom(String, Utilisateur)
     * @see #traiterPrenom(String, Utilisateur)
     * @see #traiterSexe(String, Utilisateur)
     */
    public Utilisateur inscrireUtilisateur( HttpServletRequest request ) {
        
    	/* La méthode  getValeurChamp() - vérifier si le contenu d'un champ est vide ou non, 
    	 * getValeurChamp(HttpServletRequest request, String nomChamp)
    	 * suite du code : vérifier si les chaînes sont à null ;   */
    	    	
        String nom = getValeurChamp( request, CHAMP_NOM );
        String prenom = getValeurChamp( request, CHAMP_PRENOM );
    	String email = getValeurChamp( request, CHAMP_EMAIL );
        String motDePasse = getValeurChamp( request, CHAMP_PASS );
        String confirmation = getValeurChamp( request, CHAMP_CONF );
        String sexe = getValeurChamp( request, CHAMP_SEXE );
        String dateNaissance = getValeurChamp( request, CHAMP_DATE_NAISSANCE );

        Utilisateur utilisateur = new Utilisateur();
        
        try // Lorsqu'une ligne de code lève une exception, l'instruction dans le bloc try est interrompue et le programme se rend dans le bloc catch correspondant à l'exception levée.
        {
        	traiterNom( nom, utilisateur );
            traiterPrenom( prenom, utilisateur );
            traiterEmail( email, utilisateur );
            traiterMotsDePasse( motDePasse, confirmation, utilisateur );
            traiterSexe( sexe, utilisateur );
            traiterDate( dateNaissance, utilisateur );

            if ( erreurs.isEmpty() ) 
            {
                utilisateurDao.creer( utilisateur );
                resultat = "Succès de l'inscription.";
            } 
            else 
            {
                resultat = "Échec de l'inscription.";
            }
        } 
        catch ( DAOException e )//Le paramètre de la clause catch - permet de connaître le type d'exception qui doit être capturé.
        {
            resultat = "Échec de l'inscription : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }

        return utilisateur;// La méthode inscrireUtilisateur() -  retourne un bean Utilisateur
    }
}