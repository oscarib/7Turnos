var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("prayerCard", ['prayerServices', '$location', 'prayerServices', '$routeParams', function(prayerServices, $location, prayerServices, $routeParams) {
	var self = this;
	
	function inicializarDatos(data){
		self.prayerID = data.Prayer.uid;
		self.prayerName = data.Prayer.name
		self.prayerEmail = data.Prayer.email
		self.prayerCountry = data.Prayer.ownCountry ? "X" : "";
		self.prayerVisibility = data.Prayer.hidden ? "" : "X";
		self.prayerPhone = data.Prayer.phone;
		self.prayerPseudonym = data.Prayer.pseudonym;
		self.notes = data.Prayer.notes == "" ? "No hay notas que mostrar" : data.Prayer.notes;
		self.turns = data.turns;
	};
	
	function getPrayerAndTurns(){
		var prayerID = $routeParams.prayerID;
		if (!prayerID) {
			bootbox.alert({size:'small', message: 'Esta página debe ser solicitada adjuntando el ID de un orador'});
		} else {
			var promise = prayerServices.getPrayerAndTurns(prayerID);
			promise.then(function(dataOut) {
				inicializarDatos(dataOut.data);
			}, function(error) {
				bootbox.alert({size:'small', message: 'Hubo un error al tratar de solicitar los datos del orador de n.' + prayerID});
			}).finally(function(){
			});

		}
	};
	
	getPrayerAndTurns();
}]);