var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("dashboard", ['$rootScope', 'chartService', 'prayerServices', function($rootScope, chartService, prayerServices) {
	var self = this;
	
	var errorWithServiceCall = false;
	$rootScope.batidoraGeneral=false;
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
	
//FUNCIONES PRIVADAS -->
    
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
    
    //Solicitar las estadísticas a main.js
    $rootScope.$emit('needStatistics');
    
    //Cuando main.js carga las estadísticas, lanza un evento que se escucha aquí...
    $rootScope.$on('statisticsLoaded', function(){
    	loadCharts();
    });

//<-- Fin de FUNCIONES PRIVADAS
}]);