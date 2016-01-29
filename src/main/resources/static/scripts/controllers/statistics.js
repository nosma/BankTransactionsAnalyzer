'use strict';

app.controller('statistics', ['$scope','$http','$resource','monthlyIncomeService',
                      function ($scope, $http, $resource, monthlyIncomeService) {

  $scope.moneyFlowSelected = false;

  $resource("/api/statistics/monthly").query(function(result) {
    $scope.gridOptions.data = result;
    $scope.transactionsStats = result;
    $scope.displayedTransactionsStats = [].concat($scope.transactionsStats);
  });

  $scope.callMonthlyIncome = function (year,month) {
    //monthlyIncomeService.async(year,month).then(function (data) {
    //  $scope.monthStats = data;
    //});
    //$http({
    //  method: 'GET',
    //  url: 'monthlyExpenses/'+year+'/'+month
    //}).then(function successCallback(response) {
    //  $scope.monthStats = response;
    //}, function errorCallback(response) {
    //  $scope.monthStats = response;
    //});
    $http.get('monthlyExpenses/' + year + '/' + month)
        .then(function successCallback(response) {
          $scope.monthStats = response;
        });
    $scope.moneyFlowSelected = true;
  };

  $scope.callMonthlyExpenses = function (year,month) {

  $scope.moneyFlowSelected = true;
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
