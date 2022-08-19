<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link href="<c:url value="/CSS/style_commun.css"/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/CSS/Page_daccueil_utilisateur_pas_connecte.css"/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/CSS/modifcationUtilisateur.css"/>" rel="stylesheet" type="text/css" />
		<title>Création de formulaires • modifier mon mot de passe</title>
	</head>

	<body>
	
		<%-- L'en-tête général --%>
		<header> 
			<h1>Création de formulaires</h1> 
		</header>
		
		<section>
		
			<article id= "motDePasseOublie">
			
				<%-- Accès à cette page quand l'utilisateur clique sur URL envoyé dans son adresse e-mail --%>
				<form  method="post" action="<c:url value="/resetMotDePasse"/>">
					<fieldset>
						<legend>Modifier mon mot de passe</legend>
						
						<%-- Affiche si succès ou échec de la modification du mot de passe --%>
						<div class="${empty form.erreurs ? 'succes': 'erreur'}">${form.resultat}</div>
						
						<%-- Définir un nouveau mot de passe --%>
						<label for="email">Adresse e-mail :</label>
               			<input type="email" readonly="readonly" id="email" name="email" value="<c:out value="${param.email}"/>"/>
               			<div class="erreur">${form.erreurs['email']}</div> <br />
               			
						<label for="motdepasse">Nouveau mot de passe :</label>
               			<input type="password" id="motdepasse" name="motdepasse"  />
               			<div class="erreur">${form.erreurs['motdepasse']}</div> <br />
               			
               			<label for="confirmation_mot_de_passe">Confirmation du mot de passe :</label>
               			<input type="password" id="confirmation" name="confirmation" />
               			<div class="erreur">${form.erreurs['confirmation']}</div> <br />
               			
						<input type="submit" value="Modifier" class="sansLabel" />
						
					</fieldset>
				</form>
			</article>
		</section>
	</body>
</html>
	