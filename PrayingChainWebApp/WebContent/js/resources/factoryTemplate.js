(function() {
    'use strict';

    angular.module('PrayingChain')
        .factory('factoryName', factoryName);
    
    /** @ngInject **/
    function factoryName () {
    	var self = this;
    	return self;
    }
})();