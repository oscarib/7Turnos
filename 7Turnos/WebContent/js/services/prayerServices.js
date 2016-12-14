(function() {
    'use strict';

    angular.module('PrayingChain')
        .factory('prayerServices', prayerServices);
    
    /** @ngInject **/
    function prayerServices ($q, pcUtils, $http) {
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
    	
    	self.getTurnsList = function(){
    		var defered = $q.defer();
    		var promise = defered.promise;

    		var utilsPromise = pcUtils.getBackendData("./getAllTurns.do");
    		
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
    	
    	self.getCalendarHtml = function(){
    		var defered = $q.defer();
    		var promise = defered.promise;

    		var utilsPromise = pcUtils.getBackendData("./getCalendarHtml.do");
    		
    		utilsPromise.then(function(dataOut) {
    		     defered.resolve(dataOut);
    		}, function(error) {
    		     defered.reject(error);
    		});
    		
    		return promise;
    	}
    	
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
    	
    	self.updateTurn = function(dataIn){
    		var defered = $q.defer();
    		var promise = defered.promise;

    		var utilsPromise = pcUtils.getBackendData("./updateTurn.do", dataIn);
    		
    		utilsPromise.then(function(dataOut) {
    		     defered.resolve(dataOut);
    		}, function(error) {
    		     defered.reject(error);
    		});
    		
    		return promise;
    	};

    	self.updatePrayer = function(dataIn){
    		var defered = $q.defer();
    		var promise = defered.promise;

    		var utilsPromise = pcUtils.getBackendData("./updatePrayer.do", dataIn);
    		
    		utilsPromise.then(function(dataOut) {
    		     defered.resolve(dataOut);
    		}, function(error) {
    		     defered.reject(error);
    		});
    		
    		return promise;
    	};

    	self.addTurn = function(dataIn){
    		var defered = $q.defer();
    		var promise = defered.promise;

    		var utilsPromise = pcUtils.getBackendData("./addTurn.do", dataIn);
    		
    		utilsPromise.then(function(dataOut) {
    		     defered.resolve(dataOut);
    		}, function(error) {
    		     defered.reject(error);
    		});
    		
    		return promise;
    	};
    	
    	self.getLoggedUser = function(){

    		var defered = $q.defer();
    		var promise = defered.promise;
    		
    		$http({
    			url: './getLoggedUser.do',
    			dataType: 'json',
    			method: 'POST'
    		}).then(function(dataOut) {
    		     defered.resolve(dataOut);
    		}, function(error) {
    		     defered.reject(error);
    		});
    		
    		return promise;
    	};

    	self.login = function(credentials){

    		var defered = $q.defer();
    		var promise = defered.promise;
    		
    		$http({
    			url: './login.do',
    			dataType: 'json',
    			data: credentials,
    			headers: {
    				"Content-Type": "application/json"
    			},
    			method: 'POST'
    		}).then(function(dataOut) {
    		     defered.resolve(dataOut);
    		}, function(error) {
    		     defered.reject(error);
    		});
    		
    		return promise;
    	};
    	
    	self.logout = function() {

    		var defered = $q.defer();
    		var promise = defered.promise;

    		$http({
    			url: './logout.do',
    			method: 'POST'
    		}).then(function(dataOut){
    			defered.resolve(dataOut);
    		});
    		
    		return promise;
    	};
    	
    	return self;
    }
})();
