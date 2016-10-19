var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("dashboard", ['$rootScope', 'chartService', 'prayerServices', 'DTOptionsBuilder', function($rootScope, chartService, prayerServices, DTOptionsBuilder) {
	var self = this;
    self.firstName = "Óscar";
    self.lastName = "Ibáñez";
	self.TotalPrayers = "";
	self.EmptyTurns = "";
	self.TotalTurns = "";
	self.AvailableTurns = "";
	self.CommittedPrayers = "";
	self.NonCommittedPrayers = "";
	self.Redundancy = "";
	self.UsedTurnsPerc = "";
	self.showLoadingStatistics = true;
	self.showLoadingPrayersTable = true;
	self.showPrayersList = undefined;
	self.showHead = "activeMenuItem";
	self.showDashBoard = "activeMenuItem";
	self.showNewPrayer = false;
	self.showLoadingGeneral = false;
	self.showHeaderSearch = true;
	var errorWithServiceCall = false;
    
    self.loadStatistics = function(){
    	var statistics = prayerServices.getChainStatistics();
    	statistics.then(function(dataOut){
    		$rootScope.TotalPrayers = dataOut.data.TotalPrayers;
    		self.EmptyTurns = dataOut.data.EmptyTurns;
    		self.TotalTurns = dataOut.data.TotalTurns;
    		self.TurnsCovered = dataOut.data.TurnsCovered;
    		self.AvailableTurns = self.TotalTurns - self.TurnsCovered;
    		self.CommittedPrayers = dataOut.data.CommittedPrayers;
    		self.NonCommittedPrayers = dataOut.data.NonCommittedPrayers;
    		self.Redundancy = dataOut.data.TotalRedundancy-100;
    		self.UsedTurnsPerc = dataOut.data.TurnsUsedPercentage;
    		self.HiddenPrayers = dataOut.data.HiddenPrayers;
    		self.PublicPrayers = dataOut.data.PublicPrayers;
    		self.ForeignPrayers = dataOut.data.ForeignPrayers;
    		self.LocalPrayers = dataOut.data.LocalPrayers;
    		self.OrphanTurns = dataOut.data.OrphanTurns;
    		self.OrphanPrayers = dataOut.data.OrphanPrayers;
    		loadCharts();
    	}, function(error) {
    		if (!errorWithServiceCall){
    			errorWithServiceCall = true;
    			bootbox.alert({size:'small', message: 'Se produjo un error al solicitar las estadísticas'});
    		}
    	}).finally(function(){
    			self.showLoadingStatistics = false;
    	});
    };    
    
    self.loadStatistics();
    
//FUNCIONES PRIVADAS -->
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