var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("prayerCard", ['prayerServices', '$location', 'prayerServices', '$routeParams', function(prayerServices, $location, prayerServices, $routeParams) {
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
		self.prayer.dayOfWeek = inicializarDias();
		self.unchangedPrayer = angular.copy(self.prayer);
		
		//Turno
		self.turns = data.turns;
		self.editing = false;
		self.editingTurn = [];
		self.creatingNewTurn = false;
		self.turnos = inicializarTurnos();
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
		if (JSON.stringify(self.prayer) !== JSON.stringify(self.unchangedPrayer)){
			return true;
		} else {
			return false;
		}
	};

	function turnsHaveChanged(){
		console.log(JSON.stringify(self.turns));
		console.log(JSON.stringify(self.unchangedTurns));
		if (JSON.stringify(self.turns) !== JSON.stringify(self.unchangedTurns)){
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
	
	function inicializarTurnos(){
		var turns = [];
		var turno = {key:'00:00am', value:'00:00am'};
		turns.push(turno);
		var turno = {key:'00:30am', value:'00:30am'};
		turns.push(turno);
		var turno = {key:'01:00am', value:'01:00am'};
		turns.push(turno);
		var turno = {key:'01:30am', value:'01:30am'};
		turns.push(turno);
		var turno = {key:'02:00am', value:'02:00am'};
		turns.push(turno);
		var turno = {key:'02:30am', value:'02:30am'};
		turns.push(turno);
		var turno = {key:'03:00am', value:'03:00am'};
		turns.push(turno);
		var turno = {key:'03:30am', value:'03:30am'};
		turns.push(turno);
		var turno = {key:'04:00am', value:'04:00am'};
		turns.push(turno);
		var turno = {key:'04:30am', value:'04:30am'};
		turns.push(turno);
		var turno = {key:'05:00am', value:'05:00am'};
		turns.push(turno);
		var turno = {key:'05:30am', value:'05:30am'};
		turns.push(turno);
		var turno = {key:'06:00am', value:'06:00am'};
		turns.push(turno);
		var turno = {key:'06:30am', value:'06:30am'};
		turns.push(turno);
		var turno = {key:'07:00am', value:'07:00am'};
		turns.push(turno);
		var turno = {key:'07:30am', value:'07:30am'};
		turns.push(turno);
		var turno = {key:'08:00am', value:'08:00am'};
		turns.push(turno);
		var turno = {key:'08:30am', value:'08:30am'};
		turns.push(turno);
		var turno = {key:'09:00am', value:'09:00am'};
		turns.push(turno);
		var turno = {key:'09:30am', value:'09:30am'};
		turns.push(turno);
		var turno = {key:'10:00am', value:'10:00am'};
		turns.push(turno);
		var turno = {key:'10:30am', value:'10:30am'};
		turns.push(turno);
		var turno = {key:'11:00am', value:'11:00am'};
		turns.push(turno);
		var turno = {key:'11:30am', value:'11:30am'};
		turns.push(turno);
		var turno = {key:'12:00pm', value:'12:00pm'};
		turns.push(turno);
		var turno = {key:'12:30pm', value:'12:30pm'};
		turns.push(turno);
		var turno = {key:'13:00pm', value:'13:00pm'};
		turns.push(turno);
		var turno = {key:'13:30pm', value:'13:30pm'};
		turns.push(turno);
		var turno = {key:'14:00pm', value:'14:00pm'};
		turns.push(turno);
		var turno = {key:'14:30pm', value:'14:30pm'};
		turns.push(turno);
		var turno = {key:'15:00pm', value:'15:00pm'};
		turns.push(turno);
		var turno = {key:'15:30pm', value:'15:30pm'};
		turns.push(turno);
		var turno = {key:'16:00pm', value:'16:00pm'};
		turns.push(turno);
		var turno = {key:'16:30pm', value:'16:30pm'};
		turns.push(turno);
		var turno = {key:'17:00pm', value:'17:00pm'};
		turns.push(turno);
		var turno = {key:'17:30pm', value:'17:30pm'};
		turns.push(turno);
		var turno = {key:'18:00pm', value:'18:00pm'};
		turns.push(turno);
		var turno = {key:'18:30pm', value:'18:30pm'};
		turns.push(turno);
		var turno = {key:'19:00pm', value:'19:00pm'};
		turns.push(turno);
		var turno = {key:'19:30pm', value:'19:30pm'};
		turns.push(turno);
		var turno = {key:'20:00pm', value:'20:00pm'};
		turns.push(turno);
		var turno = {key:'20:30pm', value:'20:30pm'};
		turns.push(turno);
		var turno = {key:'21:00pm', value:'21:00pm'};
		turns.push(turno);
		var turno = {key:'21:30pm', value:'21:30pm'};
		turns.push(turno);
		var turno = {key:'22:00pm', value:'22:00pm'};
		turns.push(turno);
		var turno = {key:'22:30pm', value:'22:30pm'};
		turns.push(turno);
		var turno = {key:'23:00pm', value:'23:00pm'};
		turns.push(turno);
		var turno = {key:'23:30pm', value:'23:30pm'};
		turns.push(turno);
		return turns;
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
			bootbox.alert({size:'small', message: 'Falta hacer que se graben los datos cambiados'});
			self.unchangedPrayer = angular.copy(self.prayer);
		} else {
			bootbox.alert({size:'small', message: 'No hubo cambios. No hace falta grabar nada'});
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
		self.unchangedTurns = angular.copy(self.turns);
	};
	
	self.saveTurnChanges = function(uid){
		if (turnsHaveChanged()){
			bootbox.alert({size:'small', message: 'Falta hacer que se graben los datos cambiados'});
			self.unchangedTurns = angular.copy(self.turns);
		} else {
			bootbox.alert({size:'small', message: 'No hubo cambios. No hace falta grabar nada'});
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