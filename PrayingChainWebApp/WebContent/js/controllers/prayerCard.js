var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("prayerCard", ['prayerServices', '$location', 'prayerServices', '$routeParams','$route', function(prayerServices, $location, prayerServices, $routeParams,$route) {
	var self = this;
	
	function inicializarDatos(data){
		//Orador
		self.prayer = {};
		self.prayer.uid = data.Prayer.uid;
		self.prayer.name = data.Prayer.name;
		self.prayer.email = data.Prayer.email
		self.prayer.country = data.Prayer.ownCountry ? "Sí" : "No";
		self.prayer.visibility = data.Prayer.hidden ? "Sí" : "No";
		self.prayer.phone = data.Prayer.phone;
		self.prayer.pseudonym = data.Prayer.pseudonym;
		self.prayer.notes = data.Prayer.notes == "" ? "No hay notas que mostrar" : data.Prayer.notes;
		self.unchangedPrayer = angular.copy(self.prayer);
		
		//Turno
		self.turns = data.turns;
		self.editing = false;
		self.editingTurn = [];
		self.creatingNewTurn = false;
		self.DayOfWeek = inicializarDias();
		self.horas = inicializarHoras();
		self.status = inicializarStatus();
		self.unchangedTurns = angular.copy(self.turns);
		self.alreadyANew = false;
		self.NewTurn = [];
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
	
	function prayerHasChanged(){
		if (angular.toJson(self.prayer) !== angular.toJson(self.unchangedPrayer)){
			return true;
		} else {
			return false;
		}
	};

	function turnsHaveChanged(){
		if (angular.toJson(self.turns) !== angular.toJson(self.unchangedTurns)){
			return true;
		} else {
			return false;
		}
	};
	
	function inicializarDias(){
		var dias = [];
		monday = {value:'Lunes',key:'monday'};
		tuesday = {value:'Martes',key:'tuesday'};
		wednesday = {value:'Miércoles',key:'wednesday'};
		thursday = {value:'Jueves',key:'thursday'};
		friday = {value:'Viernes',key:'friday'};
		saturday = {value:'Sábado',key:'saturday'};
		sunday = {value:'Domingo',key:'sunday'};
		dias.push(monday);
		dias.push(tuesday);
		dias.push(wednesday);
		dias.push(thursday);
		dias.push(friday);
		dias.push(saturday);
		dias.push(sunday);
		return dias;
	};
	
	function inicializarHoras(){
		var horas = [];
		var turno = {key:'00:00am', value:'00:00am'};
		horas.push(turno);
		var turno = {key:'00:30am', value:'00:30am'};
		horas.push(turno);
		var turno = {key:'01:00am', value:'01:00am'};
		horas.push(turno);
		var turno = {key:'01:30am', value:'01:30am'};
		horas.push(turno);
		var turno = {key:'02:00am', value:'02:00am'};
		horas.push(turno);
		var turno = {key:'02:30am', value:'02:30am'};
		horas.push(turno);
		var turno = {key:'03:00am', value:'03:00am'};
		horas.push(turno);
		var turno = {key:'03:30am', value:'03:30am'};
		horas.push(turno);
		var turno = {key:'04:00am', value:'04:00am'};
		horas.push(turno);
		var turno = {key:'04:30am', value:'04:30am'};
		horas.push(turno);
		var turno = {key:'05:00am', value:'05:00am'};
		horas.push(turno);
		var turno = {key:'05:30am', value:'05:30am'};
		horas.push(turno);
		var turno = {key:'06:00am', value:'06:00am'};
		horas.push(turno);
		var turno = {key:'06:30am', value:'06:30am'};
		horas.push(turno);
		var turno = {key:'07:00am', value:'07:00am'};
		horas.push(turno);
		var turno = {key:'07:30am', value:'07:30am'};
		horas.push(turno);
		var turno = {key:'08:00am', value:'08:00am'};
		horas.push(turno);
		var turno = {key:'08:30am', value:'08:30am'};
		horas.push(turno);
		var turno = {key:'09:00am', value:'09:00am'};
		horas.push(turno);
		var turno = {key:'09:30am', value:'09:30am'};
		horas.push(turno);
		var turno = {key:'10:00am', value:'10:00am'};
		horas.push(turno);
		var turno = {key:'10:30am', value:'10:30am'};
		horas.push(turno);
		var turno = {key:'11:00am', value:'11:00am'};
		horas.push(turno);
		var turno = {key:'11:30am', value:'11:30am'};
		horas.push(turno);
		var turno = {key:'12:00pm', value:'12:00pm'};
		horas.push(turno);
		var turno = {key:'12:30pm', value:'12:30pm'};
		horas.push(turno);
		var turno = {key:'13:00pm', value:'13:00pm'};
		horas.push(turno);
		var turno = {key:'13:30pm', value:'13:30pm'};
		horas.push(turno);
		var turno = {key:'14:00pm', value:'14:00pm'};
		horas.push(turno);
		var turno = {key:'14:30pm', value:'14:30pm'};
		horas.push(turno);
		var turno = {key:'15:00pm', value:'15:00pm'};
		horas.push(turno);
		var turno = {key:'15:30pm', value:'15:30pm'};
		horas.push(turno);
		var turno = {key:'16:00pm', value:'16:00pm'};
		horas.push(turno);
		var turno = {key:'16:30pm', value:'16:30pm'};
		horas.push(turno);
		var turno = {key:'17:00pm', value:'17:00pm'};
		horas.push(turno);
		var turno = {key:'17:30pm', value:'17:30pm'};
		horas.push(turno);
		var turno = {key:'18:00pm', value:'18:00pm'};
		horas.push(turno);
		var turno = {key:'18:30pm', value:'18:30pm'};
		horas.push(turno);
		var turno = {key:'19:00pm', value:'19:00pm'};
		horas.push(turno);
		var turno = {key:'19:30pm', value:'19:30pm'};
		horas.push(turno);
		var turno = {key:'20:00pm', value:'20:00pm'};
		horas.push(turno);
		var turno = {key:'20:30pm', value:'20:30pm'};
		horas.push(turno);
		var turno = {key:'21:00pm', value:'21:00pm'};
		horas.push(turno);
		var turno = {key:'21:30pm', value:'21:30pm'};
		horas.push(turno);
		var turno = {key:'22:00pm', value:'22:00pm'};
		horas.push(turno);
		var turno = {key:'22:30pm', value:'22:30pm'};
		horas.push(turno);
		var turno = {key:'23:00pm', value:'23:00pm'};
		horas.push(turno);
		var turno = {key:'23:30pm', value:'23:30pm'};
		horas.push(turno);
		return horas;
	};
	
	function inicializarStatus(){
		var statues = [];
		var stat = {key:'accepted', value:'Aceptado'};
		statues.push(stat);
		var stat = {key:'cancelled', value:'Cancelado'};
		statues.push(stat);
		var stat = {key:'NotCommitted', value:'No Comprometido'};
		statues.push(stat);
		return statues;
	};

	self.editPrayer = function(){
		self.editing = true;
	};
	
	self.cancelEditing = function(){
		self.editing = false;
		self.prayer = angular.copy(self.unchangedPrayer);
	};
	
	self.saveChanges = function(){
		if (prayerHasChanged()){
			if (angular.toJson(self.prayer) !== angular.toJson(self.unchangedPrayer)){
				var promise = prayerServices.updatePrayer(self.prayer);
				promise.then(function(dataOut) {}, function(error) {
					bootbox.alert({size:'small', message: 'Hubo un error al tratar de actualizar los datos del orador'});
					console.error("Hubo un error al tratar de actualizar los datos de este orador: " + angular.toJson(self.prayer));
					console.error("El error fue: " + error.status + ": " + error.statusText);
				}).finally(function(){
					$route.reload();
				});
			}
			self.unchangedPrayer = angular.copy(self.prayer);
		}
		self.editing = false;
	};
	
	self.editTurn = function(uid){
		self.editingTurn[uid] = true;
	};
	
	self.cancelEditingTurn = function(uid){
		if (uid=='Auto') {
			var index = self.turns.indexOf(self.NewTurn);
			self.turns.splice(index,1);
			self.alreadyANew = false;
			self.NewTurn = [];
		} else {
			self.editingTurn[uid] = false;
		}
		self.turns = angular.copy(self.unchangedTurns);
	};
	
	self.saveTurnChanges = function(uid){
		if (turnsHaveChanged()){
			for (index in self.turns){
				if (angular.toJson(self.turns[index]) !== angular.toJson(self.unchangedTurns[index])){
					var promise = prayerServices.updateTurn(self.turns[index]);
					promise.then(function(dataOut) {}, function(error) {
						bootbox.alert({size:'small', message: 'Hubo un error al tratar de actualizar los datos del turno'});
						console.error("Hubo un error al tratar de actualizar los datos de este turno: " + angular.toJson(self.turns[index]));
						console.error("El error fue: " + error.status + ": " + error.statusText);
					}).finally(function(){
						$route.reload();
					});
				}
			}
			self.unchangedTurns = angular.copy(self.turns);
		}
		self.editingTurn[uid] = false;
	};
	
	self.addTurn = function(){
		if (self.alreadyANew){
			bootbox.alert({size:'small', message: 'Ya está creando un orador. Termine ese proceso para crear más oradores...'});	
		} else {
			self.alreadyANew = true;
			self.NewTurn = {uid:'Auto',dow:'',turn:'',status:''};
			self.turns.push(self.NewTurn);
			self.editingTurn['Auto'] = true;
		}
	};

	getPrayerAndTurns();
}]);