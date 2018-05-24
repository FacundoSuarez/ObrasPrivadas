(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('profesional', {
            parent: 'entity',
            url: '/profesional',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'obrasPrivadas4App.profesional.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/profesional/profesionals.html',
                    controller: 'ProfesionalController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('profesional');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('profesional-detail', {
            parent: 'profesional',
            url: '/profesional/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'obrasPrivadas4App.profesional.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/profesional/profesional-detail.html',
                    controller: 'ProfesionalDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('profesional');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Profesional', function($stateParams, Profesional) {
                    return Profesional.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'profesional',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('profesional-detail.edit', {
            parent: 'profesional-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profesional/profesional-dialog.html',
                    controller: 'ProfesionalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Profesional', function(Profesional) {
                            return Profesional.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('profesional.new', {
            parent: 'profesional',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profesional/profesional-dialog.html',
                    controller: 'ProfesionalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                profesion: null,
                                matricula: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('profesional', null, { reload: 'profesional' });
                }, function() {
                    $state.go('profesional');
                });
            }]
        })
        .state('profesional.edit', {
            parent: 'profesional',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profesional/profesional-dialog.html',
                    controller: 'ProfesionalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Profesional', function(Profesional) {
                            return Profesional.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('profesional', null, { reload: 'profesional' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('profesional.delete', {
            parent: 'profesional',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/profesional/profesional-delete-dialog.html',
                    controller: 'ProfesionalDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Profesional', function(Profesional) {
                            return Profesional.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('profesional', null, { reload: 'profesional' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
