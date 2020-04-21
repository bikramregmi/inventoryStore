(function() {
    'use strict';
    angular
        .module('smartWebApp')
        .factory('Content', Content);

    Content.$inject = ['$resource'];

    function Content ($resource) {
        var resourceUrl =  'api/contents/:id';

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
