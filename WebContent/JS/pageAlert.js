/**
 * Alerter l'utilisateur quand nous sommes à la première et dernière page
 * 
 * @author Huihui Huang
 */

function alertPremierePage(start) {
	if (start == 0) {
		window.alert ("Nous sommes déjà à la première page");
	}	
}

function alertDernPage(start, dern) {
	if (start == dern) {
		window.alert ("Nous sommes déjà à la dernière page");
	}
}
