var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("newPrayer", ['$scope','$rootScope','prayerServices','$location', function($scope,$rootScope,prayerServices,$location) {
	var self = this;
	
	inicializarDatos();
	
	//FUNCIONES SCOPE
	self.createNewPrayer = function (isValid){
		
		self.phoneOrEmailError = (!self.telefono && !self.email) ? true: false;
		isValid = isValid && ((self.visibilidad == "Público" && !self.seudonimo) ? false: true);
		isValid = isValid && !self.phoneOrEmailError;

		if (isValid){

			var newPrayer = {};
			newPrayer.nombre = self.nombre;
			newPrayer.dia = self.dia;
			newPrayer.turno = self.turno;
			newPrayer.email = self.email;
			newPrayer.telefono = self.telefono;
			newPrayer.pais = self.pais;
			newPrayer.notas = self.notas;
			newPrayer.visibilidad = self.visibilidad;
			newPrayer.seudonimo = self.seudonimo;

			var promise = prayerServices.createNewPrayer(newPrayer);
			promise.then(function(dataOut) {
				var error = dataOut.data;
				if (error==0) {
					bootbox.alert({size:'small', message: 'Se ha creado el orador en base de datos'});
					$rootScope.resetNewPrayerForm();
					$location.path('/');
				} else {
					var problema = (error==1 ? "email": "teléfono");
					bootbox.alert({size:'small', message: 'Error: No puedes crear 2 oradores con el mismo ' + problema});
				}
			}, function(error) {
				bootbox.alert({size:'small', message: 'Hubo un error al tratar de crear el orador'});
			}).finally(function(){});
		}
	};
	
	//FUNCIONES PRIVADAS
	function inicializarDatos(){
		self.nombre = undefined;
		self.dia = "";
		self.turno = "";
		self.email = "";
		self.telefono = "";
		self.pais = "";
		self.notas = "";
		self.visibilidad = "";
		self.seudonimo = "";
		self.visibilidades = ["Oculto","Público"];
		self.daysOfWeek = ["Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"];
		self.paises = ["España", "Otro País"];
		self.turnos = ["00:00am","00:30am","01:00am","01:30am","02:00am","02:30am","03:00am","03:30am","04:00am","04:30am",
		               "05:00am","05:30am","06:00am","06:30am","07:00am","07:30am","08:00am","08:30am","10:00am","10:30am",
		               "11:00am","11:30am","12:00pm","12:30pm","13:00pm","13:30pm","14:00pm","14:30pm","15:00pm","15:30pm",
		               "16:00pm","16:30pm","17:00pm","17:30pm","18:00pm","18:30pm","19:00pm","19:30pm","20:00pm","20:30pm",
		               "21:00pm","21:30pm","22:00pm","22:30pm","23:00pm","23:30pm"];
	};
	
	$rootScope.resetNewPrayerForm = function(){
		inicializarDatos();
		$scope.newPrayerForm.nombre.$touched = false;
		$scope.newPrayerForm.pais.$touched = false;
		$scope.newPrayerForm.email.$touched = false;
		$scope.newPrayerForm.telefono.$touched = false;
		$scope.newPrayerForm.dia.$touched = false;
		$scope.newPrayerForm.turno.$touched = false;
		$scope.newPrayerForm.visibilidad.$touched = false;
		$scope.newPrayerForm.$submitted = false;
		$scope.newPrayerForm.$setPristine();
	};
	
}]);