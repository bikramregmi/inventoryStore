(function() {
    'use strict';

    angular
        .module('smartWebApp')
        .controller('CollegeDetailController', CollegeDetailController);

    CollegeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'College', 'Address', 'Contact', 'Program'];

    function CollegeDetailController($scope, $rootScope, $stateParams, previousState, entity, College, Address, Contact, Program) {
        var vm = this;

        vm.college = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartWebApp:collegeUpdate', function(event, result) {
            vm.college = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
