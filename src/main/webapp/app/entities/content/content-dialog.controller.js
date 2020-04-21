(function() {
    'use strict';

    angular
        .module('smartWebApp')
        .controller('ContentDialogController', ContentDialogController);

    ContentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Content', 'College'];

    function ContentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Content, College) {
        var vm = this;

        vm.content = entity;
        vm.clear = clear;
        vm.save = save;
        vm.colleges = College.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.final = new FormData();
            vm.final.append('file', vm.file);
            console.log(vm.file);
            vm.final.append('content', angular.toJson(vm.content, true));
            Content.save(vm.final, onSaveSuccess, onSaveError);
            vm.isSaving = true;
        }

        function onSaveSuccess (result) {
            $scope.$emit('smartWebApp:contentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
