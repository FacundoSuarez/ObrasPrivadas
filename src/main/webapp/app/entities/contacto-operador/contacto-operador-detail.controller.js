(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('ContactoOperadorDetailController', ContactoOperadorDetailController);

    ContactoOperadorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ContactoOperador', 'TipoContacto', 'Operador'];

    function ContactoOperadorDetailController($scope, $rootScope, $stateParams, previousState, entity, ContactoOperador, TipoContacto, Operador) {
        var vm = this;

        vm.contactoOperador = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('obrasPrivadas4App:contactoOperadorUpdate', function(event, result) {
            vm.contactoOperador = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
