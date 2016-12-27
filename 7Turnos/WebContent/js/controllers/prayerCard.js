var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("prayerCard", ['prayerServices', '$location', 'prayerServices', '$routeParams','$route','$rootScope', function(prayerServices, $location, prayerServices, $routeParams,$route,$rootScope) {
	var self = this;
	
	function inicializarDatos(data){
		//Orador
		self.prayer = {};
		self.prayer.uid = data.Prayer.uid;
		self.prayer.name = data.Prayer.name;
		self.prayer.email = data.Prayer.email
		self.prayer.country = data.Prayer.ownCountry ? $rootScope.labels.yes : $rootScope.labels.no;
		self.prayer.visibility = data.Prayer.hidden ? $rootScope.labels.yes : $rootScope.labels.no;
		self.prayer.phone = data.Prayer.phone;
		self.prayer.optinDate = data.Prayer.optinDate;
		self.prayer.pseudonym = data.Prayer.pseudonym;
		self.prayer.chain = data.Prayer.chain;
		self.prayer.notes = data.Prayer.notes == "" ? "" : data.Prayer.notes;
		self.unchangedPrayer = angular.copy(self.prayer);
		
		//Turno
		self.turns = data.turns;
		self.turns = self.turns.map(function(obj){
			var rObj = {};
			rObj.erased = obj.erased;
			rObj.notes = obj.notes;
			rObj.pax = obj.pax;
			rObj.prayer = obj.prayer;
			rObj.status = obj.status;
			rObj.turn = obj.turn;
			rObj.uid = obj.uid;
			switch(obj.status){
				case 'accepted':
					rObj.status = $rootScope.labels.accepted;
					break;
				case 'NotCommitted':
					rObj.status = $rootScope.labels.notCommitted;
					break;
				case 'cancelled':
					rObj.status = $rootScope.labels.cancelled;
					break;
				default:
					rObj.status = $rootScope.labels.errorNotFound;
					break;
			}
			switch(obj.dow) {
				case 'monday':
					rObj.dow = $rootScope.labels.monday;
					break;
				case 'tuesday':
					rObj.dow = $rootScope.labels.tuesday;
					break;
				case 'wednesday':
					rObj.dow = $rootScope.labels.wednesday;
					break;
				case 'thursday':
					rObj.dow = $rootScope.labels.thursday;
					break;
				case 'friday':
					rObj.dow = $rootScope.labels.friday;
					break;
				case 'saturday':
					rObj.dow = $rootScope.labels.saturday;
					break;
				case 'sunday':
					rObj.dow = $rootScope.labels.sunday;
					break;
				default:
					rObj.dow = $rootScope.labels.errorNotFound;
					break;
			}
			return rObj;
		});
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
			bootbox.alert({size:'small', message: $rootScope.labels.errorMissingPrayerID});
		} else {
			var promise = prayerServices.getPrayerAndTurns(prayerID);
			promise.then(function(dataOut) {
				inicializarDatos(dataOut.data);
			}, function(error) {
				bootbox.alert({size:'small', message: $rootScope.labels.errorGettingPrayer + " " + prayerID});
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
		monday = {value:$rootScope.labels.monday,key:'monday'};
		tuesday = {value:$rootScope.labels.tuesday,key:'tuesday'};
		wednesday = {value:$rootScope.labels.wednesday,key:'wednesday'};
		thursday = {value:$rootScope.labels.thursday,key:'thursday'};
		friday = {value:$rootScope.labels.friday,key:'friday'};
		saturday = {value:$rootScope.labels.saturday,key:'saturday'};
		sunday = {value:$rootScope.labels.sunday,key:'sunday'};
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
		var stat = {key:'accepted', value:$rootScope.labels.accepted};
		statues.push(stat);
		var stat = {key:'cancelled', value:$rootScope.labels.cancelled};
		statues.push(stat);
		var stat = {key:'NotCommitted', value:$rootScope.labels.notCommitted};
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
	
	self.deletePrayer = function(){
		bootbox.confirm($rootScope.labels.removePrayerAndTurnConfirmation, 
				function(result){ 
					if (result){
						self.prayer.erased=true;
						self.saveChanges();
						bootbox.alert({size:'small', message: $rootScope.labels.prayerRemoved});
						$rootScope.navigateTo("/");
					}
				});
	};
	
	self.deleteTurn = function(index){
		bootbox.confirm($rootScope.labels.removeTurnConfirmation, 
				function(result){ 
					if (result){
						self.turns[index].erased=true;
						self.saveTurnChanges();
						bootbox.alert({size:'small', message: $rootScope.labels.turnRemoved});
						$route.reload();
					}
				});
	};
	
	self.saveChanges = function(){
		if (prayerHasChanged()){
			if (angular.toJson(self.prayer) !== angular.toJson(self.unchangedPrayer)){
				var promise = prayerServices.updatePrayer(prayerBean(self.prayer));
				promise.then(function(dataOut) {}, function(error) {
					bootbox.alert({size:'small', message: $rootScope.labels.errorSavingPrayer});
					console.error($rootScope.labels.errorSavingPrayer + ": " + angular.toJson(self.prayer));
					console.error($rootScope.labels.errorWas + ": " + error.statusText);
				}).finally(function(){
					//bootbox.alert({size:'small', message: 'Se guardaron los datos correctamente'});
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

					self.turns[index].pax=1;
					self.turns[index].prayer = self.prayer;
					
					if (self.turns[index].uid=='Auto'){
						self.turns[index].uid='';
						var promise = prayerServices.addTurn(self.turns[index]);
						promise.then(function(dataOut) {}, function(error) {
							bootbox.alert({size:'small', message: $rootScope.labels.errorSavingTurn});
							console.error($rootScope.labels.errorSavingTurn + ": " + angular.toJson(self.turns[index]));
							console.error($rootScope.labels.errorWas + ": " + error.status + ": " + error.statusText);
						}).finally(function(){
							$route.reload();
						});
					} else {
						var promise = prayerServices.updateTurn(self.turns[index]);
						promise.then(function(dataOut) {}, function(error) {
							bootbox.alert({size:'small', message: $rootScope.labels.errorSavingTurn});
							console.error($rootScope.labels.errorSavingTurn + ": " + angular.toJson(self.turns[index]));
							console.error($rootScope.labels.errorWas + ": " + error.status + ": " + error.statusText);
						}).finally(function(){
							$route.reload();
						});
						
					}
				}
			}
			self.unchangedTurns = angular.copy(self.turns);
		}
		self.editingTurn[uid] = false;
	};
	
	self.addTurn = function(){
		if (self.alreadyANew){
			bootbox.alert({size:'small', message: $rootScope.labels.errorAlreadyCreatingPrayer});	
		} else {
			self.alreadyANew = true;
			self.NewTurn = {uid:'Auto',dow:'',turn:'',status:''};
			self.turns.push(self.NewTurn);
			self.editingTurn['Auto'] = true;
		}
	};
	
	prayerBean = function(dataIn){
		var prayerEntity = {};
		prayerEntity.uid = dataIn.uid; 
		prayerEntity.name = dataIn.name;
		prayerEntity.email = dataIn.email;
		prayerEntity.phone = dataIn.phone;
		prayerEntity.ownCountry = dataIn.country==$rootScope.labels.no ? false : true;
		prayerEntity.optinDate = dataIn.optinDate;
		prayerEntity.notes = dataIn.notes;
		prayerEntity.hidden = dataIn.visibility == $rootScope.labels.no ? false : true;
		prayerEntity.pseudonym = dataIn.pseudonym;
		prayerEntity.chain = dataIn.chain;
		prayerEntity.erased = dataIn.erased;
		return prayerEntity;
	};

	getPrayerAndTurns();
}]);