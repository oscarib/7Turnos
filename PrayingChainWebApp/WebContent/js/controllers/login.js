var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("login", ['$location','$rootScope','prayerServices','$rootScope', function($location,$rootScope,prayerServices,$rootScope) {
	var self = this;

	$rootScope.authenticated = false;

	$rootScope.authenticate = function(){
		var loggedUser = prayerServices.getLoggedUser();
		loggedUser.then(function(dataOut) {
			var loggedUser = dataOut.data; 
			if (!loggedUser.username){
				$rootScope.authenticated = false;
			} else {
				$rootScope.authenticated = true;
			}
		}, function(error) {
			//Aquí el código cuando la llamada falle (deferred.reject())

		}).finally(function(){});
	};
	
	$rootScope.authenticate();
	
	self.login = function(){
		
		$rootScope.authenticated = false;

		var promise = prayerServices.login(self.credentials);
		promise.then(function(dataOut) {
			if (!dataOut.data.username) {
				$rootScope.authenticated = true;
			}
		});
	};

}]);