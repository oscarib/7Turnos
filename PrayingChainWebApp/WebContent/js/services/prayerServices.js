(function() {
    'use strict';

    angular.module('PrayingChain')
        .factory('prayerServices', prayerServices);
    
    /** @ngInject **/
    function prayerServices ($q, $http) {
        var self = this;
        
        return self;
    }
})();
