var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("prayerList", ['$rootScope', 'chartService', 'prayerServices', '$location', 'NgTableParams', 'pcUtils', function ($rootScope, chartService, prayerServices, $location, NgTableParams, pcUtils) {
    var self = this;

    self.loadPrayerList = function () {
        //Cargamos la lista de oradores
        var prayerList = prayerServices.getPrayerList();
        prayerList.then(function (dataOut) {
            var originalArray = dataOut.data;
            var reformattedArray = originalArray.map(function (obj) {
                var rObj = {};
                rObj.name = obj.name;
                rObj.email = obj.email;
                rObj.ownCountry = obj.ownCountry ? '' : $rootScope.labels.foreign;
                rObj.hidden = obj.hidden ? 'X' : '-';
                rObj.phone = obj.phone;
                rObj.pseudonym = obj.pseudonym;
                rObj.uid = obj.uid;
                return rObj;
            });
            self.tableParams = new NgTableParams({}, {dataset: reformattedArray});
        }, function (error) {
            if (!errorWithServiceCall) {
                errorWithServiceCall = true;
                bootbox.alert({size: 'small', message: $rootScope.labels.errorGettingPrayerList});
            }
        }).finally(function () {
            self.showLoadingPrayersTable = false;
            $rootScope.batidoraGeneral = false;
        });
    };

    //Activates menu
    $rootScope.activateMenu = [];
    $rootScope.activateMenu['/oradores'] = "activeMenuItem";
    $rootScope.openMenu(2);

    var whenProperties = pcUtils.getProperties();
    whenProperties.then(function () {
        $rootScope.batidoraGeneral = true;
        $rootScope.batidoraGeneralText = $rootScope.labels.gettingPrayerList;
        self.loadPrayerList();
    });
}]);