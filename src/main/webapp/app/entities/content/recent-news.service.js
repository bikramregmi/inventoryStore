(function() {
    'use strict';
    angular
        .module('smartWebApp')
        .factory('RecentNews', RecentNews);

    RecentNews.$inject = ['$resource'];

    function RecentNews ($resource) {
        var resourceUrl =  'api/news/recent/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
