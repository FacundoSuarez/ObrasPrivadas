(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('TipoContactoDetailController', TipoContactoDetailController);

    TipoContactoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TipoContacto', 'ContactoOperador', 'ContactoProfesional'];

    function TipoContactoDetailController($scope, $rootScope, $stateParams, previousState, entity, TipoContacto, ContactoOperador, ContactoProfesional) {
        var vm = this;

        vm.tipoContacto = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('obrasPrivadas4App:tipoContactoUpdate', function(event, result) {
            vm.tipoContacto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
