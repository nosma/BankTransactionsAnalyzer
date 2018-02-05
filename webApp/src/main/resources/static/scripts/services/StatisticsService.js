app.factory('StatisticsService', function ($http, $resource) {

  var STATISTICS_API = "/api/statistics/";

  return {

    getMonthlyTags: function (year, month) {
      return $http({method: 'POST',
        url: STATISTICS_API + 'monthlyTags/' + year + '/' + month
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