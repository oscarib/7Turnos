var PrayingChain = angular.module("PrayingChain", ['ngAnimate','datatables']);

PrayingChain.controller("dashboard", ['$rootScope', 'chartService', 'prayerServices', 'DTOptionsBuilder', function($rootScope, chartService, prayerServices, DTOptionsBuilder) {
	var self = this;
    self.firstName = "Óscar";
    self.lastName = "Ibáñez";
    self.hideMenu = [];
    self.hideMenu[1] = true;
    self.hideMenu[2] = true;
    self.hideMenu[3] = true;
    self.hideMenu[4] = true;
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
	
    $rootScope.showDashBoardLayer = function(){
    	self.showPrayersList = undefined;
    	self.showDashBoard = "activeMenuItem";
    	self.showNewPrayer = false;
    	self.showHeaderSearch = true;
    	$rootScope.resetNewPrayerForm();
    };

    $rootScope.showPrayersTableLayer = function(){
    	self.showPrayersList = "activeMenuItem";
    	self.showDashBoard = undefined;
    	self.showNewPrayer = false;
    	self.showHeaderSearch = false;
    	$rootScope.resetNewPrayerForm();
    };

    $rootScope.showNewPrayerLayer = function(){
    	self.showPrayersList = undefined;
    	self.showDashBoard = undefined;
    	self.showNewPrayer = true;
    	self.showHeaderSearch = false;
    };
    
    self.uploadCalendar = function(){
    	self.showLoadingGeneral = true;
    	var uploadCalendar = prayerServices.uploadCalendar();
    	uploadCalendar.then(function() {
    		bootbox.alert({size:'small', message: 'Se ha podido actualizar el calendario correctamente'});
        	self.showLoadingGeneral = false;
    	}, function(error) {
    		bootbox.alert({size:'small', message: 'Ha habido un problema al tratar de actualizar el calendario.'+ 
    											  'Consulte la consola del navegador para más información'});
    		console.error(error);
        	self.showLoadingGeneral = false;
    	}).finally(function(){
    	});
    }
   
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
    
    self.loadPrayerList = function(){
        //Cargamos la lista de oradores
        var prayerList = prayerServices.getPrayerList();
        prayerList.then(function(dataOut){
        	self.prayers = dataOut.data;
        	setDatatableOptions();
        }, function(error) {
        	if (!errorWithServiceCall){
        		errorWithServiceCall = true;
        		bootbox.alert({size:'small', message: 'Se produjo un error al solicitar la lista de oradores'});
        	}
    	}).finally(function(){
        	self.showLoadingPrayersTable = false;
        });
    };
    
    $rootScope.loadStatistics = function(){
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
    
    self.loadPrayerList();
    $rootScope.loadStatistics();
    
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