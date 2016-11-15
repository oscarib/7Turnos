var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("prayerList", ['$rootScope', 'chartService', 'prayerServices','$location', 'NgTableParams', function($rootScope, chartService, prayerServices,$location,NgTableParams) {
	var self = this;
	self.loadPrayerList = function(){
        //Cargamos la lista de oradores
        var prayerList = prayerServices.getPrayerList();
        prayerList.then(function(dataOut){
        	var originalArray = dataOut.data;
        	var reformattedArray = originalArray.map(function(obj){ 
        		   var rObj = {};
        		   rObj.name = obj.name;
        		   rObj.email = obj.email;
        		   rObj.ownCountry = obj.ownCountry ? '' : 'Extranjero';
        		   rObj.hidden = obj.hidden ? 'X' : '-';
        		   rObj.phone = obj.phone;
        		   rObj.pseudonym = obj.pseudonym;
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
        	self.showLoadingPrayersTable = false;
        });
    };
    
    self.loadPrayerList();
}]);