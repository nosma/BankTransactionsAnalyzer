'use strict';

app.controller('statistics', ['$scope','$http','$resource', function ($scope, $http, $resource) {

  $scope.showMonthTransactions = false;
  $scope.sortType = 'date';
  $scope.sortReverse = false;

  $resource("/api/statistics/monthly").query(function(result) {
    $scope.gridOptions.data = result;
    $scope.transactionsStats = result;
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
