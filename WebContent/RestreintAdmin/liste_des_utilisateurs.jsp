<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang=fr>

	<head>
		<meta charset="ufr-8">
	    <title>Listes des utilisateurs</title>
	    <link href="<c:url value="/CSS/style_commun.css"/>" rel="stylesheet" type="text/css" />
	    <link href="<c:url value="/CSS/List.css"/>" rel="stylesheet" type="text/css" />
	    <script src="<c:url value="/JS/alert.js"/>" type="text/javascript"></script>
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
				<li><a class="active" href="<c:url value="/list-utilisateur"/>">Liste des utilisateurs</a></li>
			</ul>
		</nav>
         
		<section>

	     	<h2>Listes des utilisateurs</h2>
	     
	    	 <article class ="liste_utilisateur">
	    	 
	    	 	<p>${message}</p>
	    	 	
	    	 	<div class="total">
	    	 		Total d'utilisateurs: 
	    	 		<span class="nombre">${total}</span>
				</div>
	    	 
	        	<table border="1">
	        	
					<%-- Affichage des informations des utilisateurs inscrits --%>
			    	<tr id="titre">
						<th class="nom" title="Nom">Nom</th>
						<th class="prenom" title="Prénom">Prénom</th>
						<th class="email" title="Email">Email</th>
						<th class="sexe" title="Sexe">Sexe</th>
						<th class="dateNaissance" title="Date de Naissance">Date de Naissance</th>
						<th class="dateInscription" title="Date d'inscription">Date d'inscription</th>
						<th class="action" title="Action">Action</th>
			    	</tr>
			    	
			    	<%-- Boucle qui affiche tous les utilisateurs qui existents dans la base de données --%>
			    	<c:forEach var="utilisateur" items="${utilisateur}">
			    		<tr>
							<td title="${utilisateur.nom}">${utilisateur.nom}</td>
							<td title="${utilisateur.prenom}">${utilisateur.prenom}</td>
							<td title="${utilisateur.email}">${utilisateur.email}</td>
							<td>
								<c:choose>
									<c:when test="${utilisateur.sexe== 0 }">M</c:when>
									<c:when test="${utilisateur.sexe== 1 }">F</c:when>
								</c:choose>
							</td>
							<td title="${utilisateur.dateNaissance}">${utilisateur.dateNaissance}</td>
							<td title="${utilisateur.dateInscription}">${utilisateur.dateInscription}</td> 
							<td> 
							
								<%-- Les boutons qui permettent de supprimer ou paramétrer un utilisateur --%>
								<a href="<c:url value="/update-utilisateur?email=${utilisateur.email}"/>" >Paramètre</a> 
								<a href="<c:url value="/delete-utilisateur?email=${utilisateur.email}"/>" onclick="return deleteConfirm()">Supprimer</a>
							
							</td>      
			    		</tr>
					</c:forEach>
			 
	        	</table>
				
				<%-- Page --%>
				<div class="page">
					<a href="<c:url value="/list-utilisateur?start=0"/>">Première page</a>
					<a href="<c:url value="/list-utilisateur?start=${pre}" />" onclick="alertPremierePage(${pre})">&lt; Précédente</a>
					<a href="<c:url value="/list-utilisateur?start=${suiv}" />" onclick="alertDernPage(${suiv}, ${dern})">Suivante &gt;</a>
					<a href="<c:url value="/list-utilisateur?start=${dern}" />">Dernière page</a>
				</div>
			    	        		
	     	</article>
	     	
	     </section>
	   
	</body>

</html>
    