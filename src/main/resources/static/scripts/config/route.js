'use strict';

app.config(function ($routeProvider) {
  $routeProvider.when('/Transactions', {
      templateUrl: 'transactions.html'
    }).when('/Statistics', {
      templateUrl: 'statistics.html', controller: 'statistics'
    }).otherwise({
      templateUrl: 'home.html', controller: 'home'
    });
});