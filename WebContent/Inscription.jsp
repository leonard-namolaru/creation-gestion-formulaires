<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Création de formulaires • insscription</title>
		<meta charset="utf-8" />
		<link href="<c:url value="/CSS/style_commun.css"/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/CSS/Page_daccueil_utilisateur_pas_connecte.css"/>" rel="stylesheet" type="text/css" />
		<script src="<c:url value="/JS/page_accueil.js"/>" type="text/javascript"></script>		
	</head>

	<body>
		<header> <%-- L'en-tête général --%>
			<h1>Création de formulaires</h1> 
			<p class="connexion_ins"> <a href="<c:url value="/connexion"/>"> Connexion </a>  </p>
		</header>
		
		<%-- Navigation --%>
		<nav> 
			<ul>						
				<li><a href="<c:url value="/index.jsp"/>">Retour à la page d'accueil</a></li>
			</ul>
		</nav>
		
		<section>
			<h2>Inscription</h2>
	
			<article id="inscription">
        	 	<form method="post" action="<c:url value="/page-inscription"/>">
            		<fieldset>
              			<legend>Inscription</legend>
              			
              			<p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
                                				
               			<label for="nom">Nom :</label>
               			<input type="text" id="nom" name="nom" autofocus="autofocus"/>
               			<div class="erreur">${form.erreurs['nom_prenom']}</div><br />
               			
               			<label for="prenom">Prénom :</label>
               			<input type="text" id="prenom" name="prenom"  />
               			<div class="erreur">${form.erreurs['nom_prenom']}</div><br />
               			
               			<label for="email">Adresse e-mail :</label>
               			<input type="email" id="email" name="email"/>
               			<div class="erreur">${form.erreurs['email']}</div> <br />
                                                
               			<label for="motdepasse">Mot de passe :</label>
               			<input type="password" id="motdepasse" name="motdepasse"  />
               			<div class="erreur">${form.erreurs['motdepasse']}</div> <br />
               			
               			<label for="confirmation_mot_de_passe">Confirmation du mot de passe :</label>
               			<input type="password" id="confirmation" name="confirmation" />
               			<div class="erreur">${form.erreurs['confirmation']}</div> <br />
               			
               			<label>Sexe :</label> <br />
               			<input type="radio" id="homme" name="sexe" value="0" />
               			<label for="homme">Homme</label>
               			<input type="radio" id="femme" name="sexe" value="1" />
               			<label for="femme">Femme</label>
               			<div class="erreur">${form.erreurs['sexe']}</div>
                                                
               			<br />
               			<label for="date_naissance">Date de naissance :</label>
               			<input type="date" id="date_naissance" name="date_naissance" />
                       	<div class="erreur">${form.erreurs['date_naissance']}</div> 
                        
                        <div class="button_center">
               				<input type="submit" value="Inscription" />                        
               				<input type="reset" value="Reset" />
               			</div>
            		</fieldset>
            		
            	</form>
            	
            </article>
            
		</section>
		
	</body>
	
</html>