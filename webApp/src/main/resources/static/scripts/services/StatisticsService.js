app.factory('StatisticsService', function ($http, $resource) {

  var STATISTICS_API = "/api/statistics/";

  return {

    getMonthlyIncomeTags: function (year, month) {
      return $http({method: 'POST',
        url: STATISTICS_API + 'monthlyIncomeTags/' + year + '/' + month
      });
    },

    getMonthlyExpenseTags: function (year, month) {
      return $http({method: 'POST',
        url: STATISTICS_API + 'monthlyExpenseTags/' + year + '/' + month
      });
    },

    getInitialBalance: function () {
      return $http({method: 'GET',
        url: STATISTICS_API + "initialBalance"
      });
    },

    getMedianMonthlyExpense : function () {
    return $http({method: 'GET',
      url: STATISTICS_API + "medianMonthlyExpense"})
    },

    getMedianMonthlyIncome : function () {
      return $http({
        method: 'GET',
        url: STATISTICS_API + "medianMonthlyIncome"
      })
    },

    monthlyPnL : function () {
      return $resource(STATISTICS_API + "monthly");
    }

  }

});