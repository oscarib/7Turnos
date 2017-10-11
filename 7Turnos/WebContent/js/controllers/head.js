var PrayingChain = angular.module("PrayingChain");

PrayingChain.controller("head", ['$location', function ($location) {
    var self = this;
    self.showHeaderSearch = true;
    self.showHeaderSearch = true;
    self.EmptyTurns = 0;
    self.searchString = "";

    self.searchPrayer = function () {
        $location.path("/oradores");
        $location.search('searchString', self.searchString);
    };
}]);