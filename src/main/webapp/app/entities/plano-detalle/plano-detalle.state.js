(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('plano-detalle', {
            parent: 'entity',
            url: '/plano-detalle?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'obrasPrivadas4App.planoDetalle.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/plano-detalle/plano-detalles.html',
                    controller: 'PlanoDetalleController',
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
                    $translatePartialLoader.addPart('planoDetalle');
                    $translatePartialLoader.addPart('estadoPlano');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('plano-detalle-detail', {
            parent: 'plano-detalle',
            url: '/plano-detalle/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'obrasPrivadas4App.planoDetalle.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/plano-detalle/plano-detalle-detail.html',
                    controller: 'PlanoDetalleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('planoDetalle');
                    $translatePartialLoader.addPart('estadoPlano');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PlanoDetalle', function($stateParams, PlanoDetalle) {
                    return PlanoDetalle.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'plano-detalle',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('plano-detalle-detail.edit', {
            parent: 'plano-detalle-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plano-detalle/plano-detalle-dialog.html',
                    controller: 'PlanoDetalleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PlanoDetalle', function(PlanoDetalle) {
                            return PlanoDetalle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('plano-detalle.new', {
            parent: 'plano-detalle',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plano-detalle/plano-detalle-dialog.html',
                    controller: 'PlanoDetalleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                estado: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('plano-detalle', null, { reload: 'plano-detalle' });
                }, function() {
                    $state.go('plano-detalle');
                });
            }]
        })
        .state('plano-detalle.edit', {
            parent: 'plano-detalle',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plano-detalle/plano-detalle-dialog.html',
                    controller: 'PlanoDetalleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PlanoDetalle', function(PlanoDetalle) {
                            return PlanoDetalle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('plano-detalle', null, { reload: 'plano-detalle' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('plano-detalle.delete', {
            parent: 'plano-detalle',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/plano-detalle/plano-detalle-delete-dialog.html',
                    controller: 'PlanoDetalleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PlanoDetalle', function(PlanoDetalle) {
                            return PlanoDetalle.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('plano-detalle', null, { reload: 'plano-detalle' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
