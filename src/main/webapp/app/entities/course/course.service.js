(function() {
    'use strict';
    angular
        .module('smartWebApp')
        .factory('Course', Course);

    Course.$inject = ['$resource'];

    function Course ($resource) {
        var resourceUrl =  'api/courses/:id';

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
            'update': { method:'PUT' },
            'save': { method:'POST', headers : {
                    'Content-Type' : undefined
                }}
        });
    }
})();
