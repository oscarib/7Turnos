var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("upload", ['$rootScope', 'chartService', 'prayerServices', 'DTOptionsBuilder', function($rootScope, chartService, prayerServices, DTOptionsBuilder) {
	var self = this;
	
	self.uploadCalendar = function(){
    	self.showLoadingGeneral = true;
    	var uploadCalendar = prayerServices.uploadCalendar();
    	uploadCalendar.then(function() {
    		bootbox.alert({size:'small', message: 'Se ha podido actualizar el calendario correctamente'});
        	self.showLoadingGeneral = false;
    	}, function(error) {
    		bootbox.alert({size:'small', message: 'Ha habido un problema al tratar de actualizar el calendario.'+ 
    											  'Consulte la consola del navegador para más información'});
    		console.error(error);
        	self.showLoadingGeneral = false;
    	}).finally(function(){
    	});
    }
   
}]);