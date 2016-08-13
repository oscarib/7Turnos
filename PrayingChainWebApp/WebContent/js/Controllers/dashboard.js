var PrayingChain = angular.module("PrayingChain", ['ngAnimate','angularUtils.directives.dirPagination']);

PrayingChain.controller("dashboard", ['chartService', 'prayerServices', function(chartService, prayerServices) {
	var self = this;
    self.firstName = "John";
    self.lastName = "Doe";
    self.hideMenu = [];
    self.hideMenu[1] = true;
    self.hideMenu[2] = true;
    self.hideMenu[3] = true;
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
    

    
//FUNCIONES PRIVADAS -->
    function loadCharts(){
        //Committed / Non committed pie chart
        var labels= ["Comprometidos", "No Comprometidos"];
        var pieChartData = [973,116];
        var borderColor = ['rgba(54, 162, 235, 0.2)','rgba(255, 99, 132, 0.2)'];
        var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
        chartService.setPieChart("chartCommitted", labels, pieChartData, backgroundColor, borderColor);
        
        //Coverage pie chart
        var labels= ["Cubiertos", "Vacíos"];
        var pieChartData = [1680,1];
        var borderColor = ['rgba(54, 162, 235, 0.2)','rgba(255, 99, 132, 0.2)'];
        var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
        chartService.setPieChart("chartCoverage", labels, pieChartData, backgroundColor, borderColor);

        //availables Vs Total pie chart
        var labels= ["Totales", "Disponibles"];
        var pieChartData = [1680,715];
        var borderColor = ['rgba(80, 190, 132, 0.2)', 'rgba(13, 180, 220, 0.2)'];
        var backgroundColor = ['rgba(80,190,132,1)','rgba(13, 180, 220, 1)'];
        chartService.setPieChart("availablePieChart", labels, pieChartData, backgroundColor, borderColor);
        
        //Redundancy: overoccupied
        var labels= ["Normales	", "Sobresaturados"];
        var pieChartData = [100,13];
        var borderColor = ['rgba(54, 162, 235, 0.2)','rgba(255, 99, 132, 0.2)'];
        var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
        chartService.setPieChart("redundancePieChart", labels, pieChartData, backgroundColor, borderColor);
        redundancePieChart    		
    };
//<-- Fin de FUNCIONES PRIVADAS
}]);