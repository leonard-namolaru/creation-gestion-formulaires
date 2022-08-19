package com.sdzee.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.sdzee.beans.Categorie;
import com.sdzee.beans.Proposition;
import com.sdzee.beans.Question;
import com.sdzee.beans.Reponse;
import com.sdzee.beans.Utilisateur;
import com.sdzee.dao.CategorieDao;
import com.sdzee.dao.DAOException;

import com.sdzee.dao.PropositionDao;
import com.sdzee.dao.QuestionDao;
import com.sdzee.dao.ReponseDao;

/**
 * <b>La classe ReponseForm</b> : <br>
 * <ul>
 * 	<li>Traiter les valeurs saisies dans les champs après qu'un utilisateur a répondu aux questions d'une catégorie</li>
 * 	<li>Crée la liste de réponses d'une catégorie</li>
 * 	<li>Mise à jour d'une liste de réponses d'une catégorie</li>
 * </ul>
 * 
 * <p>Travail effectué : Source 1 comme base à partir de laquelle nous avons développé en fonction des besoins de notre projet et ajout d'explications à partir des tutoriels (source 1, source 2, source 3)</p>
 * 
 * <p>Projet : Sujet L2N1 - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes</p>
 *   
 * <p>Source1 : openclassrooms.com - Créez votre application web avec Java EE - Auteur : Médéric Munier - License CC BY-NC-SA 2.0</p>
 * <p>Source2 : openclassrooms.com - Apprenez à programmer en Java - Auteur : Cyrille Herby - License CC BY-NC-SA 2.0</p>
 * <p>Source3 : openclassrooms.com - Java et les collections - Auteur : Cyrille Herby - License CC BY-NC-SA 2.0</p>
 * 
 * @author Huihui Huang, Leonard Namolaru
 */
public final class ReponseForm {
	
	/* ************************** ATTRIBUTS ************************* */    
    private QuestionDao questionDao;
    private PropositionDao propositionDao;
	private ReponseDao reponseDao;
	private CategorieDao categorieDao;
    private String resultat;  //La chaîne resultat - contenant le statut final de la validation des champs
    private Map<String, String> erreurs = new HashMap<String, String>();
    
    /* Les collections de type Map<K,V> stockent leurs contenus sous la forme clé-valeur
     * Le langage propose trois implémentations de bases
     * HashMap<K,V> : implémentation utilisant une table de hachage pour stocker ses éléments, mais cet objet n'est pas thread-safe 
     * */

	/* ************************ CONSTRUCTEURS *********************** */
    
    /**
     * Constructeur
     * 
     * @param questionDao - QuestionDao
     * @param categorieDao - CategorieDao
     * @param propositionDao - PropositionDao
     * @param reponseDao - ReponseDao
     * 
     * @see ReponseForm
     */
    public ReponseForm(QuestionDao questionDao, CategorieDao categorieDao, PropositionDao propositionDao, ReponseDao reponseDao) {
        this.questionDao = questionDao;
        this.propositionDao = propositionDao;
        this.reponseDao = reponseDao;
        this.categorieDao= categorieDao;
    }
    
    /**
     * Constructeur
     * 
     * @param questionDao - QuestionDao
     * @param propositionDao - PropositionDao
     * 
     * @see ReponseForm
     */
    public ReponseForm(QuestionDao questionDao, PropositionDao propositionDao) {
    	this.questionDao= questionDao;
    	this.propositionDao= propositionDao;
    }
    
    
	/* ************************ METHODES **************************** */
    
    /**
     * Getters Erreurs - méthode qui récupère les erreurs
     * 
     * @return L'erreur
     * 
     * @see ReponseForm
     */ 
    public Map<String, String> getErreurs() {
        return erreurs;
    }

    /**
     * Getters résultat - méthode qui récupère le résultat
     * 
     * @return Le resultat
     * 
     * @see ReponseForm
     */
    public String getResultat() {
        return resultat;
    }
       
    
    /**
     * Méthode qui ajoute un message correspondant au champ spécifié à la map des erreurs
     * 
     * @param champ - Le champ spécifié
     * @param message - Le message d'erreur
     * 
     * @see ReponseForm
     * @see #erreurs
     */
    public void setErreur( String champ, String message ) {
    	/* put(K key, V value)
    	 * Ajoute la clé et la valeur dans la collection en retournant la valeur insérée. 
    	 * Si la clé existe déjà, sa valeur sera écrasée par celle passée en paramètre de la méthode. */
        erreurs.put( champ, message );
    }    
    
    /**
     * Méthode chargér de valider un champ de type tel
     * 
     * @param tel - Le champ tel
     * 
     * @throws FormValidationException Si erreurs du champ spécifiée
     * 
     * @see ReponseForm
     */
    public void validationTel(String tel) throws FormValidationException {
    	if (tel != null) {
    		if (!tel.matches("^(?:(?:\\+|00)33|0)\\s*[1-9](?:[\\s.-]*\\d{2}){4}$")) {
    			throw new FormValidationException( "Merci de saisir un numéro de téléphone valide." );
    		}
    	} 
    }
    
    /**
     * Méthode chargée de valider l'adresse mail
     * 
     * @param email - L'email saisi
     * 
     * @see ReponseForm
     * 
     * @throws FormValidationException Si erreurs du email saisi
     */
    public void validationEmail( String email ) throws FormValidationException {
    	//throws : ce mot clé permet de signaler qu'un morceau de code, une méthode, une classe… est potentiellement dangereux et qu'il faut utiliser un bloc try{…}catch{…}. Il est suivi du nom de la classe qui va gérer l'exception.
    	if ( email != null ) 
    	{
            if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) 
            {
            	//throw : permet de lever une exception manuellement en instanciant un objet de type Exception (ou un objet hérité).
                throw new FormValidationException( "Merci de saisir une adresse mail valide." );
            } 
        } 
    }
    
    /**
     * Méthode chargée de valider une date
     * 
     * @param date - La date saisi
     * 
     * @see ReponseForm
     * 
     * @throws FormValidationException Si erreurs de la date saisi
     */
    public void validationDate( String date ) throws FormValidationException {
    	//throws : ce mot clé permet de signaler qu'un morceau de code, une méthode, une classe… est potentiellement dangereux et qu'il faut utiliser un bloc try{…}catch{…}. Il est suivi du nom de la classe qui va gérer l'exception.
    	if (date !=null) 
    	{
    		if ( !date.matches( "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|[3][01])" ) ) //date format : yyyy-MM-dd 
            {
    			throw new FormValidationException( "Merci de saisir une date valide(yyyy-MM-dd)." );
            }
        } 
    }
    
    
    /**
     * Méthode chargée de valider les champs des propositions d'une question
     * 
     * @param request - Une requête HTTP qui contient la demande du client
     * @param question - Un bean Question
     * @param reponses - Liste des beans reponse
     * @param utilisateurEmail - L'email d'un bean Utilisateur
     * 
     * @see PropositionDao#listerQuestionId(Question)
     * @see #setErreur(String, String)
     * @see ReponseForm
     */
	private void validationQuestion(HttpServletRequest request, Question question, Integer categorieId, List<Reponse> reponses, String utilisateurEmail) {
		
		boolean obligatoire = ( question.getObligatoire() == 0 ) ? false : true;
		
		// Obtenir les propositions d'une question
        List<Proposition> propositions = propositionDao.listerQuestionId( question ); 
        String questionName = Long.toString( question.getId() );
        int questionType = question.getType();
        boolean erreurExisteDeja = false;
        boolean erreurChampVideExisteDeja = false;
        boolean check = true;
    	boolean allNull = true;
    	
        String[] reponsesValeurs = (request.getParameterValues( questionName ) != null) ? request.getParameterValues( questionName ) : new String[0];
    	/* String[] getParameterValues(String name)
    	 * Returns an array of String objects containing all of the values the given request parameter has,
    	 * or null if the parameter does not exist. 
    	 * If the parameter has a single value, the array has a length of 1.
    	 * 
    	 * Parameters : name - a String containing the name of the parameter whose value is requested.
    	 * Returns : an array of String objects containing the parameter's values.
    	 * 
    	 * Source : JavaDoc
    	 */
        for(int i = 0 ; i < reponsesValeurs.length ; i++)
        {
            //String.trim() - Supprimer les espaces sur les côtés droit et gauche de la chaîne (début et fin de la chaîne).
            if ( reponsesValeurs[i] == null || reponsesValeurs[i].trim().length() == 0 ) {
            	reponsesValeurs[i] = null;
            	check = false;
            }
            else
            {
            	allNull = false;
            }
        }    

        
        if (questionType > 3)
        {
        	if( (propositions.size() != reponsesValeurs.length) || (check == false) || (reponsesValeurs.length == 0) )
        	{
        		if( obligatoire ) {
        			setErreur( questionName , "Attention ! ceci est une question obligatoire." );
        			erreurChampVideExisteDeja = true;
        		}
        	}
        	
        	for(int i = 0 ; (i < reponsesValeurs.length) ; i++)
        	{
        		Reponse nouvelleReponse = new Reponse();
        		try // Lorsqu'une ligne de code lève une exception, l'instruction dans le bloc try est interrompue et le programme se rend dans le bloc catch correspondant à l'exception levée.
        		{ 
        			switch( questionType )
        			{
        				case 4 : /* Si le type de question == type champ textuel */
        				break;
        				case 5 : /* Si le type de question == type champ date */ validationDate( reponsesValeurs[i] );
        				break;
        				case 6 : /* Si le type de question == type champ email */ validationEmail( reponsesValeurs[i] );
        				break;
        				case 7 : /* Si le type de question == type de champ téléphone */ validationTel( reponsesValeurs[i] );
        			}
        		} 
        		catch ( FormValidationException e ) //Le paramètre de la clause catch - permet de connaître le type d'exception qui doit être capturé.
        		{ 
        			//L'exception étant capturée, l'instruction du bloc catch s'exécute.
        			String message = (erreurChampVideExisteDeja == false) ? e.getMessage() : "Attention ! ceci est une question obligatoire. <br />" + e.getMessage();     			
        			if(erreurExisteDeja == false)
        				setErreur( questionName , message );
        			erreurExisteDeja = true;
        		}
        		
				nouvelleReponse.setPropositionId( propositions.get(i).getId() );
				nouvelleReponse.setUtilisateurEmail( utilisateurEmail );
				nouvelleReponse.setValeurReponse( reponsesValeurs[i] );
				nouvelleReponse.setQuestionId( question.getId() );
				nouvelleReponse.setCategorieId( (long)categorieId );
				reponses.add( nouvelleReponse );


        	}
        }
        else // Si le type de question== type de champ radio, checkbox ou select
        {
        	if( (reponsesValeurs.length == 0) || (allNull == true) )
        	{
        		if( obligatoire ) {
        			setErreur( questionName , "Attention ! ceci est une question obligatoire." );
        			erreurChampVideExisteDeja = true;
        		}
        	}
        	else
        	{
            	for(int i = 0 ; (i < reponsesValeurs.length) ; i++)
            	{
            		Reponse nouvelleReponse = new Reponse();
            		for (int j = 0 ; j < propositions.size() ; j++) {
            			if( reponsesValeurs[i].equals( propositions.get(j).getLabel() ) )
            			{
            				nouvelleReponse.setPropositionId( propositions.get(j).getId() );
            				nouvelleReponse.setUtilisateurEmail( utilisateurEmail );
            				nouvelleReponse.setValeurReponse( reponsesValeurs[i] );
            				nouvelleReponse.setQuestionId( question.getId() );
            				nouvelleReponse.setCategorieId( (long)categorieId );
            				reponses.add( nouvelleReponse );
            			}//fin if
            		}//fin for(j)	
        	    }//fin for(i)
            }//fin du "else" du if( (reponsesValeurs.length == 0) || (allNull == true) )
          }//fin du "else" du if (questionType > 3)
	}//fin de la fonction 	
	
	
	/**
     * Méthode chargée de traiter la validation des questions dans une catégorie
     * 
     * @param request - Une requête HTTP qui contient la demande du client
     * @param reponses - La liste des réponses
     * @param categorieId - ID d'un bean Catgorie
     * @param utilisateurEmail - Email de l'utilisateur qui répond à la catégorie
     * 
     * @see #validationQuestion(HttpServletRequest, Question, Integer, List, String)
     * @see CategorieDao#trouver(Integer)
     * @see QuestionDao#lister(Categorie)
     * @see ReponseForm 
     */
    public void traiterQuestionsReponses( HttpServletRequest request, List<Reponse> reponses, Integer categorieId, String utilisateurEmail) {
    	
    	Categorie categorie= categorieDao.trouver(categorieId);
    	
        // Retrouver les questions de cette catégorie
        List<Question> question = questionDao.lister(categorie);
               		
        for(Question q : question) {
        	validationQuestion(request, q, categorieId, reponses, utilisateurEmail);
       }
    }
    
	/**
     * Méthode qui fait la mise à jour de la liste des réponses dans la base de données
     * 
     * @param request - Une requête HTTP qui contient la demande du client
     * @param categorieId - ID d'un bean Categorie
     * @param utilisateur - un bean Utilisateur
     * 
     * @return La Liste des réponses
     * 
     * @see ReponseForm 
     * @see ReponseDao#enregistrementReponses(List, Categorie, Utilisateur)
     * @see CategorieDao#trouver(Integer)
     * @see #traiterQuestionsReponses(HttpServletRequest, List, Integer, String)
     */
	public List<Reponse> enregistrementReponses(HttpServletRequest request, Integer categorieId, Utilisateur utilisateur) {
		List<Reponse> reponses = new ArrayList<Reponse>();
		
        try // Lorsqu'une ligne de code lève une exception, l'instruction dans le bloc try est interrompue et le programme se rend dans le bloc catch correspondant à l'exception levée.
        {
        	traiterQuestionsReponses(request, reponses, categorieId, utilisateur.getEmail() );
			Categorie categorie= categorieDao.trouver(categorieId);
            
            if ( erreurs.isEmpty() ) 
            {
                reponseDao.enregistrementReponses(reponses, categorie, utilisateur);
                resultat = "Vos réponses ont été enregistrées avec succès dans la base de données du site.";
            } 
            else 
            {
                resultat = "Échec : Il nous semble que vous n'avez pas répondu à toutes les questions ou que certaines de vos réponses sont incorrectes.";
            }
        } 
        catch ( DAOException e )//Le paramètre de la clause catch - permet de connaître le type d'exception qui doit être capturé.
        {
            resultat = "Échec : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
            e.printStackTrace();
        }

        return reponses; 
	}
	
	/**
	 * Méthode qui calcul le score total d'une catégorie
	 * 
	 * @param categorie - Une catégorie
	 * 
	 * @return Le score total
	 * 
	 * @see PropositionDao#scoreTotalQMultiple(Question)
	 * @see PropositionDao#scoreTotalQUnique(Question)
	 * @see QuestionDao#lister(Categorie)
	 * 
	 * @author Huihui Huang
	 */
	public int calculScoreTotal (Categorie categorie) {
		int total= 0;
		
		// 1. Recherche des questions de la catégorie
		List<Question> questions= questionDao.lister(categorie);
		
		// 2. Parcourir les questions trouver
		for (Question question: questions) {
			// Si type de question = Radio ou Select
			if (question.getType().equals(1) || question.getType().equals(3)) {
				total += propositionDao.scoreTotalQUnique(question);
			// Sinon les autres types de questions
			} else {
				total += propositionDao.scoreTotalQMultiple(question);
			}
		}
		return total;
	}
}