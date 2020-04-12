(function() {
    'use strict';

    angular
        .module('smartWebApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state','Program','RecentCourse'];

    function HomeController ($scope, Principal, LoginService, $state,Program,RecentCourse) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.programs=Program.query();
        vm.recentCourses=RecentCourse.query();
        vm.checkProgram=checkProgram;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
            });
        }
        function register () {
            $state.go('register');
        }

        function checkProgram(clickEvent){
            vm.checkProgramName=clickEvent;
        }
    }
})();
