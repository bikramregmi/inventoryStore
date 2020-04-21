(function() {
    'use strict';
    angular
        .module('smartWebApp')
        .factory('RecentEvents', RecentEvents);

    RecentEvents.$inject = ['$resource'];

    function RecentEvents ($resource) {
        var resourceUrl =  'api/events/recent/:id';

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
