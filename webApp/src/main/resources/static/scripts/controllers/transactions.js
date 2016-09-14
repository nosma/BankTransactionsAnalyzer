'use strict';

app.controller('transactions', function ($scope, $resource, $http, $route, uiGridConstants, TagService, BankService) {

  $scope.itemsByPage = 50;

  function getTransactionTags(){
    TagService.getTags()
      .then(function successCallback(response) {
        $scope.transactionTags = response.data;
      }, function errorCallback(response) {
        console.log("Error while receiving tags: " + response.data);
      });
  }
  getTransactionTags();

  function getAllTransactions(){
    BankService.getAllTransactions().then(function successCallback(response) {
      $scope.gridOptions.data = response.data;
      $scope.transactions = response.data;
      $scope.displayedTransactions = [].concat(response.data);
    }, function errorCallback(response) {
      console.log("Error while retrieving transactions: " + response.data);
    });
  }
  getAllTransactions();

  $scope.resetTags = function () {
    getTransactionTags();
  };

  $scope.setDescriptionToModal = function (description) {
    $scope.tagDescription = description;
  };

  $scope.saveTagsForDescription = function (tagDescription, transactionTags) {
    TagService.setTransactionTags({
      description: tagDescription,
      tags: getTagsFromObjectArray(transactionTags)
    }).then(function successCallback(response) {
        $scope.gridOptions.data = response.data;
        $route.reload();
      $('#TransactionActions').modal('hide');
      }, function errorCallback(response) {
        console.log("Error while retrieving results: " + response.data);
      });
  };

  function getTagsFromObjectArray(array){
    var tags = new Array(array.length);
    for(var i=0; i<array.length; i++){
      tags[i] = array[i].text
    }
    return tags;
  }


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
    exporterPdfHeader: {text: "My Header", style: 'headerStyle'},
    exporterPdfFooter: function (currentPage, pageCount) {
      return {text: currentPage.toString() + ' of ' + pageCount.toString(), style: 'footerStyle'};
    },
    exporterPdfCustomFormatter: function (docDefinition) {
      docDefinition.styles.headerStyle = {fontSize: 22, bold: true};
      docDefinition.styles.footerStyle = {fontSize: 10, bold: true};
      return docDefinition;
    },
    exporterPdfOrientation: 'portrait',
    exporterPdfPageSize: 'LETTER',
    exporterPdfMaxGridWidth: 500,
    exporterCsvLinkElement: angular.element(document.querySelectorAll(".custom-csv-link-location")),
    onRegisterApi: function (gridApi) {
      $scope.gridApi = gridApi;
    },

    columnDefs: [
      {field: 'date'},
      {field: 'description'},
      {field: 'cost', aggregationType: uiGridConstants.aggregationTypes.sum, aggregationHideLabel: true}
    ]

  };

});