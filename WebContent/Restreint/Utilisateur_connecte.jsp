<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<link href="<c:url value="/CSS/style_commun.css"/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/CSS/CategorieUtilisateur.css"/>" rel="stylesheet" type="text/css" />
		<script src="<c:url value="/JS/pageAlert.js"/>" type="text/javascript"></script>
		<title>Création de formulaire • page d'accueil</title>
	</head>

	<body>
	
		<header> <%-- L'en-tête général --%>
			<h1>Création de formulaires</h1> 
			<p class="deconnexion"> <a href="<c:url value="/deconnexion"/>"> Déconnexion </a></p>
		</header>
		
		<nav> <%-- Navigation --%>	
			<ul> 
				<li><a class="active" href="<c:url value="/AccueilUtilisateur"/>">Page d'accueil</a></li>
				<li><a href="<c:url value="/mes-donnees-personnelles"/>">Mon profil</a></li>
			</ul>
		</nav>
		
		<section>
			<%-- Vérification de la présence d'un objet utilisateur en session --%>
            <c:if test="${!empty sessionScope.sessionUtilisateur}">
            	<%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
            	<p class="succes_utilisateur"> <strong> Vous êtes connecté(e) avec l'adresse </strong> : ${sessionScope.sessionUtilisateur.email}</p>
            </c:if>      
            
			<h2>Page d'accueil : liste des catégories de questions <span class="total"> Total de catégorie:  <span class="nombre">${total}</span> </span> </h2>
			<article> 
				<div class="categorie">
			    	<ul>
			    		<%-- Affiche tous les catégorie dans la base de données sous forme de FlexBox --%>
						<c:forEach var="categorie" items="${categorie}">
				    		<li class="flex">
				    			<div class="titre_description">
				    				<div class="titre"> <a href="<c:url value="/repondre-formulaire?id=${categorie.id}"/>" >${categorie.titre}</a> <%-- Appuyer pour répondre à la catégorie --%> </div>
				    				<div class="description">
				    				 	<p>${categorie.description}</p> <%-- Description de la ctégorie --%> 
				    				</div>
				    	
				    			</div>
				    			
				    			<div class="resultat">
	    							<c:forEach var="reponse" items="${reponses}">
	 										<c:if test="${categorie.id eq reponse.categorieId}">
											<c:set var="eq_val" value="true"></c:set> 
											<c:set var="reponseId" value="${reponse.categorieId}"></c:set>
						 				</c:if>	
						 			</c:forEach>  
						 	         							
	   								<%-- Si l'utilisateur a déjà répondu à la catégorie, il pourra voir son résultat--%>
	   								<%-- Si l'utilisateur n'a jamais répondu à la catégorie, il ne pourra pas cliquer sur le bouton --%>
	   								<a href="<c:url value="/mes-resultats?id=${categorie.id}&email=${sessionScope.sessionUtilisateur.email}"/>" <c:if test="${categorie.id != reponseId}">class="disabled"</c:if>>Mon résultat</a>
			    				</div>
				    			
				    		</li>
				    
				    	</c:forEach>
			    	</ul>
				</div>
			</article>
			<div class="page">
					<a href="<c:url value="/AccueilUtilisateur?start=0"/>">Première page</a>
					<a href="<c:url value="/AccueilUtilisateur?start=${pre}" />" onclick="alertPremierePage(${pre})">&lt; Précédente</a>
					<a href="<c:url value="/AccueilUtilisateur?start=${suiv}" />" onclick="alertDernPage(${suiv}, ${dern})">Suivante &gt;</a>
					<a href="<c:url value="/AccueilUtilisateur?start=${dern}" />">Dernière page</a>
				</div>
		</section>
	</body>
</html>