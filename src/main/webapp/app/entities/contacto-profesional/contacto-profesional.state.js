(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('contacto-profesional', {
            parent: 'entity',
            url: '/contacto-profesional',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'obrasPrivadas4App.contactoProfesional.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contacto-profesional/contacto-profesionals.html',
                    controller: 'ContactoProfesionalController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contactoProfesional');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('contacto-profesional-detail', {
            parent: 'contacto-profesional',
            url: '/contacto-profesional/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'obrasPrivadas4App.contactoProfesional.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contacto-profesional/contacto-profesional-detail.html',
                    controller: 'ContactoProfesionalDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contactoProfesional');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ContactoProfesional', function($stateParams, ContactoProfesional) {
                    return ContactoProfesional.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'contacto-profesional',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('contacto-profesional-detail.edit', {
            parent: 'contacto-profesional-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contacto-profesional/contacto-profesional-dialog.html',
                    controller: 'ContactoProfesionalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ContactoProfesional', function(ContactoProfesional) {
                            return ContactoProfesional.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('contacto-profesional.new', {
            parent: 'contacto-profesional',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contacto-profesional/contacto-profesional-dialog.html',
                    controller: 'ContactoProfesionalDialogController',
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
                    $state.go('contacto-profesional', null, { reload: 'contacto-profesional' });
                }, function() {
                    $state.go('contacto-profesional');
                });
            }]
        })
        .state('contacto-profesional.edit', {
            parent: 'contacto-profesional',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contacto-profesional/contacto-profesional-dialog.html',
                    controller: 'ContactoProfesionalDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ContactoProfesional', function(ContactoProfesional) {
                            return ContactoProfesional.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('contacto-profesional', null, { reload: 'contacto-profesional' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('contacto-profesional.delete', {
            parent: 'contacto-profesional',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contacto-profesional/contacto-profesional-delete-dialog.html',
                    controller: 'ContactoProfesionalDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ContactoProfesional', function(ContactoProfesional) {
                            return ContactoProfesional.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('contacto-profesional', null, { reload: 'contacto-profesional' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
