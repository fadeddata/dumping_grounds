$(document).ready(function(){

    $("a")
    .filter(".clickme")
    .click(function(){
	alert("You are now leaving the site.");
    })
    .end()
    .filter(".hideme")
    .click(function(){
	$(this).hide();
	return false;
    })
    .end();

});
