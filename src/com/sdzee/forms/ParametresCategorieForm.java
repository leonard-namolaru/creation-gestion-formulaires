package com.sdzee.forms;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.sdzee.beans.Categorie;
import com.sdzee.beans.Proposition;
import com.sdzee.beans.Question;

import com.sdzee.dao.DAOException;
import com.sdzee.dao.PropositionDao;
import com.sdzee.dao.QuestionDao;
import com.sdzee.dao.CategorieDao;

/**
 * <b>La classe ParametresCategorieForm</b>: <br>
 * <ul>
 * 	<li>Traite les champs pour pourvoir crée ou faire la mise à jour d'une catégorie avec ces questions et propositions</li>
 * 	<li>Création d'une catégorie via l'espace de l'administrateur</li>
 * 	<li>Mise à jour d'une catégorie via l'espace de l'administrateur</li>
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
public final class ParametresCategorieForm {
	/* ************************ ATTRIBUTS *********************** */
    private static final int CHAINE_LONGUEUR_MINIMALE 		= 1;  // Longueur minimale : titre
    private static final int DESC_LONGUEUR_MINIMALE 		= 1;  // Longueur minimale : description
    
    private static final String CHAMP_TITRE 				= "titre";
    private static final String CHAMP_DESC  				= "description";
    
    private static final String CHAMP_QUESTION_TEXTE 		= "question_texte";
    private static final String CHAMP_QUESTION_OBLIGATOIRE  = "question_obligatoire";
    private static final String CHAMP_QUESTION_TYPE 		= "question_type";
    
    private static final String CHAMP_PROPOSITION_TEXTE 	= "question_proposition";
    private static final String CHAMP_PROPOSITION_SCORE	 	= "question_proposition_score";
    
    private static final String PAS_OBLIGATOIRE 			= "0";
            
    
    /* Les collections de type Map<K,V> stockent leurs contenus sous la forme clé-valeur
     * Le langage propose trois implémentations de bases
     * HashMap<K,V> : implémentation utilisant une table de hachage pour stocker ses éléments, mais cet objet n'est pas thread-safe 
     * */
    private Map<String, String> erreurs = new HashMap<String, String>();
    private CategorieDao categorieDao;
    private QuestionDao questionDao;
    private PropositionDao propositionDao;

    private String resultat;  //La chaîne resultat - contenant le statut final de la validation des champs


	/* ************************ CONSTRUCTEURS *********************** */
    
    /**
     * Constructeur
     * 
     * @param categorieDao - CategorieDao
     * @param questionDao - QuestionDao
     * @param propositionDao - PropositionDao
     * 
     * @see ParametresCategorieForm
     */
    public ParametresCategorieForm( CategorieDao categorieDao, QuestionDao questionDao, PropositionDao propositionDao ) {
        this.categorieDao = categorieDao;
        this.questionDao = questionDao;
        this.propositionDao = propositionDao;
    }
    
	/* ************************ METHODES **************************** */
        
   
    /**
     * Getters Erreurs
     * 
     * @return L'erreur
     * 
     * @see ParametresCategorieForm
     */ 
    public Map<String, String> getErreurs() {
        return erreurs;
    }

    /**
     * Getters résultat
     * 
     * @return Le resultat
     * 
     * @see ParametresCategorieForm
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
     * @see ParametresCategorieForm
     * @see #erreurs
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        
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
     * @see ParametresCategorieForm
     * @see #erreurs
     */
    public void setErreur( String champ, String message ) {
    	
    	/* put(K key, V value)
    	 * Ajoute la clé et la valeur dans la collection en retournant la valeur insérée. 
    	 * Si la clé existe déjà, sa valeur sera écrasée par celle passée en paramètre de la méthode. */
        erreurs.put( champ, message );
    }
    
    
    /**
     * Méthode chargée de valider un champ textuel
     * 
     * @param chaine - La chaine saisi
     * @param longeurChaine - La longueur de la chaine
     * 
     * @see ParametresCategorieForm
     * 
     * @throws FormValidationException Si erreur du champ textuel
     */
    public void validationChampTexte( String chaine , int longeurChaine) 
      throws FormValidationException {//throws : ce mot clé permet de signaler qu'un morceau de code, une méthode, une classe… est potentiellement dangereux. Il est suivi du nom de la classe qui va gérer l'exception.
    	
    	if ( chaine != null ) 
    	{
            if ( chaine.length() < longeurChaine ) 
               throw new FormValidationException( "Ce champ doit contenir au moins " + longeurChaine + " caractères." );                
        } 
    	else //chaine == null
    	{
        	//throw : permet de lever une exception manuellement en instanciant un objet de type Exception (ou un objet hérité).
            throw new FormValidationException( "Ce champ doit contenir au moins " + longeurChaine + " caractères." );                
        }
    }
    
        
    /**
     * Méthode qui permet la validation du titre et initialisation de la propriété titre du bean
     * 
     * @param titre - Le titre saisi
     * @param categorie - Un bean Categorie
     * 
     * @see Categorie
     * @see ParametresCategorieForm
     */
    public void traiterTitre( String titre, Categorie categorie ) {
        
    	try {
    		validationChampTexte( titre , CHAINE_LONGUEUR_MINIMALE ); // Lorsqu'une ligne de code lève une exception, l'instruction dans le bloc try est interrompue et le programme se rend dans le bloc catch correspondant à l'exception levée.
        } catch ( FormValidationException e ) { //Le paramètre de la clause catch - permet de connaître le type d'exception qui doit être capturé.
        	
        	/* private void setErreur( String champ, String message )
        	 * Ajoute un message correspondant au champ spécifié à la map des erreurs. */
            setErreur( CHAMP_TITRE, e.getMessage() );//L'exception étant capturée, l'instruction du bloc catch s'exécute.
        }
        categorie.setTitre( titre );//initialisation de la propriété titre du bean categorie
    }
    
    /**
     * Méthode qui permet de traiter le champ "description" et initialisation de la propriété description du bean
     * 
     * @param description - La description saisi
     * @param categorie - Un bean Categorie
     * 
     * @see ParametresCategorieForm
     * @see Categorie
     */
    public void traiterDescription( String description, Categorie categorie ) {
        
    	try {
    		validationChampTexte( description , DESC_LONGUEUR_MINIMALE ); // Lorsqu'une ligne de code lève une exception, l'instruction dans le bloc try est interrompue et le programme se rend dans le bloc catch correspondant à l'exception levée.
        } catch ( FormValidationException e ) { //Le paramètre de la clause catch - permet de connaître le type d'exception qui doit être capturé.
        	
        	/* private void setErreur( String champ, String message )
        	 * Ajoute un message correspondant au champ spécifié à la map des erreurs. */
            setErreur( CHAMP_DESC, e.getMessage() );//L'exception étant capturée, l'instruction du bloc catch s'exécute.
        }
        categorie.setDescription( description );//initialisation de la propriété titre du bean categorie
    }
    
    /**
     * Méthode qui permet de traiter les Questions
     * 
     * @param request - La requête HTTP qui contient la demande du client
     * @param categorie - Un bean Categorie
     * 
     * @see ParametresCategorieForm
     * @see #getValeurChamp(HttpServletRequest, String)
     * @see ParametresCategorieForm
     * @see #setErreur(String, String)
     * @see Question
     * @see Proposition
     * @see Categorie
     */
    public void traiterQuestionsPropositions( HttpServletRequest request, Categorie categorie ) {
    	int numeroQuestion = 1 , numeroGroupe = 1, numeroProposition;
    	Vector<Question> questions = new Vector<Question>();
    	String questionLabel, questionLabelValeurChamp;
    	String questionObligatoire, questionObligatoireValeurChamp;
    	String questionType, questionTypeValeurChamp;
    	String propositionTexte, propositionTexteValeurChamp;
    	String propositionScore, propositionScoreValeurChamp;

    	
		questionType = CHAMP_QUESTION_TYPE + "_q" + numeroQuestion + "_g" + numeroGroupe;
		questionTypeValeurChamp = getValeurChamp(request,  questionType);

    	
    	while(questionTypeValeurChamp != null && (! questionTypeValeurChamp.equals("titre")) ) //Chaque itération de la boucle - un groupe de questions  
    	{    	    	
    		while(questionTypeValeurChamp != null && (! questionTypeValeurChamp.equals("titre")) ) //Chaque itération de la boucle - tous les champs d'une question
    		{ 
    			Question nouvelleQuestion = new Question();
    			
    	    	questionLabel = CHAMP_QUESTION_TEXTE + "_q" + numeroQuestion + "_g" + numeroGroupe;
    	    	questionLabelValeurChamp = getValeurChamp(request,  questionLabel);

    			questionObligatoire = CHAMP_QUESTION_OBLIGATOIRE + "_q" + numeroQuestion + "_g" + numeroGroupe;
    			questionObligatoireValeurChamp = getValeurChamp(request,  questionObligatoire);    	
    	
    			try {
    				validationChampTexte( questionLabelValeurChamp , CHAINE_LONGUEUR_MINIMALE ); // Lorsqu'une ligne de code lève une exception, l'instruction dans le bloc try est interrompue et le programme se rend dans le bloc catch correspondant à l'exception levée.
    			} catch ( FormValidationException e ) { //Le paramètre de la clause catch - permet de connaître le type d'exception qui doit être capturé.
        	
    				/* private void setErreur( String champ, String message )
    				 * Ajoute un message correspondant au champ spécifié à la map des erreurs. */
    				setErreur( questionLabel, e.getMessage() );//L'exception étant capturée, l'instruction du bloc catch s'exécute.
    			}

    			nouvelleQuestion.setNumeroQuestion(numeroQuestion);
    			nouvelleQuestion.setLabel(questionLabelValeurChamp);
    			nouvelleQuestion.setQuestionGroupe(numeroGroupe);
    			
    			//Chaque type de question aura un numéro spécifique. Par exemple - le numéro 1 dans ce champ = boutons radio
    			switch ( questionTypeValeurChamp )//initialisation de la propriete type du bean
    			{
    				case "radio" : nouvelleQuestion.setType( 1 );
    							   break;
    				case "cases" : nouvelleQuestion.setType( 2 );
    							   break;
    			    case "liste" : nouvelleQuestion.setType( 3 );
						           break;
    			    case "texte" : nouvelleQuestion.setType( 4 );
    							   break;
    				case "date"  : nouvelleQuestion.setType( 5 );
								   break;
    				case "email" : nouvelleQuestion.setType( 6 );
						           break;
    				case "tel"   : nouvelleQuestion.setType( 7 );
			               		   break;
			        default      : setErreur( questionType, "Type de question inconnu." );
    			}    				    				

    	
    			if (questionObligatoireValeurChamp != null)
    			{
    				if ( PAS_OBLIGATOIRE.equals( questionObligatoireValeurChamp ) )
    					nouvelleQuestion.setObligatoire( 0 );//initialisation de la propriete obligatoire du bean
    				else
    					nouvelleQuestion.setObligatoire( 1 );//initialisation de la propriete obligatoire du bean  	
    			}
    			else
    			{
    				setErreur( questionObligatoire, "Ce champ est obligatoire." );
    			}

    			questions.add( nouvelleQuestion );
    			
    	    	Vector<Proposition> propositions = new Vector<Proposition>();
    			numeroProposition = 1;
    			propositionTexte = CHAMP_PROPOSITION_TEXTE + "_q" + numeroQuestion + "_g" + numeroGroupe + "_p" + numeroProposition;
    			propositionTexteValeurChamp = getValeurChamp(request,  propositionTexte);

    			while(propositionTexteValeurChamp != null) /* Les propositions */
    			{
        			Proposition nouvelleProposition = new Proposition();

        			propositionScore = CHAMP_PROPOSITION_SCORE + "_q" + numeroQuestion + "_g" + numeroGroupe + "_p" + numeroProposition;
        			propositionScoreValeurChamp = getValeurChamp(request,  propositionScore);
        			        			
        			try {
        				validationChampTexte( propositionTexteValeurChamp , CHAINE_LONGUEUR_MINIMALE ); // Lorsqu'une ligne de code lève une exception, l'instruction dans le bloc try est interrompue et le programme se rend dans le bloc catch correspondant à l'exception levée.
        			} catch ( FormValidationException e ) { //Le paramètre de la clause catch - permet de connaître le type d'exception qui doit être capturé.
            	
        				/* private void setErreur( String champ, String message )
        				 * Ajoute un message correspondant au champ spécifié à la map des erreurs. */
        				setErreur( propositionTexte, e.getMessage() );//L'exception étant capturée, l'instruction du bloc catch s'exécute.
        			}
        			
        			try {
        				validationChampTexte( propositionScoreValeurChamp , 1 ); // Lorsqu'une ligne de code lève une exception, l'instruction dans le bloc try est interrompue et le programme se rend dans le bloc catch correspondant à l'exception levée.
            			nouvelleProposition.setScore( Integer.parseInt(propositionScoreValeurChamp)  );
        			} catch ( FormValidationException e ) { //Le paramètre de la clause catch - permet de connaître le type d'exception qui doit être capturé.
            	
        				/* private void setErreur( String champ, String message )
        				 * Ajoute un message correspondant au champ spécifié à la map des erreurs. */
        				setErreur( propositionScore, e.getMessage() );//L'exception étant capturée, l'instruction du bloc catch s'exécute.
        			}
        			
        			nouvelleProposition.setLabel(propositionTexteValeurChamp);       			
        			propositions.add(nouvelleProposition);
        			
        			numeroProposition++;
        			propositionTexte = CHAMP_PROPOSITION_TEXTE + "_q" + numeroQuestion + "_g" + numeroGroupe + "_p" + numeroProposition;
        			propositionTexteValeurChamp = getValeurChamp(request,  propositionTexte);
    			}

    	        nouvelleQuestion.setPropositions(propositions);

    			numeroQuestion++;
    			questionType = CHAMP_QUESTION_TYPE + "_q" + numeroQuestion + "_g" + numeroGroupe;
    			questionTypeValeurChamp = getValeurChamp(request,  questionType);    			
    		}
    	
    		numeroGroupe++;
    		questionType = CHAMP_QUESTION_TYPE + "_q" + numeroQuestion + "_g" + numeroGroupe;
    		questionTypeValeurChamp = getValeurChamp(request,  questionType);
    	}
    	
        categorie.setNbGroupes(numeroGroupe - 1);
        categorie.setQuestions(questions);
    }
    

    /**
     * Méthode qui enregistre les paramètres des catégorie
     * 
     * @param request - La requête HTTP qui contient la demande du client
     * 
     * @return Un bean Categorie
     * 
     * @see CategorieDao#creer(Categorie)
     * @see ParametresCategorieForm
     * @see PropositionDao#creer(Categorie)
     * @see QuestionDao#creer(Categorie)
     * @see #traiterDescription(String, Categorie)
     * @see #traiterQuestionsPropositions(HttpServletRequest, Categorie)
     * @see #traiterTitre(String, Categorie)
     * @see #getValeurChamp(HttpServletRequest, String)
     */
    public Categorie enregistrementParametresCategorie( HttpServletRequest request ) {
        
    	/* La méthode  getValeurChamp() - vérifier si le contenu d'un champ est vide ou non, 
    	 * getValeurChamp(HttpServletRequest request, String nomChamp)
    	 * suite du code : vérifier si les chaînes sont à null ;   */
    	String titre = getValeurChamp( request, CHAMP_TITRE );
    	String description = getValeurChamp( request, CHAMP_DESC );
    	
        Categorie categorie = new Categorie();
        
        try // Lorsqu'une ligne de code lève une exception, l'instruction dans le bloc try est interrompue et le programme se rend dans le bloc catch correspondant à l'exception levée.
        {
            traiterTitre( titre , categorie );
            traiterDescription( description , categorie );
            traiterQuestionsPropositions(request, categorie);

            if ( erreurs.isEmpty() ) 
            {
                categorieDao.creer( categorie );
                questionDao.creer( categorie );
                propositionDao.creer( categorie );
                resultat = "<article class=\"succes\"> La nouvelle catégorie a été ajoutée avec succès à la base de données. Vous pouvez maintenant créer une autre nouvelle catégorie ou revenir à la page d'accueil de l'espace d'administration.</article>";
            } 
            else 
            {
                resultat = "<article class=\"erreur\"> La nouvelle catégorie n'a pas pu être ajoutée à la base de données. Veuillez réessayer. Il est très important que vous remplissiez tous les champs du formulaire. Il est également important de s'assurer que chaque groupe de questions contient au moins une question. Le score de chaque proposition doit être un chiffre entier.</article>";
            }
        } 
        catch ( DAOException e )//Le paramètre de la clause catch - permet de connaître le type d'exception qui doit être capturé.
        {
            resultat = "<article class=\"erreur\"> Échec  : une erreur imprévue est survenue, cela semble être un problème de communication avec la base de données du site. Merci de réessayer dans quelques instants.</article>";
            e.printStackTrace();
        }

        return categorie; // La méthode enregistrementParametresCategorie() -  retourne un bean Categorie
    }


	/**
	 * Méthode qui enregistre les paramètres des catégorie
	 * 
	 * @param request - La requête HTTP qui contient la demande du client
	 * @param categorie - Un bean Categorie
	 * 
	 * @return Un bean Categorie
	 * 
	 * @see CategorieDao#creer(Categorie)
	 * @see ParametresCategorieForm
	 * @see PropositionDao#creer(Categorie)
	 * @see QuestionDao#creer(Categorie)
	 * @see #traiterDescription(String, Categorie)
	 * @see #traiterQuestionsPropositions(HttpServletRequest, Categorie)
	 * @see #traiterTitre(String, Categorie)
	 * @see #getValeurChamp(HttpServletRequest, String)
	 */
	public Categorie updateParametresCategorie( HttpServletRequest request , Categorie categorie) {
	    
		/* La méthode  getValeurChamp() - vérifier si le contenu d'un champ est vide ou non, 
		 * getValeurChamp(HttpServletRequest request, String nomChamp)
		 * suite du code : vérifier si les chaînes sont à null ;   */
		String titre = getValeurChamp( request, CHAMP_TITRE );
		String description = getValeurChamp( request, CHAMP_DESC );
		    
	    try // Lorsqu'une ligne de code lève une exception, l'instruction dans le bloc try est interrompue et le programme se rend dans le bloc catch correspondant à l'exception levée.
	    {
	        traiterTitre( titre , categorie );
	        traiterDescription( description , categorie );
	        traiterQuestionsPropositions(request, categorie);
	
	        
	        if ( erreurs.isEmpty() ) 
	        {
	            categorieDao.update(categorie);
	            questionDao.update(request, categorie.getQuestions(), categorie);
	            propositionDao.update(request, categorie);
	            resultat = "<article class=\"succes\"> Succès de la modification.</article>";
	        } 
	        else 
	        {
	            resultat = "<article class=\"erreur\"> Échec de la modification. Veuillez réessayer. Il est très important que vous remplissiez tous les champs du formulaire. Il est également important de s'assurer que chaque groupe de questions contient au moins une question. Le score de chaque proposition doit être un chiffre entier.</article>";
	        }
	    } 
	    catch ( DAOException e )//Le paramètre de la clause catch - permet de connaître le type d'exception qui doit être capturé.
	    {
            resultat = "<article class=\"erreur\"> Échec  : une erreur imprévue est survenue, cela semble être un problème de communication avec la base de données du site. Merci de réessayer dans quelques instants.</article>";
	        e.printStackTrace();
	    }
	
	    return categorie; //  retourne un bean Categorie
	}
}
