app.factory("monthlyIncomeService", function ($http) {
    var getMonthStats = {
        async: function(year,month) {
            var promise = $http.get('monthlyExpenses/'+year+'/'+month).then(function (response) {
                console.log(response);
                return response.data;
            });
            return promise;
        }
    };
    return getMonthStats;
});