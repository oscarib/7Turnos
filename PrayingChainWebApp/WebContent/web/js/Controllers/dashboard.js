var app = angular.module("PrayingChain", ['ngAnimate']);

app.controller("dashboard", function($scope) {
	var self = this;
    self.firstName = "John";
    self.lastName = "Doe";
    self.hideActions = true;
    self.hideListings = true;
    
    self.openActions = function(){
    	if (self.hideActions){
	        self.hideActions = false;
	        self.hideListings = true;
    	} else {
	        self.hideActions = true;
    	}
    };

    self.openListings = function(){
    	if (self.hideListings){
	        self.hideActions = true;
	        self.hideListings = false;
    	} else {
    		self.hideListings = true;
    	}
    };
});