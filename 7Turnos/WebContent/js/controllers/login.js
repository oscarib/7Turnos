var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("login", ['$location', 'prayerServices', '$rootScope', 'pcUtils', function ($location, prayerServices, $rootScope, pcUtils) {
    var self = this;

    self.initialize = function () {
        $rootScope.maxUserRole = -1;
        $rootScope.userName = "";
        $rootScope.authenticated = false;
    };

    self.initialize();

    $rootScope.getLoggedUser = function () {
        var loggedUser = prayerServices.getLoggedUser();
        loggedUser.then(function (dataOut) {
            var loggedUser = dataOut.data;
            if (!loggedUser.username) {
                $rootScope.authenticated = false;
            } else {
                $rootScope.authenticated = true;
                $rootScope.maxUserRole = getMaxUserRole(dataOut.data.authorities);
                $rootScope.userName = dataOut.data.username;

                //Get The Chain Name
                var whenPcName = pcUtils.getChainName(dataOut.data.chain);
                whenPcName.then(function (dataOut) {
                    $rootScope.pcName = dataOut.data;
                }, function () {
                    $rootScope.pcName = "Query Failed";
                });
            }
        });
    };

    $rootScope.getLoggedUser();

    self.login = function () {

        $rootScope.authenticated = false;

        var whenPropertiesLoaded = pcUtils.getProperties();
        whenPropertiesLoaded.then(function (properties) {
            var whenCredentialsLoaded = prayerServices.login(self.credentials);
            whenCredentialsLoaded.then(function (credentials) {
                if (credentials.data.username) {
                    $rootScope.authenticated = true;
                    $rootScope.getLoggedUser();
                } else {
                    bootbox.alert({size: 'small', message: properties.data.label_loginError});
                }
            }, function (error) {
                bootbox.alert({size: 'small', message: properties.data.label_errorCheckingCredentials});
            });
        });
    };

    //Returns the value of the maximum user role
    function getMaxUserRole(roles) {
        var roleList = ["ROLE_USER", "ROLE_ADMIN", "ROLE_SU"];
        var maxUserRole = 0;
        for (rol in roles) {
            var roleIndex = roleList.indexOf(roles[rol].authority);
            maxUserRole = roleIndex > maxUserRole ? roleIndex : maxUserRole;
        }
        return maxUserRole;
    };

    $rootScope.logout = function () {
        var promise = prayerServices.logout();
        promise.finally(function () {
            self.initialize();
        });
    };

}]);