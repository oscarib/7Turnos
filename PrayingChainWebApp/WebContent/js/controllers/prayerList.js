var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("prayerList", ['$rootScope', 'chartService', 'prayerServices','$location', 'NgTableParams', function($rootScope, chartService, prayerServices,$location,NgTableParams) {
	var self = this;
	self.loadPrayerList = function(){
        //Cargamos la lista de oradores
        var prayerList = prayerServices.getPrayerList();
        prayerList.then(function(dataOut){
        	self.tableParams = new NgTableParams({}, { dataset: dataOut.data});
            self.tableParams.filter({ $: "Oscar" })
        }, function(error) {
        	if (!errorWithServiceCall){
        		errorWithServiceCall = true;
        		bootbox.alert({size:'small', message: 'Se produjo un error al solicitar la lista de oradores'});
        	}
    	}).finally(function(){
        	self.showLoadingPrayersTable = false;
        });
    };
    
    self.loadPrayerList();
}]);