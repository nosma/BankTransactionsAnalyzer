'use strict';

app.controller('StatisticsCtrl', function ($scope, $http, $resource, StatisticsService, TransactionService) {

  $scope.showMonthTransactions = false;
  $scope.showMonthTags = false;
  $scope.sortType = 'date';
  $scope.sortReverse = false;

  StatisticsService.getInitialBalance()
  .then(function successCallback(response) {
    $scope.initialBalance = response.data;
  }, function errorCallback(response) {
    $scope.initialBalance = response.data;
  });

  StatisticsService.getMedianMonthlyExpense()
  .then(function successCallback(response) {
    $scope.averageMonthlyExpense = roundNumber(response.data);
  }, function errorCallback(response) {
    $scope.averageMonthlyExpense = response.data;
  });

  StatisticsService.getMedianMonthlyIncome()
  .then(function successCallback(response) {
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

  $scope.getMonthlyIncomeInfo = function (year, month) {
    getMonthlyIncome(year, month);
    getMonthlyTags(year, month);
  };

  $scope.getMonthlyExpenseInfo = function (year, month) {
    getMonthlyExpenses(year, month);
    getMonthlyTags(year, month);
  };

  StatisticsService.monthlyPnL()
    .query(function(result) {
    $scope.gridOptions.data = result;
    $scope.transactionsStats = result;

    $scope.expenseTotal = getTotalExpense(result);
    $scope.incomeTotal = getTotalIncome(result);
    $scope.profitTotal = getTotalProfit(result);

    $scope.displayedTransactionsStats = [].concat($scope.transactionsStats);
  });

  function getMonthlyIncome (year, month) {
    TransactionService.getMonthlyIncome(year, month)
    .then(function successCallback(response) {
      $scope.monthList = response.data;
      $scope.displayedMonthList = [].concat(response.data);
    }, function errorCallback(response) {
      $scope.monthList = response.data;
    });
    $scope.showMonthTransactions = true;
  }

  function getMonthlyExpenses (year, month) {
    TransactionService.getMonthlyExpenses(year, month)
    .then(function successCallback(response) {
      $scope.monthList = response.data;
      $scope.displayedMonthList = [].concat(response.data);
    }, function errorCallback(response) {
      $scope.monthList = response.data;
    });
  $scope.showMonthTransactions = true;
  }

  function getMonthlyTags (year, month) {
    StatisticsService.getMonthlyTags(year,month)
      .then(function successCallback(response) {
      $scope.monthlyTags = response.data;
      $scope.displayedmonthlyTags = [].concat(response.data);
    }, function errorCallback(response) {
      $scope.monthlyTags = response.data;
    });
    $scope.showMonthTags = true;
  }

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

});
