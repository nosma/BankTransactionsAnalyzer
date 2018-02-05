app.factory('TransactionService', function ($http) {

  var TRANSACTIONS_API = "/api/transactions/";

  return {

    getAllTransactions: function () {
      return $http({
        method: 'GET',
        url: TRANSACTIONS_API + 'getAllTransactions'
      });
    },

    getMonthlyIncome: function (year, month) {
      return $http({
        method: 'POST',
        url: TRANSACTIONS_API + 'monthlyIncomeList/' + year + '/' + month
      })
    },

    getMonthlyExpenses: function (year, month) {
      return $http({
        method: 'POST',
        url: TRANSACTIONS_API + 'monthlyExpensesList/' + year + '/' + month
      })
    }

  };
});
