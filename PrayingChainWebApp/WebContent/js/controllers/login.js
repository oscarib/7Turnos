var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("login", ['$location','$rootScope','prayerServices','$rootScope', function($location,$rootScope,prayerServices,$rootScope) {
	var self = this;

	$rootScope.maxUserRole = -1;
	
	$rootScope.getLoggedUser = function(){
		var loggedUser = prayerServices.getLoggedUser();
		loggedUser.then(function(dataOut) {
			var loggedUser = dataOut.data; 
			if (!loggedUser.username){
				$rootScope.authenticated = false;
			} else {
				$rootScope.authenticated = true;
				$rootScope.maxUserRole = getMaxUserRole(dataOut.data.authorities);
				$rootScope.userName = dataOut.data.username;
			}
		}, function(error) {
			//Aquí el código cuando la llamada falle (deferred.reject())

		}).finally(function(){});
	};
	
	$rootScope.getLoggedUser();
	
	self.login = function(){
		
		$rootScope.authenticated = false;

		var promise = prayerServices.login(self.credentials);
		promise.then(function(dataOut) {
			if (dataOut.data.username) {
				$rootScope.authenticated = true;
			}
		});
	};
	
	//Returns the value of the maximum user role
	function getMaxUserRole(roles){
		var roleList = ["ROLE_USER", "ROLE_ADMIN", "ROLE_SU"];
		var maxUserRole = 0;
		for (rol in roles){
			var roleIndex = roleList.indexOf(roles[rol].authority);
			maxUserRole = roleIndex>maxUserRole ? roleIndex : maxUserRole;
		}
		return maxUserRole;
	}

}]);