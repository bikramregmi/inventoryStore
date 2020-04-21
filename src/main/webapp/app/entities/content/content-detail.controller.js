(function() {
    'use strict';

    angular
        .module('smartWebApp')
        .controller('ContentDetailController', ContentDetailController);

    ContentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Content', 'College'];

    function ContentDetailController($scope, $rootScope, $stateParams, previousState, entity, Content, College) {
        var vm = this;

        vm.content = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartWebApp:contentUpdate', function(event, result) {
            vm.content = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
