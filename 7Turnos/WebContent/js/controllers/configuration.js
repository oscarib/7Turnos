var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("configuration", ['$scope', '$rootScope', 'pcUtils', function ($scope, $rootScope, pcUtils) {
    var vm = this;

    vm.configuration = {};
    vm.unchangedConfiguration = {};
    var oldChainName = "";
    vm.needSave = false;

    //Activates menu
    $rootScope.activateMenu = [];
    $rootScope.activateMenu['/configuracion'] = "activeMenuItem";
    $rootScope.openMenu(3);

    //Loading properties
    var whenProperties = pcUtils.getProperties();
    whenProperties.then(function () {
        $rootScope.batidoraGeneral = true;
        $rootScope.batidoraGeneralText = $rootScope.labels.gettingPrayerList;

        //Loading Configuration
        var configuration = pcUtils.getConfiguration();
        configuration.then(function (dataOut) {
            vm.configuration.calendarFileName = dataOut.data.calendarFileName;
            vm.configuration.emailServiceHostName = dataOut.data.emailServiceHostName;
            vm.configuration.emailServiceSmtpPort = dataOut.data.emailServiceSmtpPort;
            vm.configuration.emailServiceUserName = dataOut.data.emailServiceUserName;
            vm.configuration.emailServiceUserPassword = dataOut.data.emailServiceUserPassword;
            vm.configuration.ftpPort = dataOut.data.ftpPort;
            vm.configuration.ftpPwd = dataOut.data.ftpPwd;
            vm.configuration.ftpServerName = dataOut.data.ftpServerName;
            vm.configuration.secureFTP = dataOut.data.secureFTP;
            vm.configuration.ftpUser = dataOut.data.ftpUser;
            vm.configuration.localFilePath = dataOut.data.localFilePath;
            vm.configuration.mailchimpUrlPwd = dataOut.data.mailchimpUrlPwd;
            vm.configuration.prayersPerTurn = dataOut.data.prayersPerTurn;
            vm.configuration.remoteFilePath = dataOut.data.remoteFilePath;
            vm.configuration.statisticsFileName = dataOut.data.statisticsFileName;
            vm.configuration.emailServiceSSL = dataOut.data.emailServiceSSL;
            vm.configuration.uid = dataOut.data.uid;
            vm.unchagedConfiguration = angular.copy(vm.configuration);
            vm.configuration.chain = dataOut.data.chain;

            var chainName = pcUtils.getChainName(dataOut.data.chain);
            chainName.then(function (dataOut) {
                vm.chainName = dataOut.data;
                oldChainName = vm.chainName;
            });
        }).finally(function () {
            $rootScope.batidoraGeneral = false;
        });
    });

    vm.onChange = function () {
        vm.needSave = (angular.toJson(vm.configuration) !== angular.toJson(vm.unchagedConfiguration)) || oldChainName !== vm.chainName;
    };

    vm.updateConfiguration = function (isValid) {

        if (!vm.needSave) {
            bootbox.alert({
                size: 'small',
                message: $rootScope.labels.noNeedToSave + ". " + $rootScope.labels.noDataHasChanged
            });
            return false;
        }

        $rootScope.batidoraGeneralText = $rootScope.labels.savingConfigurationPleaseWait;
        $rootScope.batidoraGeneral = true;

        var whenConfigurationSaved = pcUtils.setConfiguration(vm.configuration);
        whenConfigurationSaved.then(function () {

            var whenChainNameSaved = pcUtils.saveChainName(vm.configuration.uid, vm.chainName);
            whenChainNameSaved.then(function () {

                bootbox.alert({size: 'small', message: $rootScope.labels.configurationSaved});
                vm.unchagedConfiguration = angular.copy(vm.configuration);
                oldChainName = vm.chainName;
                vm.needSave = false;
            }, function (error) {
                bootbox.alert({size: 'small', message: $rootScope.labels.errorSavingConfiguration});
            }).finally(function () {
                $rootScope.batidoraGeneral = false;
            });

        }, function (error) {
            bootbox.alert({size: 'small', message: $rootScope.labels.errorSavingConfiguration});
        }).finally(function () {
            $rootScope.batidoraGeneral = false;
        });
    };
}]);