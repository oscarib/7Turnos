(function() {
    'use strict';

    angular.module('PrayingChain')
        .factory('pcUtils', pcUtils);
    
    /** @ngInject **/
    function pcUtils ($q, $http) {
    	var self = this;

    	self.getBackendData = function(url, requestData){
    		var defered = $q.defer();
    		var promise = defered.promise;
    		
    		$http({
    			url: url,
    			dataType: 'json',
    			method: 'POST',
    			data: JSON.stringify(requestData),
    			headers: {
    				"Content-Type": "application/json"
    			}
    		}).then(function(dataOut) {
    		     defered.resolve(dataOut);
    		}, function(error) {
    		     defered.reject(error);
    		});
    		
    		return promise;
    	}

    	return self;
    }

})();