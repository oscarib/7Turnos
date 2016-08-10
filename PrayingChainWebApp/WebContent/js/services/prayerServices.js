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

    	return self;
    }
})();
