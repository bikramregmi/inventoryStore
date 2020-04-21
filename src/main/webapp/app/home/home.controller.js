(function() {
    'use strict';

    angular
        .module('smartWebApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state','Program','RecentCourse','Content','RecentNews','RecentEvents'];

    function HomeController ($scope, Principal, LoginService, $state,Program,RecentCourse,Content,RecentNews,RecentEvents) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.programs=Program.query();
        vm.recentCourses=RecentCourse.query();
        vm.checkProgram=checkProgram;
        vm.contents=Content.query();
        vm.recentEvents=RecentEvents.query();
        vm.recentNews=RecentNews.query();
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
