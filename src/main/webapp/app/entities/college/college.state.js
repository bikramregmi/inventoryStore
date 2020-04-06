(function() {
    'use strict';

    angular
        .module('smartWebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('college', {
            parent: 'entity',
            url: '/college?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartWebApp.college.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/college/colleges.html',
                    controller: 'CollegeController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('college');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('college-detail', {
            parent: 'college',
            url: '/college/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartWebApp.college.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/college/college-detail.html',
                    controller: 'CollegeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('college');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'College', function($stateParams, College) {
                    return College.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'college',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('college-detail.edit', {
            parent: 'college-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/college/college-dialog.html',
                    controller: 'CollegeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['College', function(College) {
                            return College.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('college.new', {
            parent: 'college',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/college/college-dialog.html',
                    controller: 'CollegeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                image: null,
                                description: null,
                                extraField: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('college', null, { reload: 'college' });
                }, function() {
                    $state.go('college');
                });
            }]
        })
        .state('college.edit', {
            parent: 'college',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/college/college-dialog.html',
                    controller: 'CollegeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['College', function(College) {
                            return College.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('college', null, { reload: 'college' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('college.delete', {
            parent: 'college',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/college/college-delete-dialog.html',
                    controller: 'CollegeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['College', function(College) {
                            return College.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('college', null, { reload: 'college' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
