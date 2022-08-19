<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<link href="<c:url value="/CSS/style_commun.css"/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/CSS/Page_daccueil_utilisateur_pas_connecte.css"/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/CSS/modifcationUtilisateur.css"/>" rel="stylesheet" type="text/css" />
		<script src="<c:url value="/JS/alert.js"/>" type="text/javascript"></script>
		<title>Modification de mon mot de passe</title>
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
				<li><a class="active" href="<c:url value="/mes-donnees-personnelles"/>">Mon profil</a></li>
			</ul>
		</nav>
		
		<section>
		
			<h2>Le profil</h2>
		
			<article id= "modificatio_profil"> 
				<form method="post" action="<c:url value="/modifier-mon-motDePasse"/>">
            		<fieldset>
              			<legend>Modification de mon mot de passe</legend>
              			
              			<p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
               			
               			<label for="email">Adresse e-mail :</label>
               			<input type="email" readonly="readonly" id="email" name="email" value="<c:out value="${sessionUtilisateur.email}"/>" /> <br />
                                                
               			<label for="motdepasse">Mon nouveau mot de passe :</label>
               			<input type="password" id="motdepasse" name="motdepasse"/>
               			<div class="erreur">${form.erreurs['motdepasse']}</div> <br />
               			
               			<label for="confirmation_mot_de_passe">Confirmation de mon nouveau mot de passe :</label>
               			<input type="password" id="confirmation" name="confirmation"/>
               			<div class="erreur">${form.erreurs['confirmation']}</div> <br />
               			
                                                
               			<input type="submit" onclick="return modifConfirm()" value="Modifier mon mot de passe" />                        
            		</fieldset>
            		
            	</form>
            	
			</article>
			
		</section>
</body>
</html>