//function Hello($scope, $http) {
//  $http.get('http://rest-service.guides.spring.io/greeting').
//    success(function(data) {
//      $scope.greeting = data;
//    });
//}
 var financialModule = angular.module('financial', ['ngRoute', 'ngResource', 'ui.bootstrap']);

financialModule.config();

financialModule.controller();