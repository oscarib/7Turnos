var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("menu", ['$rootScope','$location',function($rootScope,$location) {
	var self = this;

    self.hideMenu = [];
    self.hideMenu[1] = true;
    self.hideMenu[2] = true;
    self.hideMenu[3] = true;
    self.hideMenu[4] = true;
    self.pcName = "EspañaDeMaria.ES";
	self.showPrayersList = undefined;
	self.showHead = "activeMenuItem";
	self.showDashBoard = "activeMenuItem";
	self.showNewPrayer = false;
	
	$rootScope.navigateTo = function(path){
		$location.path(path);
	};
	

    self.openMenu = function(menuItem){
    	if (self.hideMenu[menuItem]){
    		self.hideMenu[menuItem] = false;
    		for (var i=0; i<self.hideMenu.length; i++){
    			if (i!==menuItem){
    				self.hideMenu[i] = true;
    			}
    		}
    	} else {
    		self.hideMenu[menuItem] = true;
    	}
    };
	
}]);