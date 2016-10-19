var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("prayerList", ['$rootScope', 'chartService', 'prayerServices', 'DTOptionsBuilder', function($rootScope, chartService, prayerServices, DTOptionsBuilder) {
	var self = this;

	self.loadPrayerList = function(){
        //Cargamos la lista de oradores
        var prayerList = prayerServices.getPrayerList();
        prayerList.then(function(dataOut){
        	self.prayers = dataOut.data;
        	setDatatableOptions();
        }, function(error) {
        	if (!errorWithServiceCall){
        		errorWithServiceCall = true;
        		bootbox.alert({size:'small', message: 'Se produjo un error al solicitar la lista de oradores'});
        	}
    	}).finally(function(){
        	self.showLoadingPrayersTable = false;
        });
    };

}]);