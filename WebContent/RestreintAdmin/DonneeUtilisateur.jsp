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
		<title>Donnee utilisateur</title>
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
		
			<h2>Le profil</h2>
		
			<%-- Mise à jour des données de d'un utilisateur --%>
			<article id= "DonneeUtilisateur"> 
				<form method="post" action="<c:url value="/update-utilisateur"/>">
				
            		<fieldset>
              			<legend>Les données personnelles de "<c:out value="${utilisateur.nom}"/>"</legend>
               			<p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
                                				
               			<label for="nom">Nom :</label>
               			<input type="text" id="nom" name="nom" autofocus="autofocus" value="<c:out value="${utilisateur.nom}"/>"/> 
               			<div class="erreur">${form.erreurs['nom_prenom']}</div> <br />
               			
              
               			<label for="prenom">Prénom :</label>
               			<input type="text" id="prenom" name="prenom"  value="<c:out value="${utilisateur.prenom}"/>"/>
               			<div class="erreur">${form.erreurs['nom_prenom']}</div>  <br />
               			
               			
               			<label for="email">Adresse e-mail :</label>
               			<input type="email" readonly="readonly" id="email" name="email" value="<c:out value="${utilisateur.email}"/>" /> <br />
                                                
               			
               			<label>Sexe :</label> 
               			<input type="radio" id="homme" name="sexe" value="0" <c:if test="${utilisateur.sexe== 0}">checked="checked"</c:if> />
               			<label for="homme">Homme</label>
               			<input type="radio" id="femme" name="sexe" value="1" <c:if test="${utilisateur.sexe== 1}">checked="checked"</c:if> />
               			<label for="femme">Femme</label>
               			<div class="erreur">${form.erreurs['sexe']}</div>
                                                
               			<br />
               			<label for="date_naissance">Date de naissance :</label>
               			<input type="date" id="date_naissance" name="date_naissance" value="<c:out value="${utilisateur.dateNaissance}"/>" />
                       	<div class="erreur">${form.erreurs['date_naissance']}</div> <br />
                
               			<input type="submit" onclick="return modifConfirm()" value="Modifier" />                        
            		</fieldset>
            		
            	</form>
            	
			</article>
			
		</section>
</body>
</html>