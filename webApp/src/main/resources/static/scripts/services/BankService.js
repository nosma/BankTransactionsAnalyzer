app.factory('BankService', function ($http) {

  var BANK_API = "/api/bank/";

  return {

    getAllTransactions: function () {
      return $http({
        method: 'POST',
        url: BANK_API + 'getAllTransactions'
      });
    }

  };
});
