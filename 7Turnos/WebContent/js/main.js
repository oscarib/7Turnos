var PrayingChain = angular.module("PrayingChain", ['ngAnimate','ngTable','ngRoute']);

PrayingChain.config(function($routeProvider,$httpProvider){
		$routeProvider
			.when("/", {
				controller: "dashboard",
				controllerAs: "vm",
				templateUrl: "pages/dashboard.html"
			})
			.when("/crearOrador",{
				controller: "newPrayer",
				controllerAs: "vm",
				templateUrl: "pages/newPrayer.html"
			})
			.when("/oradores",{
				controller: "prayerList",
				controllerAs: "vm",
				templateUrl: "pages/prayerList.html"
			})
			.when("/upload",{
				controller: "upload",
				controllerAs: "vm",
				templateUrl: "pages/upload.html"
			})
			.when("/orador/:prayerID",{
				controller: "prayerCard",
				controllerAs: "vm",
				templateUrl: "pages/prayerCard.html"
			})
			.when("/oradores/:filterString",{
				controller: "prayerList",
				controllerAs: "vm",
				templateUrl: "pages/prayerList.html"
			})
			.when("/calendar",{
				controller: "calendar",
				controllerAs: "vm",
				templateUrl: "pages/calendar.html"
			})
			.otherwise({
				redirectTo: '/'
			});
		
		$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
		
		// CSRF headers names to fit spring csrf filter
		$httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
		$httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';
});

PrayingChain.controller("main", ['$scope','$rootScope','$window','pcUtils', function($scope,$rootScope,$window,pcUtils) {
	var self = this;
	
	//Para proteger las dimensiones mínimas de la aplicación
	$rootScope.isMinimumSize = false;
    $scope.minWidth = "800";
    $scope.minHeight = "600";
    $scope.sizeWidth = 0;
    $scope.sizeHeight = 0;
	var promise = pcUtils.getProperties();
	promise.then(function(dataOut){
		$rootScope.labels={};
		$rootScope.labels.name = dataOut.data.label_name;
		$rootScope.labels.email = dataOut.data.label_email;
		$rootScope.labels.ownCountry = dataOut.data.label_ownCountry;
		$rootScope.labels.hidden = dataOut.data.label_hidden;
		$rootScope.labels.pseudonym = dataOut.data.label_pseudonym;
		$rootScope.labels.warning = dataOut.data.label_warning;
		$rootScope.labels.there_are = dataOut.data.label_there_are;
		$rootScope.labels.without_assigned_turns = dataOut.data.label_without_assigned_turns;
	});

    var w = angular.element($window);

    $scope.getWindowDimensions = function () {
        return {
            'h': w.height(),
            'w': w.width()
        };
    };

    $scope.$watch($scope.getWindowDimensions, function (newValue, oldValue) {
    	$scope.sizeWidth = newValue.w;
    	$scope.sizeHeight =  newValue.h;
        if(newValue.w < $scope.minWidth || newValue.h < $scope.minHeight) {
        	$rootScope.isMinimumSize = true;
        } else {
        	$rootScope.isMinimumSize = false;
        }
    }, true);

    w.bind('resize', function () {
    	$scope.$apply();
    });

	
}]);