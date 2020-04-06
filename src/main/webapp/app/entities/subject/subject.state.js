(function() {
    'use strict';

    angular
        .module('smartWebApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('subject', {
            parent: 'entity',
            url: '/subject?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartWebApp.subject.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subject/subjects.html',
                    controller: 'SubjectController',
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
                    $translatePartialLoader.addPart('subject');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('subject-detail', {
            parent: 'subject',
            url: '/subject/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartWebApp.subject.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/subject/subject-detail.html',
                    controller: 'SubjectDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('subject');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Subject', function($stateParams, Subject) {
                    return Subject.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'subject',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('subject-detail.edit', {
            parent: 'subject-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subject/subject-dialog.html',
                    controller: 'SubjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Subject', function(Subject) {
                            return Subject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subject.new', {
            parent: 'subject',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subject/subject-dialog.html',
                    controller: 'SubjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                subject_id: null,
                                name: null,
                                description: null,
                                image: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('subject', null, { reload: 'subject' });
                }, function() {
                    $state.go('subject');
                });
            }]
        })
        .state('subject.edit', {
            parent: 'subject',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subject/subject-dialog.html',
                    controller: 'SubjectDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Subject', function(Subject) {
                            return Subject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subject', null, { reload: 'subject' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('subject.delete', {
            parent: 'subject',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/subject/subject-delete-dialog.html',
                    controller: 'SubjectDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Subject', function(Subject) {
                            return Subject.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('subject', null, { reload: 'subject' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
