var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("newPrayerController", [function() {
	var self = this;
	
	inicializarDatos();
	
	//FUNCIONES SCOPE
	self.crearOrador = function (isValid){
		console.log("El formulario " + (isValid ? "es " : "no es ") + "válido");
	};
	
	//FUNCIONES PRIVADAS
	function inicializarDatos(){
		self.visibilidades = ["Oculto","Público"];
		self.daysOfWeek = ["Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábados", "Domingos"];
		self.paises = ["España", "Otro País"];
		self.turnos = ["00:00am","00:30am","01:00am","01:30am","02:00am","02:30am","03:00am","03:30am","04:00am","04:30am",
		               "05:00am","05:30am","06:00am","06:30am","07:00am","07:30am","08:00am","08:30am","10:00am","10:30am",
		               "11:00am","11:30am","12:00pm","12:30pm","13:00pm","13:30pm","14:00pm","14:30pm","15:00pm","15:30pm",
		               "16:00pm","16:30pm","17:00pm","17:30pm","18:00pm","18:30pm","19:00pm","19:30pm","20:00pm","20:30pm",
		               "21:00pm","21:30pm","22:00pm","22:30pm","23:00pm","23:30pm"];
	};
	
}]);