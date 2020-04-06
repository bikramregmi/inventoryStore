(function() {
    'use strict';

    angular
        .module('smartWebApp')
        .controller('ContactDetailController', ContactDetailController);

    ContactDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Contact'];

    function ContactDetailController($scope, $rootScope, $stateParams, previousState, entity, Contact) {
        var vm = this;

        vm.contact = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartWebApp:contactUpdate', function(event, result) {
            vm.contact = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
