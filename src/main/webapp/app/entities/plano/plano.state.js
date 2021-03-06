(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('plano', {
            parent: 'entity',
            url: '/plano',
            data: {
                authorities: ['ROLE_PROFESIONAL', 'ROLE_ADMIN'],
                pageTitle: 'obrasPrivadas4App.plano.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/plano/planos.html',
                    controller: 'PlanoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('plano');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('plano-detail', {
            parent: 'plano',
            url: '/plano/{id}',
            data: {
                authorities: ['ROLE_PROFESIONAL', 'ROLE_ADMIN'],
                pageTitle: 'obrasPrivadas4App.plano.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/plano/plano-detail.html',
                    controller: 'PlanoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('plano');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Plano', function($stateParams, Plano) {
                    return Plano.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'plano',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('plano-detail.edit', {
            parent: 'plano-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_PROFESIONAL', 'ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plano/plano-dialog.html',
                    controller: 'PlanoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Plano', function(Plano) {
                            return Plano.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('plano.new', {
            parent: 'plano',
            url: '/new',
            data: {
                authorities: ['ROLE_PROFESIONAL', 'ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plano/plano-dialog.html',
                    controller: 'PlanoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fecha: null,
                                cuitResponsable: null,
                                responsable: null,
                                id: null
                            };
                        },
                        entityDetalle: function () {
                            return {
                                tipoPlano: null,
                                observaciones: null,
                                archivo: {},
                                id: null
                            };
                        },
                        entityTramite: function () {
                        	return {
                                fecha: null,
                                fechaFin: null,
                                observaciones: null,
                                id: null
                            };
                        },
                        entityArchivo: function () {
                            return {
                            	archivo: null,
                                archivoContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('plano', null, { reload: 'plano' });
                }, function() {
                    $state.go('plano');
                });
            }]
        })
        .state('plano.edit', {
            parent: 'plano',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_PROFESIONAL', 'ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plano/plano-dialog.html',
                    controller: 'PlanoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Plano', function(Plano) {
                            return Plano.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('plano', null, { reload: 'plano' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('plano.delete', {
            parent: 'plano',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_PROFESIONAL', 'ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plano/plano-delete-dialog.html',
                    controller: 'PlanoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Plano', function(Plano) {
                            return Plano.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('plano', null, { reload: 'plano' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
