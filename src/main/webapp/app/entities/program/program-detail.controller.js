(function() {
    'use strict';

    angular
        .module('smartWebApp')
        .controller('ProgramDetailController', ProgramDetailController);

    ProgramDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Program', 'Course'];

    function ProgramDetailController($scope, $rootScope, $stateParams, previousState, entity, Program, Course) {
        var vm = this;

        vm.program = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartWebApp:programUpdate', function(event, result) {
            vm.program = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
