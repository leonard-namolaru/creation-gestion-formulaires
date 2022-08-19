<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Création de formulaires • connexion</title>
		<meta charset="utf-8" />
		<link href="<c:url value="/CSS/style_commun.css"/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/CSS/Page_daccueil_utilisateur_pas_connecte.css"/>" rel="stylesheet" type="text/css" />
		<script src="<c:url value="/JS/page_accueil.js"/>" type="text/javascript"></script>		
	</head>

	<body>
		<header> <%-- L'en-tête général --%>
			<h1>Création de formulaires</h1> 
			<p class="inscription"> <a href="<c:url value="/page-inscription"/>"> Inscription </a>  </p>
		</header>
		
		<%-- Navigation --%>
		<nav> 
			<ul>						
				<li><a href="<c:url value="/index.jsp"/>">Retour à la page d'accueil</a></li>
			</ul>
		</nav>
		
		<section>
			<h2>Connexion</h2>
					
			<article id="connexion">
				<form method="post" action="<c:url value="/connexion"/>">
					<fieldset>
						<legend>Connexion</legend>	
						<p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
											
						<label for="connexion_email">Adresse e-mail :</label>
						<input type="email" id="connexion_email" name="connexion_email" required="required" />
						<div class="erreur">${form.erreurs['connexion_email']}</div>
						<label for="connexion_mot_de_passe">Mot de passe :</label>
						<input type="password" id="connexion_mot_de_passe" name="connexion_mot_de_passe" required="required" />
						<div class="erreur">${form.erreurs['connexion_mot_de_passe']}</div>
						
						<input type="checkbox"  id="lastconnect" name="lastconnect" />
						<label for="lastconnect">Se souvenir de moi</label>
						
						<div class="button_center">
						<input type="submit" value="Connexion" class="sansLabel" />
						<a href="<c:url value="/motdepasse-oublie"/>"> <input type="button" value="Mot de passe oublié ?" class="sansLabel" /> </a>
						</div>
					</fieldset>
				</form>
			</article>
	
            
		</section>
	</body>
</html>