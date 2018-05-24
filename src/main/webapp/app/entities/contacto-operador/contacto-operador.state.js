(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('contacto-operador', {
            parent: 'entity',
            url: '/contacto-operador',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'obrasPrivadas4App.contactoOperador.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contacto-operador/contacto-operadors.html',
                    controller: 'ContactoOperadorController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contactoOperador');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('contacto-operador-detail', {
            parent: 'contacto-operador',
            url: '/contacto-operador/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'obrasPrivadas4App.contactoOperador.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contacto-operador/contacto-operador-detail.html',
                    controller: 'ContactoOperadorDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contactoOperador');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ContactoOperador', function($stateParams, ContactoOperador) {
                    return ContactoOperador.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'contacto-operador',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('contacto-operador-detail.edit', {
            parent: 'contacto-operador-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contacto-operador/contacto-operador-dialog.html',
                    controller: 'ContactoOperadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ContactoOperador', function(ContactoOperador) {
                            return ContactoOperador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('contacto-operador.new', {
            parent: 'contacto-operador',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contacto-operador/contacto-operador-dialog.html',
                    controller: 'ContactoOperadorDialogController',
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
                    $state.go('contacto-operador', null, { reload: 'contacto-operador' });
                }, function() {
                    $state.go('contacto-operador');
                });
            }]
        })
        .state('contacto-operador.edit', {
            parent: 'contacto-operador',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contacto-operador/contacto-operador-dialog.html',
                    controller: 'ContactoOperadorDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ContactoOperador', function(ContactoOperador) {
                            return ContactoOperador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('contacto-operador', null, { reload: 'contacto-operador' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('contacto-operador.delete', {
            parent: 'contacto-operador',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contacto-operador/contacto-operador-delete-dialog.html',
                    controller: 'ContactoOperadorDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ContactoOperador', function(ContactoOperador) {
                            return ContactoOperador.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('contacto-operador', null, { reload: 'contacto-operador' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
