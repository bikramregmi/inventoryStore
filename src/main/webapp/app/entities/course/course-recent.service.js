(function() {
    'use strict';
    angular
        .module('smartWebApp')
        .factory('RecentCourse', RecentCourse);

    RecentCourse.$inject = ['$resource'];

    function RecentCourse ($resource) {
        var resourceUrl =  'api/courses/recent/:id';

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
