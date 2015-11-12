'use strict';

app.controller('home', function ($scope, $http) {

  $scope.bankTransactions = function () {
    $http.get("/api/bank/allTransactions").then(function successCallback(response) {
        $scope.allTransactions = response.data;
      }, function errorCallback(response) {
        $scope.allTransactions = null;
      });
  };

  $scope.bankTransactions();

});