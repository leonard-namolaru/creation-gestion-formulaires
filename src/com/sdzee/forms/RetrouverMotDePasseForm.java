package com.sdzee.forms;


import java.util.Date;
import java.util.HashMap;

import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import com.sdzee.beans.Utilisateur;
import com.sdzee.dao.DAOException;
import com.sdzee.dao.UtilisateurDao;

/**
 * <b>La classe RetrouverMotDePasseForm</b>: <br>
 * <ul>
 * 	<li>Traiter les différents champs spécifiques</li>
 * 	<li>Envoi d'un email pour l'initialisation du mot de passe d'un utilisateur</li>
 * 	<li>Mise à jour du mot de passe de l'utilisateur</li>
 * </ul>
 * 
 * <p>Travail effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet</p>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : https://www.jmdoudoux.fr/java/dej/chap-javamail.htm </p>
 * 
 * @author Huihui Huang
 */
public class RetrouverMotDePasseForm {
	
	/* ************************** ATTRIBUTS ************************* */
	private static final String CHAMP_EMAIL				= "email";
	
	private static final String CHAMP_PASS          	= "motdepasse"; 
    private static final String CHAMP_CONF           	= "confirmation";
    
	private static final String UTILISATEUR_SMTP		= "....@gmail.com";
	private static final String PORT_SMTP				= "465";
	private static final String SERVEUR_SMTP 			= "smtp.gmail.com";
	private static final String MOTDEPASSE_SMTP			= "Mot de passe du compte gmail";

	private UtilisateurDao utilisateurDao;
	private InscriptionForm inscriptionForm= new InscriptionForm(utilisateurDao);
	
	private String resultat;
	private Map<String, String> erreurs= new HashMap<String, String>();
	
	/* ************************** CONSTRUCTEURS ************************* */
	
	/**
	 * Consttucteur
	 * 
	 * @param utilisateurDao - UtilisateurDao
	 * 
	 * @see RetrouverMotDePasseForm
	 */
	public RetrouverMotDePasseForm(UtilisateurDao utilisateurDao) {
		this.utilisateurDao= utilisateurDao;
	}
	
	/* ************************** METHODES ************************* */
    
	/**
     * Méthode qui récupère les erreurs
     * 
     * @return L'erreur
     * 
     * @see RetrouverMotDePasseForm
     */
    public Map<String, String> getErreurs() {
        return erreurs;
    }

    /**
     * Méthode qui récupère les resultats
     * 
     * @return Le resultat
     * 
     * @see RetrouverMotDePasseForm
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
     * @see RetrouverMotDePasseForm
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
     * Méthode qui ajoute un message correspondant au champ spécifié à la map des erreurs
     * 
     * @param champ - Le champ spécifié
     * @param message - Le message d'erreur
     * 
     * @see RetrouverMotDePasseForm
     * @see #erreurs
     */
    public void setErreur( String champ, String message ) {
    	
    	/* put(K key, V value)
    	 * Ajoute la clé et la valeur dans la collection en retournant la valeur insérée. 
    	 * Si la clé existe déjà, sa valeur sera écrasée par celle passée en paramètre de la méthode. */
        erreurs.put( champ, message );
    }

    
    /**
     * Méthode chargée de valider l'adresse mail
     * 
     * @param email - L'email saisi
     * 
     * @see RetrouverMotDePasseForm
     * 
     * @throws FormValidationException Si erreurs du email saisi
     */
    private void validationEmail( String email ) throws FormValidationException {
    	
    	if ( email != null ) 
    	{
            if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) 
            {
                throw new FormValidationException( "Merci de saisir une adresse mail valide." );
                
            } 
            else if ( utilisateurDao.trouver( email ) == null ) {
                throw new FormValidationException( "L'adresse email n'existe pas, merci d'en choisir une autre." );
            }
        } 
    	else 
    	{
        	//throw : permet de lever une exception manuellement en instanciant un objet de type Exception (ou un objet hérité).
            throw new FormValidationException( "Merci de saisir une adresse mail." );
        }
    }
	
	/**
	 * Méthode chargée de envoyer un email au destinataire
	 * 
	 * @param destinataire - Le destinataire du email
	 * @param contenu - Le contenu du email
	 * 
	 * @see RetrouverMotDePasseForm
	 * 
	 * @throws Exception Si erreurs de l'envoi du email
	 * @throws NoSuchProviderException Si aucun mode de securité trouvé pour l'envoi du email
	 * @throws MessagingException Si erreurs pendant l'envoi du message
	 */
	public void sendEmail(String destinataire, String contenu) throws Exception {

		//Instancier la session 
		Properties properties= new Properties();
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.host", SERVEUR_SMTP);
		properties.setProperty("mail.smtp.port", PORT_SMTP);
		/* Connexion sécurisée */
		properties.setProperty("mail.smtp.ssl.enable", "true");
		Session session= Session.getInstance(properties);
		session.setDebug(true);
			
		
		try {
			// Création du message
			MimeMessage message= createMimeMessage(session, UTILISATEUR_SMTP, destinataire, contenu);
			
			// Obtenir un objet transport basé sur la session
			Transport transport = session.getTransport();
			transport.connect(SERVEUR_SMTP, UTILISATEUR_SMTP, MOTDEPASSE_SMTP);
			
			//Envoi du mail
			//Envoyer à toutes les adresses de destinataires 
			//Utiliser message.getAllRecipients() pour obtenir tous les destinataires ajouté lors de la création de l'objet courrier
			transport.sendMessage(message, message.getAllRecipients());
			
			//Fermer le transport
			transport.close();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Méthode qui crée la mise en page du message
	 * 
	 * @param session - La session du message
	 * @param expediteur - L'expéditeur du message
	 * @param destinataire - Le destinataire du message
	 * @param contenu - Le contenu du message
	 * 
	 * @return Le message crée
	 * 
	 * @see RetrouverMotDePasseForm
	 * 
	 * @throws Exception Si erreurs de la création du message
	 */
	public static MimeMessage createMimeMessage(Session session, String expediteur, String destinataire, String contenu) throws Exception {
		/* Création du message*/
		MimeMessage message= new MimeMessage(session);
		
		/* Formater le nom de l'expéditeur et l"encodage*/
		message.setFrom(new InternetAddress(expediteur, "Système de gestion de formulmaire", "UTF-8"));
		
		/* Destinataire*/
		message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(destinataire, "UTF-8"));
		
		/* Déinir l'objet du message et l'encodage*/
		message.setSubject("Récupération du mot de passe", "UTF-8");
		
		/* Configurer le corps du message et l'encodage*/
		message.setContent(contenu, "text/html;charset=UTF-8");
		
		/* Configurer la date d'envoi*/
		message.setSentDate(new Date());
		
		/* Enregistrer les configurations */
		message.saveChanges();
		
		return message;
	}
	
	/**
	 * Méthode qui contient le contenu du mail
	 * 
	 * @param userEmail - L'email d'un bean Utilisateur
	 * @param lien - Le lien qui enmène à la mofication de mot de passe de l'utilisateur
	 * @param adminEmail - Le email pour l'envoi de email
	 * 
	 * @return Le contenu du message
	 * 
	 * @see RetrouverMotDePasseForm
	 */
	public String setContenu (String userEmail, String lien, String adminEmail) {
		String contenu="<p> Bonjour, <p>"+ 
		"<p>Vous avez demandé une réunitialisation du mot de passe de votre compte.</p>" +
		"<p>Cliquer sur le lien ci-dessous afin de choisir un nouveau mot de passe: </p>" +
		"\n<a href= " + lien + ">" +lien+ "</a>";
		return contenu;
	}
	
	
	/**
	 * Méthode qui traite du champ email que l'utilisateur a saisi
	 * 
	 * @param email - L'email saisi
	 * @param utilisateur - Un bean Utilisateur
	 * 
	 * @see RetrouverMotDePasseForm
	 */
	public void traiterEmailUtilisateur (String email, Utilisateur utilisateur) {
		try {
            validationEmail( email );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }
        utilisateur.setEmail( email );
	}
	
	
	/**
	 * Méthode qui permet de retrouver le mot de passe de l'utilisateur
	 * 
	 * @param request - Une requête HTTP qui contient la demande du client
	 * 
	 * @return Un utilisateur
	 *
	 * 
	 * @see #traiterEmailUtilisateur(String, Utilisateur)
	 * @see #sendEmail(String, String)
	 * @see #setContenu(String, String, String)
	 * @see RetrouverMotDePasseForm
	 */
	public Utilisateur trouverMotDePasse(HttpServletRequest request) {
		/* Récupération des champs du formulaire */
        String email = getValeurChamp( request, CHAMP_EMAIL );
        
        Utilisateur utilisateur = new Utilisateur();
         
        try
        {
        	traiterEmailUtilisateur(email, utilisateur);
            if ( erreurs.isEmpty() ) 
            {	
            
            	try {
            		/* Clé unique*/
            		String secretKey= UUID.randomUUID().toString();
            		
            		// Création du lien
            		/* Pointe sur la portion de l'URL de la requête qui correspond à notre application*/
        			String path= request.getContextPath();
        			
        			/* request.getScheme() ->  Renvoie HTTP */
        			/* request.getServerName() -> Renvoie le nom du serveur */ 
        			/* request.getServerPort() -> Renvoie le port du serveur en question */
                	String basePath= request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
                	/* Ajout du clé unique et email de l'utilisateur sur URL */
                	String lien =  basePath+"resetMotDePasse?id="+secretKey+"&email="+utilisateur.getEmail();
                	
                	/* Envoi du email*/
					sendEmail(email, setContenu(email, lien, UTILISATEUR_SMTP));
					
				} catch (FormValidationException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
            	resultat="Le message a été envoyé. Veuillez vérifier dans votre boîte mail.";
            } else 
            {
            	resultat="Échec de l'envoi du message.";
            }
        } 
        catch ( DAOException e )
        {
            resultat = "Échec de l'envoie : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }

        return utilisateur;
  
    }
	
	/**
	 * Méthode qui permet la mise à jour du mot de passe de l'utilisateur
	 * 
	 * @param request - La requête HTTP qui contient la demande du client
	 * 
	 * @return Un utilisateur
	 * 
	 * @see RetrouverMotDePasseForm
	 * @see InscriptionForm#traiterMotsDePasse(String, String, Utilisateur)
	 * @see #getErreurs()
	 * @see Utilisateur
	 * @see UtilisateurDao#miseAJourPassword(Utilisateur)
	 */
	public Utilisateur updateMotDePasse(HttpServletRequest request) {
		/* Récupération des champs du formulaire */
        String email = getValeurChamp( request, CHAMP_EMAIL );
        
        String motDePasse = getValeurChamp( request, CHAMP_PASS );
        String confirmation = getValeurChamp( request, CHAMP_CONF );
        
        
        Utilisateur utilisateur = new Utilisateur();
       
         
        try
        {
        	inscriptionForm.traiterMotsDePasse(motDePasse, confirmation, utilisateur);
        	utilisateur.setEmail(email);
        	/* Récupérer le contenu da la map d'erreur crée par l'objet métier InscriptionForm */
            erreurs= inscriptionForm.getErreurs();
            
            if ( erreurs.isEmpty() ) 
            {	
            	/* Mise a jour*/
                utilisateurDao.miseAJourPassword(utilisateur);
                
                resultat = "Succès de la modification.";
                
            } else 
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
