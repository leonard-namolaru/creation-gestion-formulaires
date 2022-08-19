<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<link href="<c:url value="/CSS/style_commun.css"/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/CSS/resultat_categorie.css"/>" rel="stylesheet" type="text/css" />
		<title>Résultat de mon formulaire</title>
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
				<li><a href="<c:url value="/AccueilUtilisateur"/>">Page d'accueil</a></li>
				<li><a href="<c:url value="/mes-donnees-personnelles"/>">Mon profil</a></li>
			</ul>
		</nav>
		
		<section>
			<h2>Mon résultat</h2>
			
			<article>

					<fieldset id="categorie">
						<!-- Titre de la ctégorie -->
						<h2>${categorie.titre }</h2>
						
						<!-- Description de la catégorie -->
						<p>${categorie.description }</p>
						
						<!-- Score obtenu dans une catégorie -->
						<fieldset id="score">
							<p>Mon score est: ${score} / ${total }</p>
						</fieldset>
						
					</fieldset>
	
			</article>
			
		</section>

	</body>

</html>