var PrayingChain = angular.module("PrayingChain", ['ngAnimate','angularUtils.directives.dirPagination']);

PrayingChain.controller("dashboard", ['chartService', 'prayerServices', function(chartService, prayerServices) {
	var self = this;
    self.firstName = "Óscar";
    self.lastName = "Ibáñez";
    self.hideMenu = [];
    self.hideMenu[1] = true;
    self.hideMenu[2] = true;
    self.hideMenu[3] = true;
	self.TotalPrayers = "";
	self.EmptyTurns = "";
	self.TotalTurns = "";
	self.AvailableTurns = "";
	self.CommittedPrayers = "";
	self.NonCommittedPrayers = "";
	self.Redundancy = "";
	self.UsedTurnsPerc = "";
    self.showLoading = false; //no necesitamos batidora por ahora...
    
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
    
    loadCharts();
    
    //Cargamos la lista de oradores
    var prayerList = prayerServices.getPrayerList();
    prayerList.then(function(dataOut){
    	self.prayers = dataOut.data;
    }).finally(function(){
    	
    	//cargamos las estadísticas principales
    	var statistics = prayerServices.getChainStatistics();
    	statistics.then(function(dataOut){
    	}).finally(function(){
    	});
    });
    

	var statistics = prayerServices.getChainStatistics();
	statistics.then(function(dataOut){
		self.TotalPrayers = dataOut.data.TotalPrayers;
		self.EmptyTurns = fillInEmptyTurns(dataOut.data.EmptyTurns);
		self.TotalTurns = dataOut.data.TotalTurns;
		self.AvailableTurns = dataOut.data.TotalTurns - dataOut.data.TurnsCovered
		self.CommittedPrayers = dataOut.data.CommittedPrayers;
		self.NonCommittedPrayers = dataOut.data.NonCommittedPrayers;
		self.Redundancy = dataOut.data.TotalRedundancy-100;
		self.UsedTurnsPerc = dataOut.data.TurnsUsedPercentage;
	    loadCharts();
	}, function(error) {
	}).finally(function(){
			self.showLoadingStatistics = false;
	});


    
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
        var pieChartData = [self.TotalTurns,self.EmpytTurns];
        var borderColor = ['rgba(54, 162, 235, 0.2)','rgba(255, 99, 132, 0.2)'];
        var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
        chartService.setPieChart("chartCoverage", labels, pieChartData, backgroundColor, borderColor);

        //availables Vs Total pie chart
        var labels= ["Totales", "Disponibles"];
        var pieChartData = [self.TotalTurns,self.AvailableTurns];
        var borderColor = ['rgba(80, 190, 132, 0.2)', 'rgba(13, 180, 220, 0.2)'];
        var backgroundColor = ['rgba(80,190,132,1)','rgba(13, 180, 220, 1)'];
        chartService.setPieChart("availablePieChart", labels, pieChartData, backgroundColor, borderColor);
        
        //Redundancy: overoccupied
        var labels= ["No Saturados", "Saturados"];
        var pieChartData = [100,self.Redundancy];
        var borderColor = ['rgba(54, 162, 235, 0.2)','rgba(255, 99, 132, 0.2)'];
        var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
        chartService.setPieChart("redundancePieChart", labels, pieChartData, backgroundColor, borderColor);
        redundancePieChart    		
    };
    
    function fillInEmptyTurns(emptyTurns){
    	var filledString = emptyTurns;
		if (emptyTurns>0 && emptyTurns.length<3){
			for (var i = 0; i<3-emptyTurns.length; i++){
				filledString = "0"+filledString;
			}
		}
		return filledString;
    };
//<-- Fin de FUNCIONES PRIVADAS
}]);