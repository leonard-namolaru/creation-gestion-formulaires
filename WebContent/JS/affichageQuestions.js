/**
* affichageQuestions.js
* @author  Leonard Namolaru
* Travail_effectué  : Tout le code qui apparaît dans ce fichier
* Projet : Sujet L2N - Création de formulaires (web) - Projets de programmation 2019-2020, Licence informatique - Paris Descartes
*   
*/
var groupe;
var lesGroupes;

function init(){
	lesGroupes = document.getElementsByClassName("groupe");
	groupe = 1;
	
	if(lesGroupes.length != 1){
		document.getElementById("submit_button").style.display = "none";
		for(var i = 1 ; i < lesGroupes.length ; i++)
			lesGroupes[i].style.display = "none";			
	}
	
	changeLegend();
}

function changeLegend(){
	document.getElementsByTagName("legend")[groupe - 1].innerHTML = "Page " + groupe + " / " + lesGroupes.length + " ";
}

function firstGroup() {
	if (groupe != 1)
	{
		groupe = 1;		
		lesGroupes[0].style.display = "block";
		
		for(var i = 1 ; i < lesGroupes.length ; i++)
			lesGroupes[i].style.display = "none";
		
		document.getElementById("submit_button").style.display = "none";
	}		
	else
	{
		alert("Nous sommes déjà à la première page");
	}
	
	changeLegend();
}

function changeGroup1() {
	if (groupe > 1)
	{
		groupe--;				
		for(var i = 0 ; i < lesGroupes.length ; i++){
			if(i == (groupe - 1) )
				lesGroupes[i].style.display = "block";
			else
				lesGroupes[i].style.display = "none";
		}	
		
		document.getElementById("submit_button").style.display = "none";
	}		
	else
	{
		alert("Nous sommes à la première page");
	}
	
	changeLegend();
}

function changeGroup2() {
	if (groupe < lesGroupes.length )
	{
		groupe++;				
		for(var i = 0 ; i < lesGroupes.length ; i++){
			if(i == (groupe - 1) )
				lesGroupes[i].style.display = "block";
			else
				lesGroupes[i].style.display = "none";
		}
		
		if(groupe == lesGroupes.length)
			document.getElementById("submit_button").style.display = "block";
		else
			document.getElementById("submit_button").style.display = "none";			
	}		
	else
	{
		alert("Nous sommes à la dernière page");
	}
	
	changeLegend();
}

function lastGroup() {
	if (groupe != lesGroupes.length )
	{
		groupe = lesGroupes.length;		
		lesGroupes[groupe - 1].style.display = "block";
		
		for(var i = 0 ; i < (lesGroupes.length - 1) ; i++)
			lesGroupes[i].style.display = "none";
		
		document.getElementById("submit_button").style.display = "block";
	}		
	else
	{
		alert("Nous sommes déjà à la dernière page");
	}
	
	changeLegend();
}