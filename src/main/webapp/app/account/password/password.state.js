(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('password', {
            parent: 'account',
            url: '/password',
            data: {
                authorities: ['ROLE_USER', 'ROLE_ADMIN','ROLE_OPERADOR','ROLE_PROFESIONAL','ROLE_DIRECTOR'],
                pageTitle: 'global.menu.account.password'
            },
            views: {
                'content@': {
                    templateUrl: 'app/account/password/password.html',
                    controller: 'PasswordController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('password');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
