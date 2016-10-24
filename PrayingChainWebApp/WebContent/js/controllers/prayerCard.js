var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("prayerCard", ['prayerServices', '$location', 'prayerServices', function(prayerServices, $location, prayerServices) {
	var self = this;
	
	function inicializarDatos(data){
		self.prayerID = 1089;
		self.prayerName = "Oscar";
		self.prayerEmail = "oscar.ibafer@gmail.com";
		self.prayerCountry = "Sí";
		self.prayerVisibility = "No";
		self.prayerPhone = "+34.696.95.35.87";
		self.prayerPseudonym = "Oscar";
		self.turns = [];
		var turn1 = {};
		turn1.id = 1089;
		turn1.day = "Lunes";
		turn1.hour = "20:00";
		turn1.status = "Aceptado";
		self.turns.push(turn1);
		var turn2 = {};
		turn2.id = 1090;
		turn2.day = "Martes";
		turn2.hour = "21:00";
		turn2.status = "Cancelado";
		self.turns.push(turn2);
	};
	
	function getPrayerAndTurns(){
		var locationParams = $location.search();
		var prayerID = locationParams.prayerID;
		if (!prayerID) {
			bootbox.alert({size:'small', message: 'Esta página debe ser solicitada adjuntando el ID de un orador'});
		} else {
			var promise = prayerServices.getPrayerAndTurns(prayerID);
			promise.then(function(dataOut) {
				inicializarDatos(dataOut.data);
			}, function(error) {
				bootbox.alert({size:'small', message: 'Hubo un error al tratar de solicitar los datos del orador'});
			}).finally(function(){
			});

		}
	};
	
	getPrayerAndTurns();
}]);