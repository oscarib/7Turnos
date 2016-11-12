var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("login", ['$location','$rootScope','$http', function($location,$rootScope,$http) {
	var self = this;
	
	var authenticate = function(credentials, callback) {

	    var headers = credentials ? {authorization : "edmUser:" + credentials.edmUser + ":edmPwd:" + credentials.edmPwd} : {};

	    $http.post('./performLogin', {headers : headers}).then(function(response) {
	      if (response.data.name) {
	        $rootScope.authenticated = true;
	      } else {
	        $rootScope.authenticated = false;
	      }
	      callback && callback();
	    }, function() {
	      $rootScope.authenticated = false;
	      callback && callback();
	    });

	  }

	  authenticate();
	  self.credentials = {};
	  self.login = function() {
	      authenticate(self.credentials, function() {
	        if ($rootScope.authenticated) {
	          $location.path("/");
	          self.error = false;
	        } else {
	          $location.path("/login");
	          self.error = true;
	        }
	      });
	  };
}]);