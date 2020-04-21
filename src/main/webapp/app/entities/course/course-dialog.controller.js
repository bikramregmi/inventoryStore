(function() {
    'use strict';

    angular
        .module('smartWebApp')
        .controller('CourseDialogController', CourseDialogController);

    CourseDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Course'];

    function CourseDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Course) {
        var vm = this;

        vm.course = entity;
        vm.clear = clear;
        vm.save = save;

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
            vm.final.append('course', angular.toJson(vm.course, true));
            Course.save(vm.final, onSaveSuccess, onSaveError);
            vm.isSaving = true;
          /*  if (vm.course.id !== null) {
                Course.update(vm.course, onSaveSuccess, onSaveError);
            } else {
                Course.save(vm.course, onSaveSuccess, onSaveError);
            }*/
        }

        function onSaveSuccess (result) {
            $scope.$emit('smartWebApp:courseUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
