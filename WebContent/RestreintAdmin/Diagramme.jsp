<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link href="<c:url value="/CSS/style_commun.css"/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/CSS/Diagramme.css"/>" rel="stylesheet" type="text/css" />
		<script src="<c:url value="/JS/pageAlert.js"/>" type="text/javascript"></script>
		<title>Diagrammes de la catégorie</title>
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
		
			<h2>Page d'accueil</h2>
			
			<div class="${empty form.erreurs ? 'succes': 'erreur'}">${form.resultat}</div>
		
			<article>
				<p class="titre_categorie">Categorie: ${categorie.titre }</p>
				<p class="ps">Les diagrammes présentés ci-dessous sont générés à partir de question de type Radio, CheckBox et Select</p>
				
				<div id="diagramme">
					<%-- Génération des diagrammes en PNG--%>
					<c:forEach var="image" items="${images}">
						<div id="image">
                			<img id= "im" src="${image}"/>
                		</div>
                	</c:forEach>
                	
                </div>
                
            </article>
            
		</section>
		
	</body>
	
</html>