$(document).ready(function(){
	$(".simple_confirmSign").click(function(){
		button = this;
		alertify.confirm("¿Quieres entrar en esta actividad?", function(e){
			if(e){
				alertify.alert("Has entrado a la actividad: " + $(button).data('coursename'));
				document.location = "courses/" + $(button).data('idschedule') + "/add";
			}else{
				alertify.alert("No te has inscrito");
			}
		});
	});
	$(".simple_confirmQuit").click(function(){
		button = this;
		alertify.confirm("De verdad quieres salirte?", function(e){
			if(e){
				alertify.alert("Eliminado de la actividad: " + $(button).data('coursename'));
				document.location = "courses/" + $(button).data('idschedule') + "/delete";
			}else{
				alertify.alert("Ok, sigues dentro");
			}
		});
	});
});
