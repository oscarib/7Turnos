(function() {
    'use strict';

    angular.module('PrayingChain')
        .factory('prayerServices', prayerServices);
    
    /** @ngInject **/
    function prayerServices ($q, pcUtils) {
    	var self = this;

    	self.getPrayerList = function(requestData){
    		var defered = $q.defer();
    		var promise = defered.promise;

    		var utilsPromise = pcUtils.getBackendData("./getAllPrayers.do");
    		
    		utilsPromise.then(function(dataOut) {
    		     defered.resolve(dataOut);
    		}, function(error) {
    		     defered.reject(error);
    		});
    		
    		return promise;
    	};
    	
    	self.getPrayerAndTurns = function(prayerID){
    		var defered = $q.defer();
    		var promise = defered.promise;

    		var utilsPromise = pcUtils.getBackendData("./getPrayerAndTurns.do", prayerID);
    		
    		utilsPromise.then(function(dataOut) {
    		     defered.resolve(dataOut);
    		}, function(error) {
    		     defered.reject(error);
    		});
    		
    		return promise;
    	};

    	self.getChainStatistics = function(requestData){
    		var defered = $q.defer();
    		var promise = defered.promise;

    		var utilsPromise = pcUtils.getBackendData("./getStatistics.do");
    		
    		utilsPromise.then(function(dataOut) {
    		     defered.resolve(dataOut);
    		}, function(error) {
    		     defered.reject(error);
    		});
    		
    		return promise;
    	};
    	
    	self.uploadCalendar = function(){
    		var defered = $q.defer();
    		var promise = defered.promise;

    		var utilsPromise = pcUtils.getBackendData("./uploadCalendar.do");
    		
    		utilsPromise.then(function(dataOut) {
    		     defered.resolve(dataOut);
    		}, function(error) {
    		     defered.reject(error);
    		});
    		
    		return promise;
    	};
    	
    	self.createNewPrayer = function(dataIn){
    		var defered = $q.defer();
    		var promise = defered.promise;

    		var utilsPromise = pcUtils.getBackendData("./createNewPrayer.do", dataIn);
    		
    		utilsPromise.then(function(dataOut) {
    		     defered.resolve(dataOut);
    		}, function(error) {
    		     defered.reject(error);
    		});
    		
    		return promise;
    	};

    	return self;
    }
})();
