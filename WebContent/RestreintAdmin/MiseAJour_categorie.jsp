<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html lang="fr">

	<head>
		<title>Création de formulaires • paramètres de la catégorie</title> 
		<meta charset="utf-8" />
		<link href="<c:url value="/CSS/style_commun.css"/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/CSS/parametre_formulaire.css"/>" rel="stylesheet" type="text/css" />
		<script src="<c:url value="/JS/MiseAJour_categorie.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/JS/alert.js"/>" type="text/javascript"></script>
	</head>	
	
	<body>
	
		<header> <%-- L'en-tête général --%>
			<h1>Création de formulaires</h1> 
			<p class="deconnexion"> <a href="<c:url value="/deconnexion"/>"> Déconnexion </a></p>
		</header>
		
		<nav> <%-- Navigation --%>
			<ul> 
				<li> <a href="<c:url value="/AccueilAdministrateur"/>">Page d'accueil</a> </li>
				<li> <a href="<c:url value="/list-utilisateur"/>">Liste des utilisateurs</a> </li>
			</ul>
		</nav>
				
		<section>	
			<c:if test="${!empty sessionScope.sessionAdmin}"> <%-- Vérification de la présence d'un objet utilisateur en session --%>            	
            	<p id="succes_admin"> <strong> Vous êtes connecté(e) avec l'adresse </strong> : ${sessionScope.sessionAdmin.email} </p> <%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
            </c:if>           
		
			<h2>Paramètres de la catégorie "${categorie.titre}"</h2>
			<p class="description">
			Les questions de la catégorie sont divisées en groupes de questions. 
			Chaque groupe de questions peut contenir un nombre illimité de questions et chaque question peut contenir un nombre illimité de "propositions".
			Une proposition est un champ de saisie disponible pour l'utilisateur pour répondre à la question.
			 </p>
			 
			${message}
			${form.resultat}	
			<article id="parametres">
				<form id="form_parametres-categorie" method="post" action="<c:url value="/mise-a-jour-categorie?id=${categorie.id}"/>">				
					<fieldset id="nom_desc">
						<legend>Nom et description</legend>
						<label for="titre">Titre</label><br /> <%-- Titre de la catégorie --%>
						<input type="text" id="titre" name="titre" value="<c:out value="${categorie.titre}"/>" />
						<br />
						<label for="description">Description</label><br /> <%-- Description de la catégorie --%>
						<input type="text" id="description" name="description" value="<c:out value="${categorie.description}"/>" />
					</fieldset>
					
					<c:set var="numeroQuestion" value="${0}"/> <%-- Affiche les questions par groupe --%>	
					<c:forEach var = "i" begin = "1" end = "${categorie.nbGroupes}">	
						<%-- ***************************************** GROUPE DE QUESTION ***************************************** --%>
						<fieldset id="groupe_de_question_${i}">		
							<legend> <%-- Legende + Supprimer un groupe de question de la base de données --%>
								Groupe de question n°${i} <a class="supprimer_groupe_de_questions" href="<c:url value="/delete-groupe?idCategorie=${categorie.id}&groupe=${i}"/>" title="Supprimer le groupe" onclick="return deleteConfirm()"> [ X Supprimer de la base de données ] </a> 
							</legend>				
							<input class="button_ajoute_question" type="button" value=" + Questions" onClick="ajouteQuestion(${i});" /> <%-- Ajout question --%>
							
							<c:forEach var="question" items="${question}"> <%-- Boucle qui affiche les questions de la catégorie --%>	
								<c:if test="${question.questionGroupe == i}">
									<c:set var="numeroQuestion" value="${numeroQuestion + 1}"/>	
									
									<%-- ***************************************** AFFICHE LES QUESTIONS ***************************************** --%>							
									<fieldset id="question_${numeroQuestion}">	
										
										<legend> <%-- Legende + Supprimer une question --%>
											Question n°${numeroQuestion} <a class="supprimer_q" href="<c:url value="/delete-question?idCategorie=${categorie.id }&id=${question.id}"/>" title="Supprimer cette question" onclick="return deleteConfirm()"> [ X Supprimer de la base de données ] </a> 
										</legend>
										<input type="hidden" id="question${numeroQuestion}_id" name="question${numeroQuestion}_id" value="${question.id}" />
										
										<select id="question_type_q${numeroQuestion}_g${question.questionGroupe}" name="question_type_q${numeroQuestion}_g${question.questionGroupe}"> <%-- Choix des types de questions --%>
											<option value="titre">--Type de question--</option>
												<optgroup label="Eléments d'options">
													<option value="radio" <c:if test="${question.type== 1}">selected="selected"</c:if>>Boutons radio</option>
													<option value="cases" <c:if test="${question.type== 2}">selected="selected"</c:if>>Cases à cocher</option>
													<option value="liste" <c:if test="${question.type== 3}">selected="selected"</c:if>>Liste déroulante</option> 
												</optgroup> 
												<optgroup label="Chaine de caractères">
													<option value="texte" <c:if test="${question.type== 4}">selected="selected"</c:if>>Champ de texte</option> 
													<option value="date"  <c:if test="${question.type== 5}">selected="selected"</c:if>>Date</option> 
													<option value="tel"   <c:if test="${question.type== 6}">selected="selected"</c:if>>Numéro de téléphone</option> 
												</optgroup>
										</select>
										
										<%-- Question obligatoire ou non --%>
										<input type="text" id="question_texte_q${numeroQuestion}_g${question.questionGroupe}" name="question_texte_q${numeroQuestion}_g${question.questionGroupe}" placeholder="La question présentée aux utilisateurs" value="<c:out value="${question.label}"/>" />
										<label class="label_champ_obligatoire">Champ obligatoire: </label>
										<label for="champ_obligatoire_q${numeroQuestion}_g${question.questionGroupe}">Oui</label>										
										<input type="radio" id="champ_obligatoire_q${numeroQuestion}_g${question.questionGroupe}" name="question_obligatoire_q${numeroQuestion}_g${question.questionGroupe}" value="1" <c:if test="${question.obligatoire == 1}">checked="checked"</c:if> />
										<label for="champ_pas_obligatoire_q${numeroQuestion}_g${question.questionGroupe}">Non</label>
										<input type="radio" id="champ_pas_obligatoire_q${numeroQuestion}_g${question.questionGroupe}" name="question_obligatoire_q${numeroQuestion}_g${question.questionGroupe}" value="0" <c:if test="${question.obligatoire == 0}">checked="checked"</c:if> />
										
										<div id="titre_reponses_${numeroQuestion}" class="titre_reponses">
											Les champs qui seront affichés aux utilisateurs pour répondre à cette question
											<br />
											<input id="button_ajoute_proposition" type="button" value=" + propositions" onClick="ajouteProposition(${question.questionGroupe},${numeroQuestion});" />						
										</div>
										
										<c:set var="numeroProposition" value="${0}"/>
										<script> initArray(${numeroQuestion} , 0);</script>
										
										<%-- ***************************************** AFFICHE LES PROPOSITIONS ***************************************** --%>
										<c:forEach var="proposition" items="${propositions}"> <%-- Boucle qui affiche les propositions d'une question --%>												
											<c:if test="${question.id == proposition.questionId}">													
												<c:set var="numeroProposition" value="${numeroProposition + 1}"/>														
												<input type="hidden" id="question${numeroQuestion}proposition${numeroProposition}_id" name="question${numeroQuestion}proposition${numeroProposition}_id" value="${proposition.id}" />															
												<span> <a class="supprimer_proposition" href="<c:url value="/delete-proposition?idCategorie=${categorie.id }&id=${proposition.id}"/>" title="Supprimer cette proposition" onclick="return deleteConfirm()">[ X Supprimer la proposition de la base de données ]</a>	</span>
												
												<input type="text" id="question_proposition_q${numeroQuestion}_g${question.questionGroupe}_p${numeroProposition}" name="question_proposition_q${numeroQuestion}_g${question.questionGroupe}_p${numeroProposition}" placeholder="Le texte présenté aux utilisateurs" value="<c:out value="${proposition.label}"/>" />
												<input type="text" id="question_proposition_score_q${numeroQuestion}_g${question.questionGroupe}_p${numeroProposition}" name="question_proposition_score_q${numeroQuestion}_g${question.questionGroupe}_p${numeroProposition}" placeholder="Score" value="<c:out value="${proposition.score}"/>" />
												<script> initArray(${numeroQuestion} , ${numeroProposition});</script>
											</c:if>													
										</c:forEach>
									</fieldset>	
								</c:if>	
							</c:forEach>
						</fieldset>	
					</c:forEach>	
										
					<script> init(${categorie.nbGroupes + 1} , ${numeroQuestion + 1}); </script>
					<input id="button_ajoute_groupe" type="button" value=" + Groupe de questions" onClick="ajouteGroupe();" />					
					<input type="button" id="submit_button" value="Valider" onClick="beforeSubmit();" />				
				</form>		
			</article>
		</section>	
	</body>
</html>