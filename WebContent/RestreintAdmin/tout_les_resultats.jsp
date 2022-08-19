<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang=fr>
	<head>
		 <meta charset="ufr-8">
         <title>Les résultats d'un formulaire</title>
         <link href="<c:url value="/CSS/style_commun.css"/>" rel="stylesheet" type="text/css" />
         <link href="<c:url value="/CSS/List.css"/>" rel="stylesheet" type="text/css" />
         <script src="<c:url value="/JS/pageAlert.js"/>" type="text/javascript"></script>
	</head>
      
	<body>
      
      	<%-- L'en-tête général --%>
		<header> 
			<h1>Création de formulaire</h1> 
			<p class="deconnexion"> <a href="<c:url value="/deconnexion"/>"> Déconnexion </a></p>
		</header>
		
		<%-- Navigation --%>
		<nav>	
			<ul> 
				<li><a href="<c:url value="/AccueilAdministrateur"/>">Page d'accueil</a></li>
				<li><a href="<c:url value="/list-utilisateur"/>">Liste des utilisateurs</a></li>
			</ul>
		</nav>
				
				
		<section>

	     	<h2>Tous les résultats </h2>
	     	
	     	<article>
	     	
	     	<span class="diagramme">
	     		<a href="<c:url value="diagrammeAdmin?id=${categorie.id}"/>">Voir les diagrammes</a>
			</span>		
	        	<table border="1">
	        	
	        		<%-- Affiche du nom prénom, email et le score des utilisateurs --%>
			    	<tr id="titre">
			    		<th class="nom">Nom</th>
			    		<th class="prenom">Prénom</th>
						<th class="mail">E-mail</th>
						<th class="score">Score Obtenu</th>
			   		</tr>
			   		
			   		<%-- Affichage des résultats des utilisateurs d'une catégorie --%>
			   		<c:forEach var="score" items="${score}">
			      		<tr>
			      			<c:forEach var="utilisateur" items="${utilisateur }">
			      				<c:if test="${utilisateur.email == score.utilisateurEmail}">
			      					<td>${utilisateur.nom}</td>
			      					<td>${utilisateur.prenom}</td>
			      					<td>${score.utilisateurEmail}</td>
									<td>${score.scoreTotal}/${total }</td>
			      				</c:if>
			      			</c:forEach>
					
			    		</tr>
			    	</c:forEach>
			   
	        	</table>
	        		
	        	<%-- Page --%>
				<div class="page">
					<a href="?id=${categorie.id}&start=0">Première page</a>
					<a href="?id=${categorie.id}&start=${pre}" onclick="alertPremierePage(${pre})">&lt; Précédente</a>
					<a href="?id=${categorie.id}&start=${suiv}" onclick="alertDernPage(${suiv}, ${dern})">Suivante &gt;</a>
					<a href="?id=${categorie.id}&start=${dern}">Dernière page</a>
			    </div>
	        		
	     	</article>
	     	
		</section>
		
	</body>
	
</html>
