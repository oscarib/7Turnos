var PrayingChain = angular.module("PrayingChain", ['ngAnimate']);

PrayingChain.controller("dashboard", ['chartService', function(chartService) {
	var self = this;
    self.firstName = "John";
    self.lastName = "Doe";
    self.hideActions = true;
    self.hideListings = true;
    
    self.openActions = function(){
    	if (self.hideActions){
	        self.hideActions = false;
	        self.hideListings = true;
    	} else {
	        self.hideActions = true;
    	}
    };

    self.openListings = function(){
    	if (self.hideListings){
	        self.hideActions = true;
	        self.hideListings = false;
    	} else {
    		self.hideListings = true;
    	}
    };

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
    var borderColor = ['rgba(80, 99, 132, 0.2)', 'rgba(54, 162, 235, 0.2)'];
    var backgroundColor = ['rgba(80,99,132,1)','rgba(54, 162, 235, 1)'];
    chartService.setPieChart("availablePieChart", labels, pieChartData, backgroundColor, borderColor);
    
    //Redundancy: overoccupied
    var labels= ["Normales	", "Sobresaturados"];
    var pieChartData = [100,13];
    var borderColor = ['rgba(54, 162, 235, 0.2)','rgba(255, 99, 132, 0.2)'];
    var backgroundColor = ['rgba(54, 162, 235, 1)','rgba(255,99,132,1)'];
    chartService.setPieChart("redundancePieChart", labels, pieChartData, backgroundColor, borderColor);
    redundancePieChart
}]);