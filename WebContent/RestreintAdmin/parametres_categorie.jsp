<%-- parametres_categorie.jsp
* Auteur : Leonard Namolaru
* Travail effectué : Tout le code qui apparaît dans ce fichier
*
* Description : une page qui permet à l'administrateur d'ajouter une nouvelle catégorie de questions. 
* L'administrateur peut déterminer le nom et la description de la catégorie et également les questions de la catégorie. 
* Les questions de la catégorie sont divisées en groupes de questions. L'administrateur peut ajouter un nombre illimité de groupes de questions.
* Chaque groupe de questions peut contenir un nombre illimité de questions et chaque question peut contenir un nombre illimité de "propositions".
* Une proposition est un champ de saisie (<input>) disponible pour l'utilisateur pour répondre à la question.
*
* Projet : Sujet L2N - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html lang="fr">

	<head>
		<title>Création de formulaires • Nouvelle catégorie</title> 
		<meta charset="utf-8" />
		<link href="<c:url value="/CSS/style_commun.css"/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/CSS/parametre_formulaire.css"/>" rel="stylesheet" type="text/css" />
		<script src="<c:url value="/JS/parametres_categorie.js"/>" type="text/javascript"></script>
	</head>
	
	<body onload="init(1,1);">
	
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
               
			<h2>Nouvelle catégorie de questions</h2> 
			<p class="description">
			Les questions de la catégorie sont divisées en groupes de questions. 
			Chaque groupe de questions peut contenir un nombre illimité de questions et chaque question peut contenir un nombre illimité de "propositions".
			Une proposition est un champ de saisie disponible pour l'utilisateur pour répondre à la question.
			 </p>
			
			${form.resultat}
			<article id="parametres">
				<form id="form_parametres-categorie" method="post" action="parametres-categorie"> <!-- Formulaire pour créer une catégorie -->
					<fieldset id="nom_desc">
						<legend>Nom et description</legend>
							<label for="titre">Titre</label><br />
							<input type="text" id="titre" name="titre" placeholder="Titre de la catégorie" required="required" autofocus />
							<br />
							<label for="description">Description</label><br />
							<input type="text" id="description" name="description" placeholder="Description du formulaire" required="required" />
					</fieldset>
					 
					<input id="button_ajoute_groupe" type="button" value=" + Groupe de questions" onClick="ajouteGroupe();" />
					<input type="button" id="submit_button" value="Valider"  onClick="beforeSubmit();" />
				</form>				
			</article>
		</section>
	</body>
</html>