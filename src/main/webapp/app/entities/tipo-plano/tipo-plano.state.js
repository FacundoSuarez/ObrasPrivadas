(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tipo-plano', {
            parent: 'entity',
            url: '/tipo-plano',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'obrasPrivadas4App.tipoPlano.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-plano/tipo-planos.html',
                    controller: 'TipoPlanoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoPlano');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tipo-plano-detail', {
            parent: 'tipo-plano',
            url: '/tipo-plano/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'obrasPrivadas4App.tipoPlano.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-plano/tipo-plano-detail.html',
                    controller: 'TipoPlanoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoPlano');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TipoPlano', function($stateParams, TipoPlano) {
                    return TipoPlano.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tipo-plano',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tipo-plano-detail.edit', {
            parent: 'tipo-plano-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-plano/tipo-plano-dialog.html',
                    controller: 'TipoPlanoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoPlano', function(TipoPlano) {
                            return TipoPlano.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-plano.new', {
            parent: 'tipo-plano',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-plano/tipo-plano-dialog.html',
                    controller: 'TipoPlanoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                descripcion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tipo-plano', null, { reload: 'tipo-plano' });
                }, function() {
                    $state.go('tipo-plano');
                });
            }]
        })
        .state('tipo-plano.edit', {
            parent: 'tipo-plano',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-plano/tipo-plano-dialog.html',
                    controller: 'TipoPlanoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoPlano', function(TipoPlano) {
                            return TipoPlano.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-plano', null, { reload: 'tipo-plano' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-plano.delete', {
            parent: 'tipo-plano',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-plano/tipo-plano-delete-dialog.html',
                    controller: 'TipoPlanoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TipoPlano', function(TipoPlano) {
                            return TipoPlano.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-plano', null, { reload: 'tipo-plano' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
