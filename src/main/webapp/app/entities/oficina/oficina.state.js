(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('oficina', {
            parent: 'entity',
            url: '/oficina',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'obrasPrivadas4App.oficina.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/oficina/oficinas.html',
                    controller: 'OficinaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('oficina');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('oficina-detail', {
            parent: 'oficina',
            url: '/oficina/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'obrasPrivadas4App.oficina.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/oficina/oficina-detail.html',
                    controller: 'OficinaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('oficina');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Oficina', function($stateParams, Oficina) {
                    return Oficina.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'oficina',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('oficina-detail.edit', {
            parent: 'oficina-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/oficina/oficina-dialog.html',
                    controller: 'OficinaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Oficina', function(Oficina) {
                            return Oficina.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('oficina.new', {
            parent: 'oficina',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/oficina/oficina-dialog.html',
                    controller: 'OficinaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                profesional: null,
                                correcciones: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('oficina', null, { reload: 'oficina' });
                }, function() {
                    $state.go('oficina');
                });
            }]
        })
        .state('oficina.edit', {
            parent: 'oficina',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/oficina/oficina-dialog.html',
                    controller: 'OficinaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Oficina', function(Oficina) {
                            return Oficina.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('oficina', null, { reload: 'oficina' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('oficina.delete', {
            parent: 'oficina',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/oficina/oficina-delete-dialog.html',
                    controller: 'OficinaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Oficina', function(Oficina) {
                            return Oficina.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('oficina', null, { reload: 'oficina' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
