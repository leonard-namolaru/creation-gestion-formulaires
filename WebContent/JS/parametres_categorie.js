/**
* parametres_categorie.js
* @author  Leonard Namolaru
* Travail_effectué : Tout le code qui apparaît dans ce fichier ( sauf indication contraire à côté d'un extrait de code )
* Projet : Sujet L2N - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes
*   
*/

var nbGroupes; /* Nombre de groupes de questions. */
var nbQuestions; /* Nombre de questions. */
var nbPropositions = new Array(); /* Nombre de questions pour chaque question. nbPropositions.length = nbQuestions */

function init(nGroupes, nQuestions){
	nbGroupes = nGroupes;
	nbQuestions = nQuestions;
}

function initArray(nQuestion, nPropositions){
	
	nbPropositions[nQuestion - 1] = nPropositions;
}

function supprimerProposition(numeroGroupe, numeroQuestion, numeroPropositionASupprimer)
{
	var question = document.getElementById("question_" + numeroQuestion );
	var parametresQuestion = question.childNodes;
	
  	var k = 9 , numeroProposition = 0, check = false, propositionASupprimer;
  	while( (k + 3) <  parametresQuestion.length)//Cette question a des propositions
  	{
	  	numeroProposition++;

  		if(numeroProposition == numeroPropositionASupprimer)
  		{
  			propositionASupprimer = new Array(parametresQuestion[k], parametresQuestion[k + 1], parametresQuestion[k + 2], parametresQuestion[k + 3]);
  			nbPropositions[numeroQuestion - 1]--;
  			check = true;
  		}
  		
  		if(check == true)
  		{
		  	parametresQuestion[k].innerHTML = '<span class="supprimer_proposition" onclick="supprimerProposition(' + numeroGroupe + ' , ' + numeroQuestion + ' , ' + (numeroProposition - 1) + ')"> [ X Supprimer la proposition ] </span>';

  		  	parametresQuestion[k + 1].name = "question_proposition_q" + numeroQuestion + "_g" + numeroGroupe + "_p" + (numeroProposition - 1);
  		  	parametresQuestion[k + 1].id   = parametresQuestion[k + 1].name;
  		  	
  		  	parametresQuestion[k + 2].name = "question_proposition_score_q" + numeroQuestion + "_g" + numeroGroupe + "_p" + (numeroProposition - 1);
  		  	parametresQuestion[k + 2].id   = parametresQuestion[k + 2].name;
  		  	
  		  	//parametresQuestion[k + 3] = <br />

  		}
	  	k+=4;
  	}
  	
  	for(var i = 0 ; i < propositionASupprimer.length ; i++)
  		question.removeChild(propositionASupprimer[i]);
}

function supprimerQuestion(numeroGroupe, numeroQuestion)
{			
		var check = false;
		var questionASupprimer;
		
		for(var i = numeroGroupe ; i < nbGroupes ; i++)
		{
			var groupe = document.getElementById("groupe_de_question_" + i );
			var questions = groupe.childNodes;
		  
			/* Chaque ensemble de champs d'un groupe de questions a 2 "fils" qui ne sont pas des questions :
			 * le titre, le bouton pour ajouter une question
			 */ 
		  for (var j = 2; j < questions.length ; j++) {
			  	var numeroQuestionI = questions[j].id.substring(9);
			  	
			  	if(numeroQuestionI == numeroQuestion)
			  	{
			  		questionASupprimer = questions[j] ;
			  		nbPropositions[numeroQuestion - 1] = 0;
			  		check = true;
			  	}

			  	if(check == true)
			  	{
			  		var numeroQuestionNouveau = numeroQuestionI - 1;
			  		questions[j].id = "question_" + (numeroQuestionNouveau);//<fieldset id="question_4"> </fieldset>
				  	
			  		var question = document.getElementById("question_" + (numeroQuestionNouveau) );
				  	var parametresQuestion = question.childNodes;
				  	
				  	parametresQuestion[0].innerHTML = 'Question n°' + numeroQuestionNouveau + '<span class="supprimer_q" onclick="supprimerQuestion(' + i + ',' +  numeroQuestionNouveau + ');"> [ X ] </span>';
				  				  	
				  	/* élément <select> */
				  	parametresQuestion[1].name = "question_type_q" + numeroQuestionNouveau + "_g" + i;
				  	parametresQuestion[1].id = parametresQuestion[1].name;
				  	
					/*  <input type="text" id="question_texte_q1_g1" name="question_texte_q1_g1" placeholder="La question présentée aux utilisateurs" /> */
				  	parametresQuestion[2].name = "question_texte_q" + numeroQuestionNouveau + "_g" + i;
				  	parametresQuestion[2].id = parametresQuestion[2].name;

					/* <label for="champ_obligatoire_q3_g2">Oui</label> */
				  	parametresQuestion[4].htmlFor = "champ_obligatoire_q" + numeroQuestionNouveau + "_g" + i;

					/* <input type="radio" id="champ_obligatoire_q3_g2" name="question_obligatoire_q3_g2" value="1" /> */
				  	parametresQuestion[5].name = "question_obligatoire_q" + numeroQuestionNouveau + "_g" + i;
				  	parametresQuestion[5].id   = "champ_obligatoire_q" + numeroQuestionNouveau + "_g" + i;

				  	/*  <label for="champ_pas_obligatoire_q3_g2">Non</label> */
				  	parametresQuestion[6].htmlFor = "champ_pas_obligatoire_q" + numeroQuestionNouveau + "_g" + i;

					/*  <input type="radio" id="champ_pas_obligatoire_q3_g2" name="question_obligatoire_q3_g2" value="0"  />  */
				  	parametresQuestion[7].name = "question_obligatoire_q" + numeroQuestionNouveau + "_g" + i;
				  	parametresQuestion[7].id   = "champ_pas_obligatoire_q" + numeroQuestionNouveau + "_g" + i;
					
				  	/* <input type="button" id="question_3_ajouteProposition" value=" + Proposition" onclick="ajouteProposition();" />  */			  	
				  	parametresQuestion[8].innerHTML = "Les champs qui seront affichés aux utilisateurs pour répondre à cette question <br />" ;
				  	parametresQuestion[8].innerHTML += '<input id="question_' + numeroQuestionNouveau + '_ajouteProposition" value=" + Proposition" type="button" onclick="ajouteProposition(' + i + ' , ' + numeroQuestionNouveau + ');" /> \n' ;           				                            				                                                

				  	var k = 9 , numeroProposition = 0;
				  	while( (k + 3) <  parametresQuestion.length)//Cette question a des propositions
				  	{
					  	numeroProposition++;
					  	
					  	parametresQuestion[k].innerHTML = '<span class="supprimer_proposition" onclick="supprimerProposition(' + i + ' , ' + numeroQuestionNouveau + ' , ' + numeroProposition + ')"> [ X Supprimer la proposition ] </span>';

					  	parametresQuestion[k + 1].name = "question_proposition_q" + numeroQuestionNouveau + "_g" + i + "_p" + numeroProposition;
					  	parametresQuestion[k + 1].id   = parametresQuestion[k + 1].name;
					  	
					  	parametresQuestion[k + 2].name = "question_proposition_score_q" + numeroQuestionNouveau + "_g" + i + "_p" + numeroProposition;
					  	parametresQuestion[k + 2].id   = parametresQuestion[k + 2].name;

					  	k+=4;
				  	}
				  	
				  	nbPropositions[numeroQuestionI - 1] = 0;
				  	nbPropositions[numeroQuestionNouveau - 1] = numeroProposition;

			  	}// fin du if(check == true)			  	
		  }//fin du for(j)		  
		}//fin du for(i)		  
		document.getElementById("groupe_de_question_" + numeroGroupe ).removeChild(questionASupprimer);
		nbQuestions--;		
}


function supprimerGroupeDeQuestions(numeroGroupe)
{
	var groupeASupprimer = document.getElementById("groupe_de_question_" + numeroGroupe);		
	var nombreQuestionsGroupeASupprimer = 0;
	var i;
	
	while (groupeASupprimer.firstChild) {
		nombreQuestionsGroupeASupprimer++;
		groupeASupprimer.removeChild(groupeASupprimer.firstChild);
	}
	
	/* Chaque ensemble de champs d'un groupe de questions a 2 "fils" qui ne sont pas des questions :
	 * le titre, le bouton pour ajouter une question
	 */ 
	nombreQuestionsGroupeASupprimer -= 2;
	document.getElementById("form_parametres-categorie").removeChild(groupeASupprimer);
	
	for(i = (numeroGroupe + 1) ; i < nbGroupes ; i++)
	{
		document.getElementById("groupe_de_question_" + i).id = "groupe_de_question_" + (i - 1);
		document.getElementById("groupe_de_question_" + i + "legend").id = "groupe_de_question_" + (i - 1) +"legend";
		document.getElementById("groupe_de_question_" + (i - 1) +"legend").innerHTML = 'Groupe de questions n°' + (i - 1) + '<span class="supprimer_groupe_de_questions" onclick="supprimerGroupeDeQuestions(' + (i - 1) + ')"> [ X ] </span>';
		document.getElementById("groupe_de_question_" + i + "_ajouteQuestion").parentNode.innerHTML = '<input type="button" id="groupe_de_question_' + (i - 1) + '_ajouteQuestion" value=" + Question" onclick="ajouteQuestion(' + (i - 1) + ');" /> \n'		
	
		var groupe = document.getElementById("groupe_de_question_" + (i - 1));
		
		if (groupe.hasChildNodes()) {
		  var questions = groupe.childNodes;
		  
			/* Chaque ensemble de champs d'un groupe de questions a 2 "fils" qui ne sont pas des questions :
			 * le titre, le bouton pour ajouter une question
			 */ 
		  for (var j = 2; j < questions.length; j++) {
			  	var numeroQuestionActuelle = questions[j].id.substring(9);
			  	var numeroQuestionNouveau = Math.abs(numeroQuestionActuelle - nombreQuestionsGroupeASupprimer)
			  	questions[j].id = "question_" + numeroQuestionNouveau; //<fieldset id="question_4"> </fieldset>
			  	
			  	var question = document.getElementById("question_" + numeroQuestionNouveau);
			  	var parametresQuestion = question.childNodes;
			  	
			  	parametresQuestion[0].innerHTML = 'Question n°' + numeroQuestionNouveau + '<span class="supprimer_q" onclick="supprimerQuestion(' + (i-1) + ',' +  numeroQuestionNouveau + ');"> [ X ] </span>';
			  	
			  	/* élément <select> */
			  	parametresQuestion[1].name = "question_type_q" + numeroQuestionNouveau + "_g" + (i - 1);
			  	parametresQuestion[1].id = parametresQuestion[1].name;
			  	
				/*  <input type="text" id="question_texte_q1_g1" name="question_texte_q1_g1" placeholder="La question présentée aux utilisateurs" /> */
			  	parametresQuestion[2].name = "question_texte_q" + numeroQuestionNouveau + "_g" + (i - 1);
			  	parametresQuestion[2].id = parametresQuestion[2].name;

				/* <label for="champ_obligatoire_q3_g2">Oui</label> */
			  	parametresQuestion[4].htmlFor = "champ_obligatoire_q" + numeroQuestionNouveau + "_g" + (i - 1);

				/* <input type="radio" id="champ_obligatoire_q3_g2" name="question_obligatoire_q3_g2" value="1" /> */
			  	parametresQuestion[5].name = "question_obligatoire_q" + numeroQuestionNouveau + "_g" + (i - 1);
			  	parametresQuestion[5].id   = "champ_obligatoire_q" + numeroQuestionNouveau + "_g" + (i - 1);

			  	/*  <label for="champ_pas_obligatoire_q3_g2">Non</label> */
			  	parametresQuestion[6].htmlFor = "champ_pas_obligatoire_q" + numeroQuestionNouveau + "_g" + (i - 1);

				/*  <input type="radio" id="champ_pas_obligatoire_q3_g2" name="question_obligatoire_q3_g2" value="0"  />  */
			  	parametresQuestion[7].name = "question_obligatoire_q" + numeroQuestionNouveau + "_g" + (i - 1);
			  	parametresQuestion[7].id   = "champ_pas_obligatoire_q" + numeroQuestionNouveau + "_g" + (i - 1);
							  	
			  	/* <input type="button" id="question_3_ajouteProposition" value=" + Proposition" onclick="ajouteProposition();" />  */			  	
			  	parametresQuestion[8].innerHTML = "Les champs qui seront affichés aux utilisateurs pour répondre à cette question <br />" ;
			  	parametresQuestion[8].innerHTML += '<input id="question_' + numeroQuestionNouveau + '_ajouteProposition" value=" + Proposition" type="button" onclick="ajouteProposition(' + (i - 1) + ' , ' + numeroQuestionNouveau + ');" /> \n' ;           				                            				                                                

			  	var k = 9 , numeroProposition = 0;
			  	while( (k + 3) <  parametresQuestion.length)//Cette question a des propositions
			  	{
				  	numeroProposition++;
				  	
				  	parametresQuestion[k].innerHTML = '<span class="supprimer_proposition" onclick="supprimerProposition(' + numeroGroupe + ' , ' + numeroQuestionNouveau + ' , ' + numeroProposition + ')"> [ X Supprimer la proposition ] </span>';

				  	parametresQuestion[k + 1].name = "question_proposition_q" + numeroQuestionNouveau + "_g" + (i - 1) + "_p" + numeroProposition;
				  	parametresQuestion[k + 1].id   = parametresQuestion[k + 1].name;
				  	
				  	parametresQuestion[k + 2].name = "question_proposition_score_q" + numeroQuestionNouveau + "_g" + (i - 1) + "_p" + numeroProposition;
				  	parametresQuestion[k + 2].id   = parametresQuestion[k + 2].name;

				  	k+=4;
			  	}			  	
			  	nbPropositions[numeroQuestionActuelle - 1] = 0;
			  	nbPropositions[numeroQuestionNouveau - 1] = numeroProposition;			  	
		  }
		}	
	}
	
	nbGroupes--;
	nbQuestions -= nombreQuestionsGroupeASupprimer;
	
	for(i = nbQuestions ; i < nbPropositions.length ; i++)
		nbPropositions[i] = 0;	
}

function ajouteProposition(numeroGroupe ,numeroQuestion)
{
	nbPropositions[numeroQuestion -1]++;
	
	var newSpan = document.createElement("span"); /* Nouvel élément <span> */
	newSpan.innerHTML = '<span class="supprimer_proposition" onclick="supprimerProposition(' + numeroGroupe + ' , ' + numeroQuestion + ' , ' + nbPropositions[numeroQuestion -1] + ')"> [ X Supprimer la proposition ] </span>';
	
	/*  <input type="text" id="question_proposition_q1_g1_p1" name="question_proposition_q1_g1_p1" placeholder="Le texte présenté aux utilisateurs" /> */
	var input1 = document.createElement("input"); /* Nouvel élément <input> */
	input1.setAttribute("type", "text");
	input1.setAttribute("id", "question_proposition_q" + numeroQuestion + "_g" + numeroGroupe + "_p" + nbPropositions[numeroQuestion - 1] );	
	input1.setAttribute("name", "question_proposition_q" + numeroQuestion + "_g" + numeroGroupe + "_p" + nbPropositions[numeroQuestion - 1] );	
	input1.setAttribute("placeholder", "Le texte présenté aux utilisateurs");	
	
	var input2 = document.createElement("input"); /* Nouvel élément <input> */
	input2.setAttribute("type", "text");
	input2.setAttribute("id", "question_proposition_score_q" + numeroQuestion + "_g" + numeroGroupe + "_p" + nbPropositions[numeroQuestion - 1] );	
	input2.setAttribute("name", "question_proposition_score_q" + numeroQuestion + "_g" + numeroGroupe + "_p" + nbPropositions[numeroQuestion - 1] );	
	input2.setAttribute("placeholder", "Score");	

	document.getElementById("question_" + numeroQuestion).appendChild(newSpan);
	document.getElementById("question_" + numeroQuestion).appendChild(input1);
	document.getElementById("question_" + numeroQuestion).appendChild(input2);
	
	document.getElementById("question_" + numeroQuestion).appendChild( document.createElement("br") );


}


function ajouteQuestion(numeroGroupe)
{
	
	/* <fieldset id="question_4"> </fieldset>  */
	var fieldset = document.createElement("fieldset"); /* Nouvel élément <fieldset> */
	var fieldsetId = "question_" + nbQuestions;
	fieldset.setAttribute("id", fieldsetId);	
	document.getElementById("groupe_de_question_" + numeroGroupe).appendChild(fieldset);
	
	/* <legend>Question </legend> */
	var legend = document.createElement("legend"); /* Nouvel élément <legend> */
	legend.innerHTML = 'Question n°' + nbQuestions + '<span class="supprimer_q" onclick="supprimerQuestion(' + numeroGroupe + ' , ' +  nbQuestions + ');"> [ X ] </span>';
	document.getElementById(fieldsetId).appendChild(legend);
	
	var select = document.createElement("select"); /* Nouvel élément <select> */
	select.setAttribute("id", "question_type_q" + nbQuestions + "_g" + numeroGroupe);	
	select.setAttribute("name", "question_type_q" + nbQuestions + "_g" + numeroGroupe);	
	
	select.innerHTML = '<option value="titre">--Type de question--</option> \n';
	select.innerHTML += '<optgroup label="Eléments d\'options"> \n';
	select.innerHTML += '<option value="radio">Boutons radio</option> \n';
	select.innerHTML += '<option value="cases">Cases à cocher</option> \n';
	select.innerHTML += '<option value="liste">Liste déroulante</option> \n';
	select.innerHTML += '</optgroup> \n';
	select.innerHTML += '<optgroup label="Chaine de caractères"> \n';
	select.innerHTML += '<option value="texte">Champ de texte</option> \n';
	select.innerHTML += '<option value="date">Date</option> \n';
	select.innerHTML += '<option value="email">Email</option> \n';
	select.innerHTML += '<option value="tel">Numéro de téléphone</option> \n';
	select.innerHTML += '</optgroup> \n';
	
	document.getElementById(fieldsetId).appendChild(select);
	
	/*  <input type="text" id="question_texte_q1_g1" name="question_texte_q1_g1" placeholder="La question présentée aux utilisateurs" /> */
	var input1 = document.createElement("input"); /* Nouvel élément <input> */
	input1.setAttribute("type", "text");
	input1.setAttribute("id", "question_texte_q" + nbQuestions + "_g" + numeroGroupe);	
	input1.setAttribute("name", "question_texte_q" + nbQuestions + "_g" + numeroGroupe);	
	input1.setAttribute("placeholder", "La question présentée aux utilisateurs");	
	document.getElementById(fieldsetId).appendChild(input1);

	/* <label class="label_champ_obligatoire">Champ obligatoire : </label> */
	var label_boutons_radio = document.createElement("label"); /* Nouvel élément <label> */
	label_boutons_radio.setAttribute("class", "label_champ_obligatoire");
	label_boutons_radio.innerHTML = "Champ obligatoire : ";
	
	/* <label for="champ_obligatoire_q3_g2">Oui</label> */
	var label_boutons_radio_oui = document.createElement("label"); /* Nouvel élément <label> */
	label_boutons_radio_oui.setAttribute("for", "champ_obligatoire_q" + nbQuestions + "_g" + numeroGroupe);
	label_boutons_radio_oui.innerHTML = "Oui";
	
	/* <input type="radio" id="champ_obligatoire_q3_g2" name="question_obligatoire_q3_g2" value="1" /> */
	var input2 = document.createElement("input"); /* Nouvel élément <input> */
	input2.setAttribute("type", "radio");
	input2.setAttribute("id", "champ_obligatoire_q" + nbQuestions + "_g" + numeroGroupe);	
	input2.setAttribute("name", "question_obligatoire_q" + nbQuestions + "_g" + numeroGroupe);	
	input2.setAttribute("value", "1");	
	
	/*  <label for="champ_pas_obligatoire_q3_g2">Non</label> */
	var label_boutons_radio_non = document.createElement("label"); /* Nouvel élément <label> */
	label_boutons_radio_non.setAttribute("for", "champ_pas_obligatoire_q" + nbQuestions + "_g" + numeroGroupe);
	label_boutons_radio_non.innerHTML = "Non";
	
	/*  <input type="radio" id="champ_pas_obligatoire_q3_g2" name="question_obligatoire_q3_g2" value="0"  />  */
	var input3 = document.createElement("input"); /* Nouvel élément <input> */
	input3.setAttribute("type", "radio");
	input3.setAttribute("id", "champ_pas_obligatoire_q" + nbQuestions + "_g" + numeroGroupe);	
	input3.setAttribute("name", "question_obligatoire_q" + nbQuestions + "_g" + numeroGroupe);	
	input3.setAttribute("value", "0");	

	document.getElementById(fieldsetId).appendChild(label_boutons_radio);
	document.getElementById(fieldsetId).appendChild(label_boutons_radio_oui);
	document.getElementById(fieldsetId).appendChild(input2);
	document.getElementById(fieldsetId).appendChild(label_boutons_radio_non);
	document.getElementById(fieldsetId).appendChild(input3);
	
	/* <input type="button" id="question_3_ajouteProposition" value=" + Proposition" onclick="ajouteProposition();" />  */
	var div = document.createElement("div"); /* Nouvel élément <div> */
	div.setAttribute("class", "titre_reponses");
	div.setAttribute("id", "titre_reponses_" + nbQuestions);
	div.innerHTML = "Les champs qui seront affichés aux utilisateurs pour répondre à cette question <br />" ;
	div.innerHTML += '<input id="question_' + nbQuestions + '_ajouteProposition" value=" + Proposition" type="button" onclick="ajouteProposition(' + numeroGroupe + ' , ' + nbQuestions + ');" /> \n' ;           				                            				                                                
	document.getElementById(fieldsetId).appendChild(div);
		
	var numeroQuestionNouveau = 0;
	
	nbPropositions[nbQuestions] = 0;
	
	for(var i = 1 ; i < nbGroupes ; i++)
	{
		var groupe = document.getElementById("groupe_de_question_" + i );
		var questions = groupe.childNodes;
	  
		/* Chaque ensemble de champs d'un groupe de questions a 2 "fils" qui ne sont pas des questions :
		 * le titre, le bouton pour ajouter une question
		 */ 
	  for (var j = 2; j < questions.length ; j++) {	  	
		  		numeroQuestionNouveau++;
		  		questions[j].id = "question_" + (numeroQuestionNouveau);//<fieldset id="question_4"> </fieldset>
			  	
		  		var question = document.getElementById("question_" + (numeroQuestionNouveau) );
			  	var parametresQuestion = question.childNodes;
			  	
			  	parametresQuestion[0].innerHTML = 'Question n°' + numeroQuestionNouveau + '<span class="supprimer_q" onclick="supprimerQuestion(' + i + ',' +  numeroQuestionNouveau + ');"> [ X ] </span>';
			  				  	
			  	/* élément <select> */
			  	parametresQuestion[1].name = "question_type_q" + numeroQuestionNouveau + "_g" + i;
			  	parametresQuestion[1].id = parametresQuestion[1].name;
			  	
				/*  <input type="text" id="question_texte_q1_g1" name="question_texte_q1_g1" placeholder="La question présentée aux utilisateurs" /> */
			  	parametresQuestion[2].name = "question_texte_q" + numeroQuestionNouveau + "_g" + i;
			  	parametresQuestion[2].id = parametresQuestion[2].name;

				/* <label for="champ_obligatoire_q3_g2">Oui</label> */
			  	parametresQuestion[4].htmlFor = "champ_obligatoire_q" + numeroQuestionNouveau + "_g" + i;

				/* <input type="radio" id="champ_obligatoire_q3_g2" name="question_obligatoire_q3_g2" value="1" /> */
			  	parametresQuestion[5].name = "question_obligatoire_q" + numeroQuestionNouveau + "_g" + i;
			  	parametresQuestion[5].id   = "champ_obligatoire_q" + numeroQuestionNouveau + "_g" + i;

			  	/*  <label for="champ_pas_obligatoire_q3_g2">Non</label> */
			  	parametresQuestion[6].htmlFor = "champ_pas_obligatoire_q" + numeroQuestionNouveau + "_g" + i;

				/*  <input type="radio" id="champ_pas_obligatoire_q3_g2" name="question_obligatoire_q3_g2" value="0"  />  */
			  	parametresQuestion[7].name = "question_obligatoire_q" + numeroQuestionNouveau + "_g" + i;
			  	parametresQuestion[7].id   = "champ_pas_obligatoire_q" + numeroQuestionNouveau + "_g" + i;
				
			  	/* <input type="button" id="question_3_ajouteProposition" value=" + Proposition" onclick="ajouteProposition();" />  */			  	
			  	parametresQuestion[8].innerHTML = "Les champs qui seront affichés aux utilisateurs pour répondre à cette question <br />" ;
			  	parametresQuestion[8].innerHTML += '<input id="question_' + numeroQuestionNouveau + '_ajouteProposition" value=" + Proposition" type="button" onclick="ajouteProposition(' + i + ' , ' + numeroQuestionNouveau + ');" /> \n' ;           				                            				                                                

			  	var k = 9 , numeroProposition = 0;
			  	while( (k + 3) <  parametresQuestion.length)//Cette question a des propositions
			  	{
				  	numeroProposition++;
				  	
				  	parametresQuestion[k].innerHTML = '<span class="supprimer_proposition" onclick="supprimerProposition(' + i + ' , ' + numeroQuestionNouveau + ' , ' + numeroProposition + ')"> [ X Supprimer la proposition ] </span>';

				  	parametresQuestion[k + 1].name = "question_proposition_q" + numeroQuestionNouveau + "_g" + i + "_p" + numeroProposition;
				  	parametresQuestion[k + 1].id   = parametresQuestion[k + 1].name;
				  	
				  	parametresQuestion[k + 2].name = "question_proposition_score_q" + numeroQuestionNouveau + "_g" + i + "_p" + numeroProposition;
				  	parametresQuestion[k + 2].id   = parametresQuestion[k + 2].name;

				  	k+=4;
			  	}
			  	
			  	nbPropositions[numeroQuestionNouveau - 1] = numeroProposition;

	  }//fin du for(j)		  
	}//fin du for(i)		  

	nbQuestions++;
}

function ajouteGroupe()
{	
	/* <input id="button_ajoute_groupe" type="button" value=" + Groupe de questions" onclick="ajouteGroupe();" /> */
	var buttonAjouteGroupe = document.getElementById("button_ajoute_groupe");
	
	/* <fieldset id="groupe_de_question_2"> </fieldset>  */
	var fieldset = document.createElement("fieldset"); /* Nouvel élément <fieldset> */
	var fieldsetId = "groupe_de_question_" + nbGroupes;
	fieldset.setAttribute("id", fieldsetId);	
	document.getElementById("form_parametres-categorie").insertBefore(fieldset, buttonAjouteGroupe);
	
	/* <legend>Groupe de questions n°2</legend> */
	var legend = document.createElement("legend"); /* Nouvel élément <legend> */
	legend.setAttribute("id", fieldsetId +"legend");
	legend.innerHTML = 'Groupe de questions n°' + nbGroupes + '<span class="supprimer_groupe_de_questions" onclick="supprimerGroupeDeQuestions(' + nbGroupes + ')"> [ X ] </span>';	
	document.getElementById(fieldsetId).appendChild(legend);

	/* <input type="button" id="groupe_de_question_2_ajouteQuestion" value=" + Question" onclick="ajouteQuestion(2);" /> */             				                            				                                                
	var div = document.createElement("div"); /* Nouvel élément <div> */
	div.innerHTML = '<input type="button" id="groupe_de_question_' + nbGroupes + '_ajouteQuestion" value=" + Question" onclick="ajouteQuestion(' + nbGroupes + ');" /> \n' ;           				                            				                                                
	document.getElementById(fieldsetId).appendChild(div);
	
	nbGroupes++;
}

/* Source : https://wiki.developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Number/isInteger#Polyfill
 * Any copyright is dedicated to the Public Domain. http://creativecommons.org/publicdomain/zero/1.0/
 */
Number.isInteger = Number.isInteger || function(value) {
	  return typeof value === 'number' && 
	    isFinite(value) && 
	    Math.floor(value) === value;
	};

function beforeSubmit()
{	
	var erreurElement = document.getElementsByClassName("erreur")[0];
	var succesElement = document.getElementsByClassName("succes")[0];

	if (erreurElement != null)
		document.getElementsByTagName("section")[0].removeChild(erreurElement);
	
	if (succesElement != null)
		document.getElementsByTagName("section")[0].removeChild(succesElement);

	var t = 0;
	var erreurs = new Array();	
	var valeurTitre = document.getElementById("titre").value;
	
        if ( valeurTitre == null || valeurTitre.trim().length == 0 )
        	erreurs[t++] = "Le champ <strong>Titre</strong> est un champ obligatoire.";  
        
    var valeurDescription = document.getElementById("description").value;
    	
        if ( valeurDescription == null || valeurDescription.trim().length == 0 )
        	erreurs[t++] = "Le champ <strong>Description</strong> est un champ obligatoire.";
        
    	for(var i = 1 ; i < nbGroupes ; i++)
    	{
    		var groupe = document.getElementById("groupe_de_question_" + i );
    		var questions = groupe.children;
    	  
    		/* Chaque ensemble de champs d'un groupe de questions a 2 "fils" qui ne sont pas des questions :
    		 * le titre, le bouton pour ajouter une question
    		 */ 
    		if( questions.length == 2)
            	erreurs[t++] = "Le <strong>Groupe de questions n°" + i + "</strong> est vide.";
    		
    		for (var j = 2; j < questions.length ; j++)
    		{	  	
		  		var question = questions[j];
			  	var numeroQuestion = questions[j].id.substring(9); //<fieldset id="question_4"> </fieldset>
			  	var parametresQuestion = question.children;
			  	
			  	// parametresQuestion[0] = <label>Question n°1 [ X ] </label> 			  	
		  	
			  	/* parametresQuestion[1] - élément <select id="question_type_q1_g1"> */			  	
		        if ( parametresQuestion[1].value == null || parametresQuestion[1].value.trim().length == 0 || parametresQuestion[1].value == "titre")
		        	erreurs[t++] = "<strong>Groupe n°" + i + " Question n°" + numeroQuestion + "</strong> Type de question est un champ obligatoire.";  

			  	
				/*  parametresQuestion[2] - <input type="text" id="question_texte_q1_g1" name="question_texte_q1_g1" placeholder="La question présentée aux utilisateurs" /> */
		        if ( parametresQuestion[2].value == null || parametresQuestion[2].value.trim().length == 0)
		        	erreurs[t++] = "<strong>Groupe n°" + i + " Question n°" + numeroQuestion + "</strong> La question présentée aux utilisateurs est un champ obligatoire.";  

		        /*  parametresQuestion[3] - <label class="label_champ_obligatoire">Champ obligatoire : </label>   */
		        /*  parametresQuestion[4] - <label for="champ_obligatoire_q3_g2">Oui</label>   */		        
				/* parametresQuestion[5] - <input type="radio" id="champ_obligatoire_q3_g2" name="question_obligatoire_q3_g2" value="1" /> */
			  	/* parametresQuestion[6] - <label for="champ_pas_obligatoire_q3_g2">Non</label> */
		        /* parametresQuestion[7] - <input type="radio" id="champ_pas_obligatoire_q3_g2" name="question_obligatoire_q3_g2" value="0"  /> */
		        var champObligatoire = document.getElementsByName( parametresQuestion[7].name );
		        		        
		        if ( !( champObligatoire[0].checked || champObligatoire[1].checked ) )
		        	erreurs[t++] = "<strong>Groupe n°" + i + " Question n°" + numeroQuestion + "</strong> Le choix s’il s’agit d’une question obligatoire est indispensable.";  

		        
			  	/* parametresQuestion[8] - <input type="button" id="question_3_ajouteProposition" value=" + Proposition" onclick="ajouteProposition();" />  */			  	

			  	var k = 9 , numeroProposition = 0;
			  	while( (k + 3) <  parametresQuestion.length)//Cette question a des propositions ?
			  	{
				  	numeroProposition++;
				  	
				  	//parametresQuestion[k] =  [ X Supprimer la proposition ] 

				  	//parametresQuestion[k + 1] - question_proposition
			        if ( parametresQuestion[k + 1].value == null || parametresQuestion[k + 1].value.trim().length == 0 )
			        	erreurs[ t++ ] = "<strong>Groupe n°" + i + " Question n°" + numeroQuestion + " Proposition n°" + numeroProposition + "</strong> Le texte présenté aux utilisateurs est un champ obligatoire.";
				  					  	
				  	//parametresQuestion[k + 2] - question_proposition_score
			        if ( parametresQuestion[k + 2].value == null || parametresQuestion[k + 2].value.trim().length == 0 || !Number.isInteger( (parametresQuestion[k + 2].value)*1 ) )
			        	erreurs[ t++ ] = "<strong>Groupe n°" + i + " Question n°" + numeroQuestion + " Proposition n°" + numeroProposition + "</strong> Le score est un champ obligatoire et doit contenir que des chiffres entiers.";

				  	//parametresQuestion[k + 3] = <br />

				  	k+=4;
			  	}//fin while			  	
    	  }//for(j)
    	}//for(i)
    	
    	var formElement = document.getElementById("form_parametres-categorie");
    	if(erreurs.length == 0)
    	{
    		formElement.submit();    		
    	}
    	else
    	{
    		var articleElement = document.createElement("article");
    		articleElement.setAttribute("class", "erreur");
    		articleElement.innerHTML = "";
    		
    		for( i = 0 ; i < erreurs.length ; i++ )
    			articleElement.innerHTML += (erreurs[i] + "<br />");
    		
    		var articleElementParametres = document.getElementById("parametres");
    		document.getElementsByTagName("section")[0].insertBefore(articleElement, articleElementParametres);
    	}
    	
}//fin fonction
