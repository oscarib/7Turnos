var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("dashboard", ['$rootScope', 'chartService', 'prayerServices', 'DTOptionsBuilder', function($rootScope, chartService, prayerServices, DTOptionsBuilder) {
	var self = this;
	
	$rootScope.showLoadingStatistics = true;
	var errorWithServiceCall = false;
	initStatistics();
	
    self.loadStatistics = function(){
    	var statistics = prayerServices.getChainStatistics();
    	statistics.then(function(dataOut){
    		$rootScope.TotalPrayers = dataOut.data.TotalPrayers;
    		$rootScope.EmptyTurns = dataOut.data.EmptyTurns;
    		$rootScope.TotalTurns = dataOut.data.TotalTurns;
    		$rootScope.TurnsCovered = dataOut.data.TurnsCovered;
    		$rootScope.AvailableTurns = $rootScope.TotalTurns - $rootScope.TurnsCovered;
    		$rootScope.CommittedPrayers = dataOut.data.CommittedPrayers;
    		$rootScope.NonCommittedPrayers = dataOut.data.NonCommittedPrayers;
    		$rootScope.Redundancy = dataOut.data.TotalRedundancy-100;
    		$rootScope.UsedTurnsPerc = dataOut.data.TurnsUsedPercentage;
    		$rootScope.HiddenPrayers = dataOut.data.HiddenPrayers;
    		$rootScope.PublicPrayers = dataOut.data.PublicPrayers;
    		$rootScope.ForeignPrayers = dataOut.data.ForeignPrayers;
    		$rootScope.LocalPrayers = dataOut.data.LocalPrayers;
    		$rootScope.OrphanTurns = dataOut.data.OrphanTurns;
    		$rootScope.OrphanPrayers = dataOut.data.OrphanPrayers;
    		loadCharts();
    	}, function(error) {
    		if (!errorWithServiceCall){
    			errorWithServiceCall = true;
    			bootbox.alert({size:'small', message: 'Se produjo un error al solicitar las estadísticas'});
    		}
    	}).finally(function(){
    		$rootScope.showLoadingStatistics = false;
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
        var pieChartData = [self.CommittedPrayers,self.NonCommittedPrayers];
        var borderColor = ['rgba(54, 162, 235, 0.2)','rgba(255, 99, 132, 0.2)'];
        var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
        chartService.setPieChart("chartCommitted", labels, pieChartData, backgroundColor, borderColor);
        
        //Coverage pie chart
        var labels= ["Cubiertos", "Vacíos"];
        var pieChartData = [self.TurnsCovered, self.EmptyTurns];
        var borderColor = ['rgba(54, 162, 235, 0.2)','rgba(255, 99, 132, 0.2)'];
        var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
        chartService.setPieChart("chartCoverage", labels, pieChartData, backgroundColor, borderColor);

        //availables Vs Total pie chart
        var labels= ["Cubiertos", "Disponibles"];
        var pieChartData = [self.TurnsCovered,self.AvailableTurns];
        var borderColor = ['rgba(80, 190, 132, 0.2)', 'rgba(13, 180, 220, 0.2)'];
        var backgroundColor = ['rgba(80,190,132,1)','rgba(13, 180, 220, 1)'];
        chartService.setPieChart("availablePieChart", labels, pieChartData, backgroundColor, borderColor);
        
        //Redundancy: overoccupied
        var labels= ["No Saturados", "Saturados"];
        var pieChartData = [100,self.Redundancy];
        var borderColor = ['rgba(54, 162, 235, 0.2)','rgba(255, 99, 132, 0.2)'];
        var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
        chartService.setPieChart("redundancePieChart", labels, pieChartData, backgroundColor, borderColor);

        //Hidden Vs Public Prayers
        var labels= ["Públicos", "Anónimos"];
        var pieChartData = [self.PublicPrayers, self.HiddenPrayers];
        var borderColor = ['rgba(54, 162, 235, 0.2)','rgba(255, 99, 132, 0.2)'];
        var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
        chartService.setPieChart("hiddenPublicRatio", labels, pieChartData, backgroundColor, borderColor);

        //Hidden Vs Public Prayers
        var labels= ["España", "Otro País"];
        var pieChartData = [self.LocalPrayers, self.ForeignPrayers];
        var borderColor = ['rgba(80, 190, 132, 0.2)', 'rgba(13, 180, 220, 0.2)'];
        var backgroundColor = ['rgba(80,190,132,1)','rgba(13, 180, 220, 1)'];
        chartService.setPieChart("localForeignRatio", labels, pieChartData, backgroundColor, borderColor);
    };
    
    function setDatatableOptions(){
    	self.dtOptions = DTOptionsBuilder.newOptions();
    	self.dtOptions.withPaginationType('full_numbers').withDisplayLength(10).withDOM('lftpir').withLanguage({
            "sEmptyTable":     "No hay datos disponibles",
            "sInfo":           "Mostrando entradas de la _START_ a la _END_ de un total de _TOTAL_",
            "sInfoEmpty":      "Mostrando entradas de la 0 a la 0 de un total de 0",
            "sInfoFiltered":   "(filtradas de un total de _MAX_ entradas)",
            "sInfoPostFix":    "",
            "sInfoThousands":  ".",
            "sLengthMenu":     "Mostrar _MENU_ entradas",
            "sLoadingRecords": "Cargando...",
            "sProcessing":     "Procesando...",
            "sSearch":         "Filtrar:",
            "sZeroRecords":    "No hay resultados que coincidan",
            "oPaginate": {
                "sFirst":    "Primera",
                "sLast":     "Última",
                "sNext":     "Siguiente",
                "sPrevious": "Anterior"
            },
            "oAria": {
                "sSortAscending":  ": activar para ordenar ascendentemente",
                "sSortDescending": ": activar para ordenar descendentemente"
            }
        });
    };

//<-- Fin de FUNCIONES PRIVADAS
}]);