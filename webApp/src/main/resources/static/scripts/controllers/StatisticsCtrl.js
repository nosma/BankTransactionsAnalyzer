'use strict';

app.controller('StatisticsCtrl', ['$scope','$http','$resource', function ($scope, $http, $resource) {

  $scope.showMonthTransactions = false;
  $scope.showMonthTags = false;
  $scope.sortType = 'date';
  $scope.sortReverse = false;

  $http({method: 'GET', url: "/api/statistics/initialBalance"
  }).then(function successCallback(response) {
    $scope.initialBalance = response.data;
  }, function errorCallback(response) {
    $scope.initialBalance = response.data;
  });

  $http({method: 'GET', url: "/api/statistics/medianMonthlyExpense"
  }).then(function successCallback(response) {
    $scope.averageMonthlyExpense = roundNumber(response.data);
  }, function errorCallback(response) {
    $scope.averageMonthlyExpense = response.data;
  });

  $http({method: 'GET', url: "/api/statistics/medianMonthlyIncome"
  }).then(function successCallback(response) {
    $scope.averageMonthlyIncome = roundNumber(response.data);
  }, function errorCallback(response) {
    $scope.averageMonthlyIncome = response.data;
  });

  var roundNumber = function(num){
    return Math.round(num * 100) / 100
  };

  var getTotalProfit = function(data){
    var total = 0;
    for(var i = 0; i < data.length; i++){
      total += data[i].expense;
      total += data[i].income;
    }
    return roundNumber(total - $scope.initialBalance);
  };

  var getTotalExpense = function(data){
    var total = 0;
    for(var i = 0; i < data.length; i++){
        total += data[i].expense;
    }
    return roundNumber(total);
  };

  var getTotalIncome = function(data){
    var total = 0;
    for(var i = 0; i < data.length; i++){
        total += data[i].income;
    }
    return roundNumber(total);
  };

  $resource("/api/statistics/monthly").query(function(result) {
    $scope.gridOptions.data = result;
    $scope.transactionsStats = result;

    $scope.expenseTotal = getTotalExpense(result);
    $scope.incomeTotal = getTotalIncome(result);
    $scope.profitTotal = getTotalProfit(result);

    $scope.displayedTransactionsStats = [].concat($scope.transactionsStats);
  });

  $scope.callMonthlyIncome = function (year,month) {
    $http({
      method: 'GET',
      url: '/api/bank/monthlyIncomeList/'+year+'/'+month
    }).then(function successCallback(response) {
      $scope.monthList = response.data;
      $scope.displayedMonthList = [].concat(response.data);
    }, function errorCallback(response) {
      $scope.monthList = response.data;
    });
    $scope.showMonthTransactions = true;
  };

  $scope.callMonthlyExpenses = function (year,month) {
    $http({
      method: 'GET',
      url: '/api/bank/monthlyExpensesList/'+year+'/'+month
    }).then(function successCallback(response) {
      $scope.monthList = response.data;
      $scope.displayedMonthList = [].concat(response.data);
    }, function errorCallback(response) {
      $scope.monthList = response.data;
    });
  $scope.showMonthTransactions = true;
  };

  $scope.callMonthlyTags = function (year,month) {
    $http({
      method: 'GET',
      url: '/api/statistics/monthlyTags/' + year + '/' + month
    }).then(function successCallback(response) {
      $scope.monthlyTags = response.data;
      $scope.displayedmonthlyTags = [].concat(response.data);
    }, function errorCallback(response) {
      $scope.monthlyTags = response.data;
    });
    $scope.showMonthTags = true;
  };

  $scope.gridOptions = {
    showGridFooter: true,
    showColumnFooter: true,
    enableFiltering: true,
    enableGridMenu: true,
    enableSelectAll: true,
    exporterCsvFilename: 'monthlyBankStatistics.csv',
    exporterPdfDefaultStyle: {fontSize: 9},
    exporterPdfTableStyle: {margin: [30, 30, 30, 30]},
    exporterPdfTableHeaderStyle: {fontSize: 10, bold: true, italics: true, color: 'red'},
    exporterPdfHeader: { text: "Monthly Bank Statistics", style: 'headerStyle' },
    exporterPdfFooter: function ( currentPage, pageCount ) {
      return { text: currentPage.toString() + ' of ' + pageCount.toString(), style: 'footerStyle' };
    },
    exporterPdfCustomFormatter: function ( docDefinition ) {
      docDefinition.styles.headerStyle = { fontSize: 22, bold: true };
      docDefinition.styles.footerStyle = { fontSize: 10, bold: true };
      return docDefinition;
    },
    exporterPdfOrientation: 'portrait',
    exporterPdfPageSize: 'LETTER',
    exporterPdfMaxGridWidth: 500,
    exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location")),
    onRegisterApi: function(gridApi){
      $scope.gridApi = gridApi;
    },

    columnDefs: [
      //{ field: 'ID' },
      { field: 'yearMonth.year' },
      { field: 'yearMonth.monthOfYear' },
      { field: 'income' },
      { field: 'expense' },
      { field: 'profit' }
    ]
  };

}]);
