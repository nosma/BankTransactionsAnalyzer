'use strict';

app.controller('transactions', function ($scope, $resource, $http, uiGridConstants) {

  $scope.itemsByPage = 50;

  $resource("/api/bank/allTransactions").query(function(result) {
    $scope.gridOptions.data = result;
    $scope.transactions = result;
    $scope.displayedTransactions = [].concat($scope.transactions);
  });

// ***************************************************************************************************************************** //

  $scope.gridOptions = {
    showGridFooter: true,
    showColumnFooter: true,
    enableFiltering: true,
    enableGridMenu: true,
    enableSelectAll: true,
    exporterCsvFilename: 'myFile.csv',
    exporterPdfDefaultStyle: {fontSize: 9},
    exporterPdfTableStyle: {margin: [30, 30, 30, 30]},
    exporterPdfTableHeaderStyle: {fontSize: 10, bold: true, italics: true, color: 'red'},
    exporterPdfHeader: { text: "My Header", style: 'headerStyle' },
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
      { field: 'date' },
      { field: 'description' },
      { field: 'cost', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true }
  ]

  };

});