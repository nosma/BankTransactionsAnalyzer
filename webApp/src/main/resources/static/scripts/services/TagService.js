app.factory('TagService', function ($http) {

  var TAGS_API = "/api/tags/";

  return {

    getTags: function () {
      return $http({
        method: 'POST',
        url: TAGS_API + 'getPredefinedTags'
      });
    },

    setTransactionTags: function (tagDescription) {
      return $http({
        method: 'POST',
        url: TAGS_API + 'setTagsForTransaction',
        data: tagDescription
      });
    }

  };
});