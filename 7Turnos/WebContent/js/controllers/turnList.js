var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("turnList", ['$rootScope', 'chartService', 'prayerServices','$location', 'NgTableParams', function($rootScope, chartService, prayerServices,$location,NgTableParams) {
	var self = this;
	$rootScope.batidoraGeneral=true;
	$rootScope.batidoraGeneralText=$rootScope.labels.gettingPrayerList;
	self.loadPrayerList = function(){
        //Cargamos la lista de oradores
        var turnsList = prayerServices.getTurnsList();
        turnsList.then(function(dataOut){
        	var originalArray = dataOut.data;
        	var reformattedArray = originalArray.map(function(obj){ 
        		   var rObj = {};
        		   if (obj.dow==='monday') rObj.day=$rootScope.labels.monday;
        		   if (obj.dow==='tuesday') rObj.day=$rootScope.labels.tuesday;
        		   if (obj.dow==='wednesday') rObj.day=$rootScope.labels.wednesday;
        		   if (obj.dow==='thursday') rObj.day=$rootScope.labels.thursday;
        		   if (obj.dow==='friday') rObj.day=$rootScope.labels.friday;
        		   if (obj.dow==='saturday') rObj.day=$rootScope.labels.saturday;
        		   if (obj.dow==='sunday') rObj.day=$rootScope.labels.sunday;
        		   rObj.turn = obj.turn;
        		   rObj.name = obj.prayer.name;
        		   rObj.uid = obj.prayer.uid;
        		   rObj.email = obj.prayer.email
        		   if (obj.status==='NotCommitted') rObj.status=$rootScope.labels.notCommitted;
        		   if (obj.status==='accepted') rObj.status=$rootScope.labels.accepted;
        		   if (obj.status==='cancelled') rObj.status=$rootScope.labels.cancelled;
        		   rObj.notes = obj.notes;
        		   rObj.uid = obj.uid;
        		   return rObj;
        		});
        	self.tableParams = new NgTableParams({}, { dataset: reformattedArray});
        }, function(error) {
        	if (!errorWithServiceCall){
        		errorWithServiceCall = true;
        		bootbox.alert({size:'small', message: $rootScope.labels.errorGettingPrayerList});
        	}
    	}).finally(function(){
    		$rootScope.batidoraGeneral=false;
        });
    };
    
    self.loadPrayerList();
}]);