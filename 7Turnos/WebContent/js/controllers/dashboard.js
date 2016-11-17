var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("dashboard", ['$rootScope', 'chartService', 'prayerServices', function($rootScope, chartService, prayerServices) {
	var self = this;
	
	var errorWithServiceCall = false;
	$rootScope.batidoraGeneral=false;
	initStatistics();
	
	$rootScope.uploadCalendar = function(){
		$rootScope.batidoraGeneralText='Realizando el envío del calendario. Por favor, espere...';
		$rootScope.batidoraGeneral=true;
		var promise = prayerServices.uploadCalendar();
		promise.then(function(dataOut){
			bootbox.alert({size:'small', message: 'Se ha actualizado el calendario con éxito'});
    	}, function(error) {
			bootbox.alert({size:'small', message: 'Hubo un error al tratar de actualizar el calendario'});
    	}).finally(function(){
			$rootScope.batidoraGeneral=false;
    	});
	};
	
    self.loadStatistics = function(){
    	$rootScope.batidoraGeneralText='Cargando estadísticas y datos. Por favor, espere...';
    	$rootScope.batidoraGeneral=true;
    	var statistics = prayerServices.getChainStatistics();
    	statistics.then(function(dataOut){
    		$rootScope.TotalPrayers = dataOut.data.TotalPrayers;
    		$rootScope.UsedTurns = dataOut.data.UsedTurns;
    		$rootScope.EmptyTurns = dataOut.data.EmptyTurns;
    		$rootScope.TotalTurns = dataOut.data.TotalTurns;
    		$rootScope.TurnsCovered = dataOut.data.TurnsCovered;
    		$rootScope.AvailableTurns = dataOut.data.AvailableTurns;
    		$rootScope.DaysCovered = dataOut.data.DaysCovered;
    		$rootScope.availableDays = dataOut.data.availableDays;
    		$rootScope.CommittedPrayers = dataOut.data.CommittedPrayers;
    		$rootScope.NonCommittedPrayers = dataOut.data.NonCommittedPrayers;
    		$rootScope.HiddenPrayers = dataOut.data.HiddenPrayers;
    		$rootScope.PublicPrayers = dataOut.data.PublicPrayers;
    		$rootScope.ForeignPrayers = dataOut.data.ForeignPrayers;
    		$rootScope.LocalPrayers = dataOut.data.LocalPrayers;
    		$rootScope.OrphanPrayers = dataOut.data.OrphanPrayers;
    		$rootScope.OrphanPrayersText = $rootScope.OrphanPrayers > 1 ? $rootScope.OrphanPrayers + " oradores" : $rootScope.OrphanPrayers + " orador";
    		loadCharts();
    	}, function(error) {
    		if (!errorWithServiceCall){
    			errorWithServiceCall = true;
    			bootbox.alert({size:'small', message: 'Se produjo un error al solicitar las estadísticas'});
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
        var labels= ["Comprometidos", "No Comprometidos"];
        var pieChartData = [$rootScope.CommittedPrayers,$rootScope.NonCommittedPrayers];
        var borderColor = ['rgba(54, 162, 235, 0.2)','rgba(255, 99, 132, 0.2)'];
        var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
        chartService.setPieChart("chartCommitted", labels, pieChartData, backgroundColor, borderColor);
        
        //Coverage pie chart
        var labels= ["No Vacíos", "Vacíos"];
        var pieChartData = [$rootScope.DaysCovered, $rootScope.availableDays];
        var borderColor = ['rgba(54, 162, 235, 0.2)','rgba(255, 99, 132, 0.2)'];
        var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
        chartService.setPieChart("chartCoverage", labels, pieChartData, backgroundColor, borderColor);

        //availables Vs Total pie chart
        var labels= ["Cubiertos", "Disponibles"];
        var pieChartData = [$rootScope.UsedTurns,$rootScope.AvailableTurns];
        var borderColor = ['rgba(54, 162, 235, 0.2)', 'rgba(255, 99, 132, 0.2)'];
        var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
        chartService.setPieChart("availablePieChart", labels, pieChartData, backgroundColor, borderColor);
        
        //Hidden Vs Public Prayers
        var labels= ["Públicos", "Anónimos"];
        var pieChartData = [$rootScope.PublicPrayers, $rootScope.HiddenPrayers];
        var borderColor = ['rgba(54, 162, 235, 0.2)','rgba(255, 99, 132, 0.2)'];
        var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
        chartService.setPieChart("hiddenPublicRatio", labels, pieChartData, backgroundColor, borderColor);

        //Hidden Vs Public Prayers
        var labels= ["España", "Otro País"];
        var pieChartData = [$rootScope.LocalPrayers, $rootScope.ForeignPrayers];
        var borderColor = ['rgba(54, 162, 235, 0.2)', 'rgba(255, 99, 132, 0.2)'];
        var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
        chartService.setPieChart("localForeignRatio", labels, pieChartData, backgroundColor, borderColor);
    };
    
//<-- Fin de FUNCIONES PRIVADAS
}]);