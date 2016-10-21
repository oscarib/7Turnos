var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("prayerList", ['$rootScope', 'chartService', 'prayerServices', 'DTOptionsBuilder', function($rootScope, chartService, prayerServices, DTOptionsBuilder) {
	var self = this;

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

    
    self.loadPrayerList();

}]);