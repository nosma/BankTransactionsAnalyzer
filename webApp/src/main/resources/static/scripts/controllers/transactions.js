'use strict';

app.controller('transactions', function ($scope, $resource, $http, $route, uiGridConstants, TagService, BankService) {

  $scope.itemsByPage = 50;
  $scope.transactionTags = [];
  $scope.numOfTagged = -1;
  $scope.tagged = -1;
  $scope.numOfUntagged = -1;
  $scope.untagged = -1;

  function getTaggedTransactionsCount(){
    TagService.getTaggedTransactionsCount()
      .then(function successCallback(response) {
        $scope.numOfTagged = response.data;
      }, function errorCallback(response) {
        console.log("Error while receiving Tagged Transactions Count: " + response.data);
      });
  }

  function getUnTaggedTransactionsCount(){
    TagService.getUnTaggedTransactionsCount()
      .then(function successCallback(response) {
        $scope.numOfUntagged = response.data;
      }, function errorCallback(response) {
        console.log("Error while receiving UnTagged Transactions Count: " + response.data);
      });
  }

  $scope.getTagged = function(){
    TagService.getTaggedTransactions()
      .then(function successCallback(response) {
        populateTransactionsTable(response.data);
      }, function errorCallback(response) {
        console.log("Error while receiving Tagged Transactions : " + response.data);
      });
  };

  $scope.getUnTagged = function(){
    TagService.getUnTaggedTransactions()
      .then(function successCallback(response) {
        populateTransactionsTable(response.data);
      }, function errorCallback(response) {
        console.log("Error while receiving UnTagged Transactions : " + response.data);
      });
  };

  $scope.tableInit = function(){
    getAllTransactions();
  };

  function getTransactionTags(){
    TagService.getTags()
      .then(function successCallback(response) {
        $scope.transactionTags = response.data;
      }, function errorCallback(response) {
        console.log("Error while receiving tags: " + response.data);
      });
  }

  function getAllTransactions(){
    BankService.getAllTransactions().then(function successCallback(response) {
      populateTransactionsTable(response.data);
    }, function errorCallback(response) {
      console.log("Error while retrieving transactions: " + response.data);
    });
  }

  function populateTransactionsTable(data) {
    $scope.gridOptions.data = data;
    $scope.transactions = data;
    $route.refresh;
  }

  $scope.allTags = function () {
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
        $route.refresh;
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

  $scope.loadTags = function ($query) {
    return TagService.getTags()
      .then(function (response) {
      var tags = response.data;
      return tags.filter(function (tag) {
        return tag.toLowerCase().indexOf($query.toLowerCase()) != -1;
      });
    });
  };

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

  function init(){
    getTaggedTransactionsCount();
    getUnTaggedTransactionsCount();
    getAllTransactions();
  }
  init();

});
