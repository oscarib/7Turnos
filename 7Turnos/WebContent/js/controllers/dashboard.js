var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("dashboard", ['$rootScope', 'chartService', 'prayerServices', function($rootScope, chartService, prayerServices) {
	var self = this;
	
	var errorWithServiceCall = false;
	$rootScope.batidoraGeneral=false;
	initStatistics();
	
	$rootScope.uploadCalendar = function(){
		$rootScope.batidoraGeneralText=$rootScope.labels.uploadingCalendar;
		$rootScope.batidoraGeneral=true;
		var promise = prayerServices.uploadCalendar();
		promise.then(function(dataOut){
			bootbox.alert({size:'small', message: $rootScope.labels.calendarUpdated});
    	}, function(error) {
			bootbox.alert({size:'small', message: $rootScope.labels.errorUpdatingCalendar});
    	}).finally(function(){
			$rootScope.batidoraGeneral=false;
    	});
	};
	
    self.loadStatistics = function(){
    	$rootScope.batidoraGeneralText=$rootScope.labels.loadingStatistics;
    	$rootScope.batidoraGeneral=true;
    	var statistics = prayerServices.getChainStatistics();
    	statistics.then(function(dataOut){
    		$rootScope.TotalPrayers = dataOut.data.totalPrayers;
    		$rootScope.UsedTurns = dataOut.data.usedTurns;
    		$rootScope.EmptyTurns = dataOut.data.emptyTurns;
    		$rootScope.TotalTurns = dataOut.data.totalTurns;
    		$rootScope.NonEmptyTurns = $rootScope.TotalTurns - $rootScope.EmptyTurns;
    		$rootScope.AvailableTurns = $rootScope.TotalTurns - $rootScope.UsedTurns;
    		$rootScope.CommittedPrayers = dataOut.data.committedPrayers;
    		$rootScope.NonCommittedPrayers = dataOut.data.nonCommittedPrayers;
    		$rootScope.HiddenPrayers = dataOut.data.hiddenPrayers;
    		$rootScope.PublicPrayers = $rootScope.TotalPrayers - dataOut.data.hiddenPrayers;
    		$rootScope.ForeignPrayers = dataOut.data.foreignPrayers;
    		$rootScope.LocalPrayers = dataOut.data.localPrayers;
    		$rootScope.OrphanPrayers = dataOut.data.orphanPrayers;
    		$rootScope.OrphanPrayersText = $rootScope.OrphanPrayers + " " + ($rootScope.OrphanPrayers > 1 ? $rootScope.labels.prayers : $rootScope.labels.prayer);
    		loadCharts();
    	}, function(error) {
    		if (!errorWithServiceCall){
    			errorWithServiceCall = true;
    			bootbox.alert({size:'small', message: $rootScope.labels.errorLoadingStatistics});
    		}
    	}).finally(function(){
    		$rootScope.batidoraGeneral=false;
    	});
    };    
    
    self.loadStatistics();
    
//FUNCIONES PRIVADAS -->
    function initStatistics(){
		$rootScope.TotalPrayers = 0;
		$rootScope.EmptyTurns = 0;
		$rootScope.TotalTurns = 0;
		$rootScope.TurnsCovered = 0;
		$rootScope.AvailableTurns = 0;
		$rootScope.CommittedPrayers = 0;
		$rootScope.NonCommittedPrayers = 0;
		$rootScope.Redundancy = 0;
		$rootScope.UsedTurnsPerc = 0;
		$rootScope.HiddenPrayers = 0;
		$rootScope.PublicPrayers = 0;
		$rootScope.ForeignPrayers = 0;
		$rootScope.LocalPrayers = 0;
		$rootScope.OrphanTurns = 0;
		$rootScope.OrphanPrayers = 0;
    	
    };
    
    function loadCharts(){
        //Committed / Non committed pie chart
        var labels= [$rootScope.labels.committed, $rootScope.labels.notCommittedPlural];
        var pieChartData = [$rootScope.CommittedPrayers,$rootScope.NonCommittedPrayers];
        var borderColor = ['rgba(54, 162, 235, 0.2)','rgba(255, 99, 132, 0.2)'];
        var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
        chartService.setPieChart("chartCommitted", labels, pieChartData, backgroundColor, borderColor);
        
        //Coverage pie chart
        var labels= [$rootScope.labels.notEmpty, $rootScope.labels.empty];
        var pieChartData = [$rootScope.UsedTurns, $rootScope.EmptyTurns];
        var borderColor = ['rgba(54, 162, 235, 0.2)','rgba(255, 99, 132, 0.2)'];
        var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
        chartService.setPieChart("chartCoverage", labels, pieChartData, backgroundColor, borderColor);

        //availables Vs Total pie chart
        var labels= [$rootScope.labels.covered, $rootScope.labels.available];
        var pieChartData = [$rootScope.UsedTurns,$rootScope.AvailableTurns];
        var borderColor = ['rgba(54, 162, 235, 0.2)', 'rgba(255, 99, 132, 0.2)'];
        var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
        chartService.setPieChart("availablePieChart", labels, pieChartData, backgroundColor, borderColor);
        
        //Hidden Vs Public Prayers
        var labels= [$rootScope.labels.publicoPlural, $rootScope.labels.anonymousPlural];
        var pieChartData = [$rootScope.PublicPrayers, $rootScope.HiddenPrayers];
        var borderColor = ['rgba(54, 162, 235, 0.2)','rgba(255, 99, 132, 0.2)'];
        var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
        chartService.setPieChart("hiddenPublicRatio", labels, pieChartData, backgroundColor, borderColor);

        //Hidden Vs Public Prayers
        var labels= [$rootScope.labels.ownCountry, $rootScope.labels.otherCountry];
        var pieChartData = [$rootScope.LocalPrayers, $rootScope.ForeignPrayers];
        var borderColor = ['rgba(54, 162, 235, 0.2)', 'rgba(255, 99, 132, 0.2)'];
        var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
        chartService.setPieChart("localForeignRatio", labels, pieChartData, backgroundColor, borderColor);
    };
    
//<-- Fin de FUNCIONES PRIVADAS
}]);