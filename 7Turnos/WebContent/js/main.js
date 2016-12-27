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
			.when("/turnos",{
				controller: "turnList",
				controllerAs: "vm",
				templateUrl: "pages/turnList.html"
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
		$rootScope.labels.publico = dataOut.data.label_publico;
		$rootScope.labels.pseudonym = dataOut.data.label_pseudonym;
		$rootScope.labels.warning = dataOut.data.label_warning;
		$rootScope.labels.there_are = dataOut.data.label_there_are;
		$rootScope.labels.without_assigned_turns = dataOut.data.label_without_assigned_turns;
		$rootScope.labels.label_prayer = dataOut.data.label_prayer;
		$rootScope.labels.prayers = dataOut.data.label_prayers;
		$rootScope.labels.createPrayer = dataOut.data.label_createPrayer;
		$rootScope.labels.createPrayerOneStep = dataOut.data.label_createPrayerOneStep;
		$rootScope.labels.showCalendar = dataOut.data.label_showCalendar;
		$rootScope.labels.showCalendarDescription = dataOut.data.label_showCalendarDescription;
		$rootScope.labels.prayerList = dataOut.data.label_prayerList;
		$rootScope.labels.prayerListDescription = dataOut.data.label_prayerListDescription;
		$rootScope.labels.goTo = dataOut.data.label_goTo;
		$rootScope.labels.show = dataOut.data.label_show;
		$rootScope.labels.turnList = dataOut.data.label_turnList;
		$rootScope.labels.turnListDescription = dataOut.data.label_turnListDescription;
		$rootScope.labels.sendCalendar = dataOut.data.label_sendCalendar;
		$rootScope.labels.sendCalendarDescription = dataOut.data.label_sendCalendarDescription;
		$rootScope.labels.send = dataOut.data.label_send;
		$rootScope.labels.stopedPrayers = dataOut.data.label_stopedPrayers;
		$rootScope.labels.stopedPrayersDescription = dataOut.data.label_stopedPrayersDescription;
		$rootScope.labels.emptyTurns = dataOut.data.label_emptyTurns;
		$rootScope.labels.emptyTurnsDescription = dataOut.data.label_emptyTurnsDescription;
		$rootScope.labels.prayersInCountry = dataOut.data.label_prayersInCountry;
		$rootScope.labels.anonymousPrayers = dataOut.data.label_anonymousPrayers;
		$rootScope.labels.committed = dataOut.data.label_committed;
		$rootScope.labels.turns = dataOut.data.label_turns;
		$rootScope.labels.turn = dataOut.data.label_turn;
		$rootScope.labels.covered = dataOut.data.label_covered;
		$rootScope.labels.empty = dataOut.data.label_empty;
		$rootScope.labels.coveredTurns = dataOut.data.label_coveredTurns;
		$rootScope.labels.committedPrayers = dataOut.data.label_committedPrayers;
		$rootScope.labels.nonCommittedPrayers = dataOut.data.label_nonCommittedPrayers;
		$rootScope.labels.listings = dataOut.data.label_listings;
		$rootScope.labels.calendar = dataOut.data.label_calendar;
		$rootScope.labels.dashboard = dataOut.data.label_dashboard;
		$rootScope.labels.logout = dataOut.data.label_logout;
		$rootScope.labels.fileRequired = dataOut.data.label_fileRequired;
		$rootScope.labels.emailError = dataOut.data.label_emailError;
		$rootScope.labels.phone = dataOut.data.label_phone;
		$rootScope.labels.country = dataOut.data.label_country;
		$rootScope.labels.notes = dataOut.data.label_notes;
		$rootScope.labels.visibility = dataOut.data.label_visibility;
		$rootScope.labels.prayerData = dataOut.data.label_prayerData;
		$rootScope.labels.yes = dataOut.label_yes;
		$rootScope.labels.no = dataOut.label_no;
		$rootScope.labels.turnsData = dataOut.data.label_turnsData;
		$rootScope.labels.day = dataOut.data.label_day;
		$rootScope.labels.status = dataOut.data.label_status;
		$rootScope.labels.actions = dataOut.data.label_actions;
		$rootScope.labels.monday = dataOut.data.label_monday;
		$rootScope.labels.tuesday = dataOut.data.label_tuesday;
		$rootScope.labels.wednesday = dataOut.data.label_wednesday;
		$rootScope.labels.thursday = dataOut.data.label_thursday;
		$rootScope.labels.friday = dataOut.data.label_friday;
		$rootScope.labels.saturday = dataOut.data.label_saturday;
		$rootScope.labels.sunday = dataOut.data.label_sunday;
		$rootScope.labels.accepted = dataOut.data.label_accepted;
		$rootScope.labels.notCommitted = dataOut.data.label_notCommitted;
		$rootScope.labels.notCommittedPlural = dataOut.data.label_notCommittedPlural;
		$rootScope.labels.cancelled = dataOut.data.label_cancelled;
		$rootScope.labels.errorAlreadyCreatingPrayer = dataOut.data.label_errorAlreadyCreatingPrayer;
		$rootScope.labels.errorSavingTurn = dataOut.label_errorSavingTurn;
		$rootScope.labels.errorWas = dataOut.data.label_errorWas;
		$rootScope.labels.errorSavingPrayer = dataOut.data.label_errorSavingPrayer;
		$rootScope.labels.turnRemoved = dataOut.data.label_turnRemoved;
		$rootScope.labels.removeTurnConfirmation = dataOut.data.label_removeTurnConfirmation;
		$rootScope.labels.prayerRemoved = dataOut.data.label_prayerRemoved;
		$rootScope.labels.removePrayerAndTurnConfirmation = dataOut.data.label_removePrayerAndTurnConfirmation;
		$rootScope.labels.errorGettingPrayer = dataOut.datalabel_errorGettingPrayer;
		$rootScope.labels.errorMissingPrayerID = dataOut.data.label_errorMissingPrayerID;
		$rootScope.labels.errorNotFound = dataOut.data.label_errorNotFound;
		$rootScope.labels.prayerCreated = dataOut.data.label_prayerCreated;
		$rootScope.labels.errorPrayerAlreadyExists = dataOut.data.label_errorPrayerAlreadyExists;
		$rootScope.labels.errorRepetitiveID = dataOut.data.label_errorRepetitiveID;
		$rootScope.labels.errorCreatingPrayer = dataOut.data.label_errorCreatingPrayer;
		$rootScope.labels.otherCountry = dataOut.data.label_otherCountry;
		$rootScope.labels.label_foreign = dataOut.data.label_foreign;
		$rootScope.labels.errorGettingPrayerList = dataOut.data.label_errorGettingPrayerList;
		$rootScope.labels.gettingPrayerList = dataOut.data.label_gettingPrayerList;
		$rootScope.labels.calendarUpdated = dataOut.data.label_calendarUpdated;
		$rootScope.labels.errorUpdatingCalendar = dataOut.data.label_errorUpdatingCalendar;
		$rootScope.labels.loginError = dataOut.data.label_loginError;
		$rootScope.labels.errorCheckingCredentials = dataOut.data.label_errorCheckingCredentials;
		$rootScope.labels.uploadingCalendar = dataOut.data.label_uploadingCalendar;
		$rootScope.labels.loadingStatistics = dataOut.data.label_loadingStatistics;
		$rootScope.labels.errorLoadingStatistics = dataOut.data.label_errorLoadingStatistics;
		$rootScope.labels.available = dataOut.data.label_available;
		$rootScope.labels.publicoPlural = dataOut.data.label_publicoPlural;
		$rootScope.labels.anonymousPlural = dataOut.data.label_anonymousPlural;
		$rootScope.labels.notEmpty = dataOut.data.label_notEmpty;
		$rootScope.labels.updateCalendarShort = dataOut.data.label_updateCalendarShort;
		$rootScope.labels.configuration = dataOut.data.label_configuration;
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