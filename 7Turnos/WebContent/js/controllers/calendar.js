var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("calendar", ['prayerServices', '$location', '$sce', '$rootScope', function (prayerServices, $location, $sce, $rootScope) {
    var self = this;
    $rootScope.batidoraGeneralText = 'Construyendo el calendario. Por favor, espere...';
    $rootScope.batidoraGeneral = true;

    //Activates menu
    $rootScope.activateMenu = [];
    $rootScope.activateMenu['/calendar'] = "activeMenuItem";
    $rootScope.openMenu(2);

    var promise = prayerServices.getCalendarHtml();
    promise.then(function (dataOut) {
        self.calendar = $sce.trustAsHtml(dataOut.data[0]);
        $rootScope.batidoraGeneral = false;
    }, function (error) {
        bootbox.alert({size: 'small', message: 'Hubo un problema al solicitar el calendario'});
    }).finally(function () {
        //Aquí el código que deberá ejecutarse al finalizar la llamada, independientemente del resultado de la llamada

    });

}]);