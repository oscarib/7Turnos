var app = angular.module("PrayingChain", ['ngAnimate']);

app.controller("dashboard", function($scope) {
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
    
    var ctx = document.getElementById("chartCommitted");
    var ctx2 = document.getElementById("chartCoverage");
    
    var data = {
        labels: ["Comprometidos", "No comprometidos"],
        datasets: [{
            data: [973, 116],
            backgroundColor: [
                'rgba(255, 99, 132, 0.2)',
                'rgba(54, 162, 235, 0.2)'
            ],
            borderColor: [
                'rgba(255,99,132,1)',
                'rgba(54, 162, 235, 1)'
            ],
            borderWidth: 1
        }]
    };
    
    var data2 = {
            labels: ["Cubiertos", "Disponibles"],
            datasets: [{
                data: [965, 715],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.2)',
                    'rgba(54, 162, 235, 0.2)'
                ],
                borderColor: [
                    'rgba(255,99,132,1)',
                    'rgba(54, 162, 235, 1)'
                ],
                borderWidth: 1
            }]
        };

    var options = {
    		scales: {
    			yAxes: [{
    				ticks: {
    					beginAtZero:true
    				}
    			}]
    		},
    		legend: {
    			display: false
    		}
    };
    
    var myDoughnutChart = new Chart(ctx, {
        type: 'doughnut',
        data: data,
        options: options
    });

    var myDoughnutChart2 = new Chart(ctx2, {
        type: 'doughnut',
        data: data2,
        options: options
    });
    
});