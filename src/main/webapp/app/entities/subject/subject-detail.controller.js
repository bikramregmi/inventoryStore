(function() {
    'use strict';

    angular
        .module('smartWebApp')
        .controller('SubjectDetailController', SubjectDetailController);

    SubjectDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Subject', 'Course'];

    function SubjectDetailController($scope, $rootScope, $stateParams, previousState, entity, Subject, Course) {
        var vm = this;

        vm.subject = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('smartWebApp:subjectUpdate', function(event, result) {
            vm.subject = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
