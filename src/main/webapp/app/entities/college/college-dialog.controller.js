(function() {
    'use strict';

    angular
        .module('smartWebApp')
        .controller('CollegeDialogController', CollegeDialogController);

    CollegeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'College', 'Address', 'Contact', 'Program'];

    function CollegeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, College, Address, Contact, Program) {
        var vm = this;

        vm.college = entity;
        vm.clear = clear;
        vm.save = save;
        vm.addresses = Address.query();
        vm.contacts = Contact.query({filter: 'college-is-null'});
        $q.all([vm.college.$promise, vm.contacts.$promise]).then(function() {
            if (!vm.college.contactId) {
                return $q.reject();
            }
            return Contact.get({id : vm.college.contactId}).$promise;
        }).then(function(contact) {
            vm.contacts.push(contact);
        });
        vm.programs = Program.query();

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
            vm.final.append('college', angular.toJson(vm.college, true));
            College.save(vm.final, onSaveSuccess, onSaveError);
            vm.isSaving = true;
            /*if (vm.college.id !== null) {
                College.update(vm.college, onSaveSuccess, onSaveError);
            } else {
                College.save(vm.college, onSaveSuccess, onSaveError);
            }*/
        }

        function onSaveSuccess (result) {
            $scope.$emit('smartWebApp:collegeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
