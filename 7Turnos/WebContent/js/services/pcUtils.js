(function () {
    'use strict';

    angular.module('PrayingChain').factory('pcUtils', pcUtils);

    /** @ngInject **/
    function pcUtils($q, $http, $rootScope) {
        var self = this;

        self.getBackendData = function (url, requestData) {
            var defered = $q.defer();
            var promise = defered.promise;

            $http({
                url: url,
                dataType: 'json',
                method: 'POST',
                data: requestData,
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function (dataOut) {
                defered.resolve(dataOut);
            }, function (error) {
                defered.reject(error);
            });

            return promise;
        };

        self.getProperties = function () {
            var defered = $q.defer();
            var promise = defered.promise;

            if (!self.dataProperties) {
                $http({
                    url: "./getProperties.do",
                    dataType: 'json',
                    method: 'POST',
                    headers: {
                        "Content-Type": "application/json"
                    }
                }).then(function (dataOut) {
                    self.dataProperties = dataOut
                    defered.resolve(dataOut);
                }, function (error) {
                    defered.reject(error);
                });
            } else {
                defered.resolve(self.dataProperties);
            }

            return promise;
        };

        self.getConfiguration = function () {
            var defered = $q.defer();
            var promise = defered.promise;

            $http({
                url: "./getConfiguration.do",
                dataType: 'json',
                method: 'POST',
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function (dataOut) {
                defered.resolve(dataOut);
            }, function (error) {
                defered.reject(error);
            });

            return promise;
        };

        self.setConfiguration = function (dataIn) {
            var defered = $q.defer();
            var promise = defered.promise;

            $http({
                url: "./setConfiguration.do",
                dataType: 'json',
                data: dataIn,
                method: 'POST',
                headers: {
                    "Content-Type": "application/json"
                }
            }).then(function (dataOut) {
                defered.resolve(dataOut);
            }, function (error) {
                defered.reject(error);
            });

            return promise;
        };

        self.saveChainName = function (chain, name) {
            var defered = $q.defer();
            var promise = defered.promise;
            var bean = {};
            bean.chain = chain;
            bean.name = name;

            $http({
                url: "./saveChainName.do",
                data: bean,
                method: 'POST',
                //This is for avoiding angular to parse to JSON
                transformResponse: [function (data) {
                    return data;
                }]
            }).then(function (dataOut) {
                defered.resolve(dataOut);
            }, function (error) {
                defered.reject(error);
            });

            return promise;
        };

        self.getChainName = function (chainNumber) {
            var defered = $q.defer();
            var promise = defered.promise;

            $http({
                url: "./getChainName.do",
                data: chainNumber,
                method: 'POST',
                //This is for avoiding angular to parse to JSON
                transformResponse: [function (data) {
                    return data;
                }]
            }).then(function (chainName) {
                defered.resolve(chainName);
            }, function (error) {
                defered.reject(error);
            });

            return promise;
        };

        return self;
    }

})();