'use strict';

app.config(function ($routeProvider) {
    $routeProvider.
        when('/Transactions', {
            templateUrl: 'views/transactions.html',
            controller: 'transactions'
        }).when('/Statistics', {
            templateUrl: 'views/statistics.html',
            controller: 'statistics'
        }).when('/Home', {
            templateUrl: 'views/home.html'
        }).when('/Microphone', {
            templateUrl: 'views/microphone.html',
            controller: 'VoiceCtrl'
        }).otherwise({
            templateUrl: 'views/home.html'
        });
});