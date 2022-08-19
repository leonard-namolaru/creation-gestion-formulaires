<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link href="<c:url value="/CSS/style_commun.css"/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/CSS/Page_daccueil_utilisateur_pas_connecte.css"/>" rel="stylesheet" type="text/css" />
		<title>Création de formulaires • Mot de passe oublié</title>
	</head>

	<body>
	
		<%-- L'en-tête général --%>
		<header> 
			<h1>Création de formulaires</h1> 
			<p class="inscription"> <a href="<c:url value="/page-inscription"/>"> Inscription </a>  </p>
		</header>
		
		<%-- Navigation --%>
		<nav> 
			<ul>						
				<li><a href="<c:url value="/connexion"/>">Retour à la page de connexion</a></li>
			</ul>
		</nav>
		
		<section>
		
			<article id= "motDePasseOublie">
			
				<form  method="post" action="<c:url value="/motdepasse-oublie"/>">
				
					<fieldset>
						<legend>Mot de passe oublié ?</legend>
						
						<%-- Affiche si succès ou échec de l'envoie du email --%>
						<div class="${empty form.erreurs ? 'succes': 'erreur'}">${form.resultat}</div>
						
						<%-- L'utilisateur remplit son mail dans le champ et appuie sur le bouton Envoyer--%>
						<label for="email">Adresse e-mail :</label>
						<input type="email" id="email" name="email" maxlength="25" required="required" />
						<div class="erreur">${form.erreurs['email']}</div>
						<input type="submit" value="Envoyer" class="sansLabel" />
						
					</fieldset>
					
				</form>
			</article>
			
		</section>
		
	</body>
	
</html>
	