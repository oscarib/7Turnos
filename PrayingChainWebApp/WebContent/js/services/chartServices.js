(function() {
    'use strict';

    angular.module('PrayingChain')
        .factory('chartService', chartService);

    /** @ngInject **/
    function chartService () {
        var self = this;
        
        self.setPieChart = function(chartObj, labels, pieChartData, borderColor, backgroundColor){
        	
            var pieChart = document.getElementById(chartObj);
            
            var data = 
            {
                	labels: labels, 
                	datasets: [{
                		data: pieChartData,
                		backgroundColor: backgroundColor,
                		borderColor: borderColor,
                		borderWidth: 1
                	}]
            };

            var options = 
            {
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
            
            var myDoughnutChart = new Chart(pieChart, {
                type: 'doughnut',
                data: data,
                options: options
            });
        };
        
        return self;
    }
})();