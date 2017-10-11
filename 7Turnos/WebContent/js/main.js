var PrayingChain = angular.module("PrayingChain", ['ngAnimate', 'ngTable', 'ngRoute']);

PrayingChain.config(function ($routeProvider, $httpProvider) {
    $routeProvider
        .when("/", {
            controller: "dashboard",
            controllerAs: "vm",
            templateUrl: "pages/dashboard.html"
        })
        .when("/crearOrador", {
            controller: "newPrayer",
            controllerAs: "vm",
            templateUrl: "pages/newPrayer.html"
        })
        .when("/oradores", {
            controller: "prayerList",
            controllerAs: "vm",
            templateUrl: "pages/prayerList.html"
        })
        .when("/turnos", {
            controller: "turnList",
            controllerAs: "vm",
            templateUrl: "pages/turnList.html"
        })
        .when("/upload", {
            controller: "upload",
            controllerAs: "vm",
            templateUrl: "pages/upload.html"
        })
        .when("/orador/:prayerID", {
            controller: "prayerCard",
            controllerAs: "vm",
            templateUrl: "pages/prayerCard.html"
        })
        .when("/oradores/:filterString", {
            controller: "prayerList",
            controllerAs: "vm",
            templateUrl: "pages/prayerList.html"
        })
        .when("/calendar", {
            controller: "calendar",
            controllerAs: "vm",
            templateUrl: "pages/calendar.html"
        })
        .when("/configuracion", {
            controller: "configuration",
            controllerAs: "vm",
            templateUrl: "pages/configuration.html"
        })
        .otherwise({
            redirectTo: '/'
        });

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    // CSRF headers names to fit spring csrf filter
    $httpProvider.defaults.xsrfCookieName = 'XSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';
});

PrayingChain.factory('httpInterceptor', function ($q, $rootScope, $log) {
    return {
        request: function (config) {
            return config || $q.when(config)
        },
        response: function (response) {
            return response || $q.when(response);
        },
        responseError: function (response) {
            if (response.status !== 401) {
                console.error("Ha habido un error " + response.status);
            }
            return $q.reject(response);
        }
    };
})
    .config(function ($httpProvider) {
        $httpProvider.interceptors.push('httpInterceptor');
    });

PrayingChain.controller("main", ['$scope', '$rootScope', '$window', function ($scope, $rootScope, $window) {
    var self = this;

    //Para proteger las dimensiones mínimas de la aplicación
    $rootScope.isMinimumSize = false;
    $scope.minWidth = "800";
    $scope.minHeight = "600";
    $scope.sizeWidth = 0;
    $scope.sizeHeight = 0;

    var w = angular.element($window);

    $scope.getWindowDimensions = function () {
        return {
            'h': w.height(),
            'w': w.width()
        };
    };

    $scope.$watch($scope.getWindowDimensions, function (newValue, oldValue) {
        $scope.sizeWidth = newValue.w;
        $scope.sizeHeight = newValue.h;
        if (newValue.w < $scope.minWidth || newValue.h < $scope.minHeight) {
            $rootScope.isMinimumSize = true;
        } else {
            $rootScope.isMinimumSize = false;
        }
    }, true);

    w.bind('resize', function () {
        $scope.$apply();
    });
}]);