app.factory('TagService', function ($http) {

  var TAGS_API = "/api/tags/";

  return {

    getTags: function () {
      return $http({
        method: 'POST',
        url: TAGS_API + 'getPredefinedTags',
        cache: true
      });
    },

    setTransactionTags: function (tagDescription) {
      return $http({
        method: 'POST',
        url: TAGS_API + 'setTagsForTransaction',
        data: tagDescription
      });
    },

    getTaggedTransactionsCount: function () {
      return $http({
        method: 'GET',
        url: TAGS_API + 'getTaggedTransactionsCount'
      });
    },

    getUnTaggedTransactionsCount: function () {
      return $http({
        method: 'GET',
        url: TAGS_API + 'getUnTaggedTransactionsCount'
      });
    },

    getTaggedTransactions: function () {
      return $http({
        method: 'GET',
        url: TAGS_API + 'getTaggedTransactions'
      });
    },

    getUnTaggedTransactions: function () {
      return $http({
        method: 'GET',
        url: TAGS_API + 'getUnTaggedTransactions'
      });
    }

  };
});