<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Création de formulaires • ${categorie.titre}</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link href="<c:url value="/CSS/style_commun.css"/>" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/CSS/RepondreFormulaire.css"/>" rel="stylesheet" type="text/css" />
		
		<script src="<c:url value="/JS/pageAlert.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/JS/affichageQuestions.js"/>" type="text/javascript"></script>
		<script src="<c:url value="/JS/alert.js"/>" type="text/javascript"></script>
	</head>
	
	<body onLoad="init();">
	
		<header> <!-- L'en-tête général -->
			<h1>Création de formulaires</h1>
			<p class="deconnexion"> <a href="<c:url value="/deconnexion"/>"> Déconnexion </a></p>
		</header>
		
		<nav> <!-- Navigation -->
			<ul>
				<li><a href="<c:url value="/AccueilUtilisateur"/>">Page d'accueil</a></li>
				<li><a href="<c:url value="/mes-donnees-personnelles"/>">Mon profil</a></li>
			</ul>
		</nav>
			
		<section>
            <c:if test="${!empty sessionScope.sessionUtilisateur}"> <%-- Vérification de la présence d'un objet utilisateur en session --%>
            	<%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
            	<p class="succes_utilisateur"> <strong> Vous êtes connecté(e) avec l'adresse </strong> : ${sessionScope.sessionUtilisateur.email}</p>
            </c:if>      
			
			<h2>${categorie.titre}</h2> <!-- Titre de la ctégorie -->
			<p class="description">${categorie.description}</p> <!-- Description de la catégorie -->
		
			<article id="parametres">
				<!-- Affiche les informations succès ou erreurs des champs après que l'utilisateur a répondu-->
				<div class="${empty form.erreurs ? 'succes': 'erreur'}">${form.resultat}</div>
		
				<form id="form_parametres-categorie" method="post" action="<c:url value="/repondre-formulaire?id=${categorie.id}"/>">					
				<c:set var="numeroQuestion" value="${0}"/>	
				<c:forEach var = "i" begin = "1" end = "${categorie.nbGroupes}">
					<fieldset class="groupe" id="groupe${i}">
							<legend> </legend>
							<c:forEach var="question" items="${questions}"> <!-- Affiche les questions de la catégorie -->
								<c:if test="${question.questionGroupe == i}">
									<c:set var="numeroQuestion" value="${numeroQuestion + 1}"/>	
									<div class="question"> <!-- Ajout d'un "*" si c'est une question obligatoire -->
									<div class="numeroQuestion"> Question ${numeroQuestion} </div>
									<div class="labelQuestion"> ${question.label} <c:if test="${question.obligatoire==1}"><span class="obligatoire">*</span></c:if></div>
									
									<div class="proposition"> <!-- Affiche les propositions d'une question de la catégorie -->
												
										<c:choose>
											<%-- ***************************************** Type de question -> RADIO ***************************************** --%>
	    									<c:when test="${question.type == 1 }">
												<c:forEach var="proposition" items="${propositions}">
	    											<c:set var="eq_val" value="false"></c:set>
	    											
	    											<c:forEach var="reponse" items="${reponses}"> <!-- Trouver la réponse correspondante -->
	    												<c:if test="${proposition.id eq reponse.propositionId }">
                 												<c:set var="eq_val" value="true"></c:set>
                 												<c:set var="reponseLogId" value="${reponse.reponseLogId}"></c:set>
            											 	</c:if>	
													</c:forEach>
													
	    											<c:if test="${question.id == proposition.questionId}">
	    										    	<p>
	       									 				<input type="radio" id="prop${proposition.id}" name="${question.id}" value="${proposition.label}" <c:if test="${eq_val}">checked="checked"</c:if> />
	       									 				<label for="prop${proposition.id}">${proposition.label}</label>
	       									 			</p>
	       									 		</c:if>
	       										</c:forEach>
	       										
	       										<c:forEach items="${form.erreurs}" var="form"> <!-- Affiche l'erreur de cette question -->
	       											<c:if test="${form.key == question.id}"><div class="erreur">${form.value}</div></c:if>
	       										</c:forEach>
	    									</c:when>
	    									
	    									<%-- ***************************************** Type de question -> CHECKBOX ***************************************** --%>
	    									<c:when test="${question.type == 2 }">
	    										<c:forEach var="proposition" items="${propositions}">
	    											<c:set var="eq_val" value="false"></c:set>
	    											
	    											<!-- Trouver la réponse correspondante -->
	    											<c:forEach var="reponse" items="${reponses}">
	    												<c:if test="${proposition.id eq reponse.propositionId }">
                 												<c:set var="eq_val" value="true"></c:set> 
                 												<c:set var="reponseLogId" value="${reponse.reponseLogId}"></c:set>
            											 	</c:if>	
													</c:forEach>
													
	    										    <c:if test="${question.id == proposition.questionId}">
	    												<p>	    													
	       									 				<input type="checkbox" id="prop${proposition.id}" name="${question.id}" value="${proposition.label}" <c:if test="${eq_val}">checked="checked"</c:if> />
	       									 				<label for="prop${proposition.id}">${proposition.label}</label>
	       									 			</p>
	       									 		</c:if>
	    										</c:forEach>
	    										
	       										<c:forEach items="${form.erreurs}" var="form"> <!-- Affiche l'erreur de cette question -->
	       											<c:if test="${form.key == question.id}"><div class="erreur">${form.value}</div></c:if>
	       										</c:forEach>
	    									</c:when>
	    									
	    									<%-- ***************************************** Type de question -> SELECT ***************************************** --%>
	    									<c:when test="${question.type == 3 }">
	    										<select name="${question.id}">
	    											<c:forEach var="proposition" items="${propositions}">
	    												<c:set var="eq_val" value="false"></c:set>
	    											
	    												<c:forEach var="reponse" items="${reponses}"> <!-- Trouver la réponse correspondante -->
	    													<c:if test="${proposition.id eq reponse.propositionId }">
                 													<c:set var="eq_val" value="true"></c:set> 
                 													<c:set var="reponseLogId" value="${reponse.reponseLogId}"></c:set>
            											 		</c:if>	
            											 </c:forEach>
            											 	
	    										    	<c:if test="${question.id == proposition.questionId}">       									 				
	       									 				<option id="prop${proposition.id}" value="${proposition.label}" <c:if test="${eq_val}">selected="selected"</c:if>>${proposition.label}</option>
	       									 			</c:if>
	       									 		</c:forEach>
	       									 	</select> 
	       									 	
	       										<c:forEach items="${form.erreurs}" var="form"> <!-- Affiche l'erreur de cette question -->
	       											<c:if test="${form.key == question.id}"><div class="erreur">${form.value}</div></c:if>
	       										</c:forEach>
	    									</c:when>
	    									
	    									<%-- ***************************************** Type de question -> EMAIL ***************************************** --%>
	    									<c:when test="${question.type == 4 }">
	    										<c:forEach var="proposition" items="${propositions}">
	    											<c:set var="eq_val" value="false"></c:set>
	    											
    												<c:forEach var="reponse" items="${reponses}"> <!-- Trouver la réponse correspondante -->
    													<c:if test="${proposition.id eq reponse.propositionId }">
                												<c:set var="eq_val" value="true"></c:set> 
                												<c:set var="valeurReponse" value="${reponse.valeurReponse}"></c:set>
                												<c:set var="reponseLogId" value="${reponse.reponseLogId}"></c:set>
           											 	</c:if>	
           											</c:forEach>
            											 	
	    										    <c:if test="${question.id == proposition.questionId}">
	    										    	<label for="prop${proposition.id}">${proposition.label}</label>	<br />	    										    		
	       									 			<input type="text" id="prop${proposition.id}" name="${question.id}" placeholder="Ecrire la réponse ici" <c:if test="${eq_val}">value="<c:out value="${valeurReponse}"/>"</c:if>/>
	       									 		</c:if>
	    										</c:forEach>
	    										
	       										<c:forEach items="${form.erreurs}" var="form"> <!-- Affiche l'erreur de cette question -->
	       											<c:if test="${form.key == question.id}"><div class="erreur">${form.value}</div></c:if>
	       										</c:forEach>
	    									</c:when>
	    									
	    									<%-- ***************************************** Type de question -> DATE ***************************************** --%>
	    									<c:when test="${question.type == 5 }">
	    										<c:forEach var="proposition" items="${propositions}">
	    											<c:set var="eq_val" value="false"></c:set>
	    											
    												<c:forEach var="reponse" items="${reponses}"> <!-- Trouver la réponse correspondante -->
    													<c:if test="${proposition.id eq reponse.propositionId }">
                												<c:set var="eq_val" value="true"></c:set> 
                												<c:set var="valeurReponse" value="${reponse.valeurReponse}"></c:set>
                												<c:set var="reponseLogId" value="${reponse.reponseLogId}"></c:set>
           											 	</c:if>	
           											</c:forEach>
           											
	    										    <c:if test="${question.id == proposition.questionId}">
	    										    	<label for="prop${proposition.id}">${proposition.label}</label>		    										    		
	       									 			<input type="date" id="prop${proposition.id}" name="${question.id}" <c:if test="${eq_val}">value="<c:out value="${valeurReponse}"/>"</c:if> />
	       									 		</c:if>
	    										</c:forEach>
	    										
	       										<c:forEach items="${form.erreurs}" var="form"> <!-- Affiche l'erreur de cette question -->
	       											<c:if test="${form.key == question.id}"><div class="erreur">${form.value}</div></c:if>
	       										</c:forEach>
	    									</c:when>
	    									
	    									<%-- ***************************************** Type de question -> EMAIL ***************************************** --%>
	    									<c:when test="${question.type == 6 }">
	    										<c:forEach var="proposition" items="${propositions}">
	    											<c:set var="eq_val" value="false"></c:set>
	    											
    												<c:forEach var="reponse" items="${reponses}"> <!-- Trouver la réponse correspondante -->
    													<c:if test="${proposition.id eq reponse.propositionId }">
                												<c:set var="eq_val" value="true"></c:set> 
                												<c:set var="valeurReponse" value="${reponse.valeurReponse}"></c:set>
                												<c:set var="reponseLogId" value="${reponse.reponseLogId}"></c:set>
           											 	</c:if>	
           											</c:forEach>
           											
	    										   <c:if test="${question.id == proposition.questionId}">
	    										    	<label for="prop${proposition.id}">${proposition.label}</label>		    										    				    										   
	       									 			<input type="email" id="prop${proposition.id}" name="${question.id}" placeholder="Ecrire un adresse email" <c:if test="${eq_val}">value="<c:out value="${valeurReponse}"/>"</c:if> />
	       									 		</c:if>
	    										</c:forEach>
	    										
	       										<c:forEach items="${form.erreurs}" var="form"> <!-- Affiche l'erreur de cette question -->
	       											<c:if test="${form.key == question.id}"><div class="erreur">${form.value}</div></c:if>
	       										</c:forEach>
	    									</c:when>
	    									
	    									<%-- ***************************************** Type de question -> TELEPHONE ***************************************** --%>
	    									<c:when test="${question.type == 7 }">
	    										<c:forEach var="proposition" items="${propositions}">
	    											<c:set var="eq_val" value="false"></c:set>
	    											
    												<c:forEach var="reponse" items="${reponses}"> <!-- Trouver la réponse correspondante -->
    													<c:if test="${proposition.id eq reponse.propositionId }">
                												<c:set var="eq_val" value="true"></c:set> 
                												<c:set var="valeurReponse" value="${reponse.valeurReponse}"></c:set>
                												<c:set var="reponseLogId" value="${reponse.reponseLogId}"></c:set>
           											 	</c:if>	
           											</c:forEach>
           											
	    										   	<c:if test="${question.id == proposition.questionId}">
	    										    	<label for="prop${proposition.id}">${proposition.label}</label>		    										    					       									 		
		       									 		<input type="tel" id="prop${proposition.id}" name="${question.id}" placeholder="Ecrire un numéro téléphone" <c:if test="${eq_val}">value="<c:out value="${valeurReponse}"/>"</c:if> />
	       									 		</c:if>
	    										</c:forEach>
	    										
	       										<c:forEach items="${form.erreurs}" var="form"> <!-- Affiche l'erreur de cette question -->
	       											<c:if test="${form.key == question.id}"><div class="erreur">${form.value}</div></c:if>
	       										</c:forEach>
	    									</c:when>
										</c:choose>				
									</div>
								</div>
								</c:if>
							</c:forEach>	
						</fieldset>
					</c:forEach>
					<input type="submit" onclick="return repondreConfirm()" value="Répondre" id="submit_button" class="sansLabel" />						
				</form>
			</article>		
			<c:if test="${categorie.nbGroupes > 1}">
				<div class="page">
					<a href="#" onClick="firstGroup();">Première page</a> 
					<a href="#" onClick="changeGroup1();"> &lt; Précédente</a>
					<a href="#" onClick="changeGroup2();">Suivante &gt;</a>
					<a href="#" onClick="lastGroup();">Dernière page</a>
	 			</div>
	 		</c:if>	
		</section>	
	</body>
</html>