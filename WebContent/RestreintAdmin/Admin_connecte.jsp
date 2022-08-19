<%-- Admin_connecte.jsp
* Auteurs : Leonard Namolaru, Huihui Huang
* Travail effectué : Tout le code qui apparaît dans ce fichier

* Description : Page principale de l'espace d'administration. 
* Une fois qu'un utilisateur défini comme administrateur se connecte, il est directement dirigé vers cette page.
* Un utilisateur est défini en tant qu'administrateur si dans la table des utilisateurs de la base de données, la valeur dans la colonne utilisateur_type pour cet utilisateur est 1 et non 0 (0 = utilisateur normal).
*
* Projet : Sujet L2N - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes
 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<link href="<c:url value="/CSS/style_commun.css"/>" rel="stylesheet" type="text/css" />
	    <link href="<c:url value="/CSS/List.css"/>" rel="stylesheet" type="text/css" />
		<script src="<c:url value="/JS/alert.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/JS/pageAlert.js"/>" type="text/javascript"></script>
		<title>Création de formulaires • Page d'accueil - espace d'administration</title>
	</head>
	
	<body>
		<header> <%-- L'en-tête général --%>
			<h1>Création de formulaires</h1> 
			<p class="deconnexion"> <a href="<c:url value="/deconnexion"/>"> Déconnexion </a></p>
		</header>
		
		<nav> <%-- Navigation --%>
			<ul> 
				<li><a class="active" href="<c:url value="/AccueilAdministrateur"/>">Page d'accueil</a></li>
				<li><a href="<c:url value="/list-utilisateur"/>">Liste des utilisateurs</a></li>
			</ul>
		</nav>
		
		<section>					
            <c:if test="${!empty sessionScope.sessionAdmin}"> <%-- Vérification de la présence d'un objet utilisateur en session --%>           	
            	<p id="succes_admin"> <strong> Vous êtes connecté(e) avec l'adresse </strong> : ${sessionScope.sessionAdmin.email} </p> <%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
            </c:if>              
			<h2>Page d'accueil - espace d'administration</h2>	
			<h3>Liste des catégories de questions</h3>		
			
			${message}
			<article>
			
				<a href="<c:url value="/parametres-categorie"/>"> <input type="button" id="ajouteCategorie" value=" + Categorie" /> </a>					
				
	    	 	<div class="total">
	    	 		Total de catégorie: 
	    	 		<span class="nombre">${total}</span>
				</div>
				
				<div class="categorie">				
					<table border="1">					
				    	<tr id="titre">
							<th class="nom">Titre</th>
							<th class="prenom">Description</th>
							<th class="action">Action</th>
				    	</tr>
				    	
				    	<%-- Affiche tous les catégorie crées dans la base de données--%>
						<c:forEach var="categorie" items="${categorie}"> 
					    	<tr>
								<td>${categorie.titre}</td>  
								<td>${categorie.description}</td>
								<td> 					
									<%-- Mise à jour d'une catégorie --%>				
									<a href="<c:url value="mise-a-jour-categorie?id=${categorie.id}"/>">Paramètre</a> 
									
									<%-- Supprimer une catégorie --%>
									<a href="<c:url value="delete-categorie?id=${categorie.id}"/>" onclick="return deleteConfirm()">Supprimer</a> 
									
									<!-- Parcourir la catégorie des réponses -->
									<c:forEach var="reponse" items="${reponses}">
    									<c:if test="${categorie.id eq reponse.categorieId}">
  											<c:set var="eq_val" value="true"></c:set> 
  											<c:set var="reponseId" value="${reponse.categorieId}"></c:set>
								 		</c:if>	
								 	</c:forEach>  
								 	
									<%-- Tous les résultats d'une catégorie --%>
									<a href="<c:url value="toutlesresultat?id=${categorie.id}"/>" <c:if test="${categorie.id != reponseId}">class="disabled"</c:if>>Résultats</a> 
								</td>      
					    	</tr>
				    	</c:forEach>
		        	</table>
				</div>
				
				<div class="page">
					<a href="<c:url value="/AccueilAdministrateur?start=0"/>">Première page</a>
					<a href="<c:url value="/AccueilAdministrateur?start=${pre}" />" onclick="alertPremierePage(${pre})">&lt; Précédente</a>
					<a href="<c:url value="/AccueilAdministrateur?start=${suiv}" />" onclick="alertDernPage(${suiv}, ${dern})">Suivante &gt;</a>
					<a href="<c:url value="/AccueilAdministrateur?start=${dern}" />">Dernière page</a>
				</div>
			</article>
			
		</section>
		
	</body>
</html>