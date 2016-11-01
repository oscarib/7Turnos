var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("prayerCard", ['prayerServices', '$location', 'prayerServices', '$routeParams', function(prayerServices, $location, prayerServices, $routeParams) {
	var self = this;
	
	function inicializarDatos(data){
		self.prayerID = data.Prayer.uid;
		self.prayerName = data.Prayer.name
		self.prayerEmail = data.Prayer.email
		self.prayerCountry = data.Prayer.ownCountry ? "Sí" : "No";
		self.prayerCountryClass = self.prayerCountry == "Sí" ? "Green" : "Red";
		self.prayerVisibility = data.Prayer.hidden ? "Sí" : "No";
		self.prayerVisibilityClass = self.prayerVisibility == "Sí" ? "Red" : "Green";
		self.prayerPhone = data.Prayer.phone;
		self.prayerPseudonym = data.Prayer.pseudonym;
		self.notes = data.Prayer.notes == "" ? "No hay notas que mostrar" : data.Prayer.notes;
		self.turns = data.turns;
		self.editing = false;
		self.editingTurn = [];
		self.creatingNewTurn = false;
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
	
	self.editPrayer = function(){
		self.editing = true;
	};
	
	self.cancelEditing = function(){
		//TODO: Implementar
		self.editing = false;
	};
	
	self.saveChanges = function(){
		//TODO: Implementar
		self.editing = false;
	};
	
	self.editTurn = function(uid){
		self.editingTurn[uid] = true;
	};
	
	self.cancelEditingTurn = function(uid){
		self.editingTurn[uid] = false;
	};
	
	self.saveTurnChanges = function(uid){
		//TODO: Implementar
		self.editingTurn[uid] = false;
	};

	getPrayerAndTurns();
}]);