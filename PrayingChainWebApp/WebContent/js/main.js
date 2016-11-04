var PrayingChain = angular.module("PrayingChain", ['ngAnimate','ngTable','ngRoute']);

PrayingChain.config(function($routeProvider){
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
			.otherwise({
				redirectTo: '/'
			});
});