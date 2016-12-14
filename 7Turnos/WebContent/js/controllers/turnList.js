var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("turnList", ['$rootScope', 'chartService', 'prayerServices','$location', 'NgTableParams', function($rootScope, chartService, prayerServices,$location,NgTableParams) {
	var self = this;
	$rootScope.batidoraGeneral=true;
	$rootScope.batidoraGeneralText='Cargando lista de turnos. Por favor, espere...';
	self.loadPrayerList = function(){
        //Cargamos la lista de oradores
        var turnsList = prayerServices.getTurnsList();
        turnsList.then(function(dataOut){
        	var originalArray = dataOut.data;
        	var reformattedArray = originalArray.map(function(obj){ 
        		   var rObj = {};
        		   if (obj.dow==='monday') rObj.day='Lunes';
        		   if (obj.dow==='tuesday') rObj.day='Martes';
        		   if (obj.dow==='wednesday') rObj.day='Miércoles';
        		   if (obj.dow==='thursday') rObj.day='Jueves';
        		   if (obj.dow==='friday') rObj.day='Viernes';
        		   if (obj.dow==='saturday') rObj.day='Sábado';
        		   if (obj.dow==='sunday') rObj.day='Domingo';
        		   rObj.turn = obj.turn;
        		   if (obj.status==='NotCommitted') rObj.status='No Comprometido';
        		   if (obj.status==='accepted') rObj.status='Activo';
        		   if (obj.status==='cancelled') rObj.status='Cancelado';
        		   rObj.notes = obj.notes;
        		   rObj.uid = obj.uid;
        		   return rObj;
        		});
        	self.tableParams = new NgTableParams({}, { dataset: reformattedArray});
        }, function(error) {
        	if (!errorWithServiceCall){
        		errorWithServiceCall = true;
        		bootbox.alert({size:'small', message: 'Se produjo un error al solicitar la lista de oradores'});
        	}
    	}).finally(function(){
    		$rootScope.batidoraGeneral=false;
        });
    };
    
    self.loadPrayerList();
}]);