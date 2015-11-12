'use strict';

app.config(function ($routeProvider) {
  $routeProvider.
    when('/Transactions', {
      templateUrl: 'views/transactions.html',
      controller: 'transactions'
    }).when('/Statistics', {
      templateUrl: 'views/statistics.html'
      //controller: 'statistics'
    }).when('/Home', {
      templateUrl: 'views/home.html'
    }).otherwise({
      templateUrl: 'views/home.html'
    });
});