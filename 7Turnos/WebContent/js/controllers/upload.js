var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("upload", ['$rootScope', 'chartService', 'prayerServices', 'DTOptionsBuilder', function($rootScope, chartService, prayerServices, DTOptionsBuilder) {
	var self = this;
	
	self.uploadCalendar = function(){
    	self.showLoadingGeneral = true;
    	var uploadCalendar = prayerServices.uploadCalendar();
    	uploadCalendar.then(function() {
    		bootbox.alert({size:'small', message: $rootScope.labels.calendarUpdated});
        	self.showLoadingGeneral = false;
    	}, function(error) {
    		bootbox.alert({size:'small', message: $rootScope.labels.errorUpdatingCalendar});
    		console.error(error);
        	self.showLoadingGeneral = false;
    	}).finally(function(){
    	});
    }
   
}]);