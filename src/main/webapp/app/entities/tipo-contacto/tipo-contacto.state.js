(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tipo-contacto', {
            parent: 'entity',
            url: '/tipo-contacto',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'obrasPrivadas4App.tipoContacto.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-contacto/tipo-contactos.html',
                    controller: 'TipoContactoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoContacto');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tipo-contacto-detail', {
            parent: 'tipo-contacto',
            url: '/tipo-contacto/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'obrasPrivadas4App.tipoContacto.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tipo-contacto/tipo-contacto-detail.html',
                    controller: 'TipoContactoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tipoContacto');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TipoContacto', function($stateParams, TipoContacto) {
                    return TipoContacto.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tipo-contacto',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tipo-contacto-detail.edit', {
            parent: 'tipo-contacto-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-contacto/tipo-contacto-dialog.html',
                    controller: 'TipoContactoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoContacto', function(TipoContacto) {
                            return TipoContacto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-contacto.new', {
            parent: 'tipo-contacto',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-contacto/tipo-contacto-dialog.html',
                    controller: 'TipoContactoDialogController',
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
                    $state.go('tipo-contacto', null, { reload: 'tipo-contacto' });
                }, function() {
                    $state.go('tipo-contacto');
                });
            }]
        })
        .state('tipo-contacto.edit', {
            parent: 'tipo-contacto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-contacto/tipo-contacto-dialog.html',
                    controller: 'TipoContactoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TipoContacto', function(TipoContacto) {
                            return TipoContacto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-contacto', null, { reload: 'tipo-contacto' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tipo-contacto.delete', {
            parent: 'tipo-contacto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tipo-contacto/tipo-contacto-delete-dialog.html',
                    controller: 'TipoContactoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TipoContacto', function(TipoContacto) {
                            return TipoContacto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tipo-contacto', null, { reload: 'tipo-contacto' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
