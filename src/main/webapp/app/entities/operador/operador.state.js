(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('operador', {
            parent: 'entity',
            url: '/operador',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'obrasPrivadas4App.operador.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/operador/operadors.html',
                    controller: 'OperadorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('operador');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('operador-detail', {
            parent: 'operador',
            url: '/operador/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'obrasPrivadas4App.operador.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/operador/operador-detail.html',
                    controller: 'OperadorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('operador');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Operador', function($stateParams, Operador) {
                    return Operador.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'operador',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('operador-detail.edit', {
            parent: 'operador-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/operador/operador-dialog.html',
                    controller: 'OperadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Operador', function(Operador) {
                            return Operador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('operador.new', {
            parent: 'operador',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/operador/operador-dialog.html',
                    controller: 'OperadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('operador', null, { reload: 'operador' });
                }, function() {
                    $state.go('operador');
                });
            }]
        })
        .state('operador.edit', {
            parent: 'operador',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/operador/operador-dialog.html',
                    controller: 'OperadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Operador', function(Operador) {
                            return Operador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('operador', null, { reload: 'operador' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('operador.delete', {
            parent: 'operador',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/operador/operador-delete-dialog.html',
                    controller: 'OperadorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Operador', function(Operador) {
                            return Operador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('operador', null, { reload: 'operador' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
