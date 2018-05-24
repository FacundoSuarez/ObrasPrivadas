(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('OperadorDetailController', OperadorDetailController);

    OperadorDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Operador', 'User', 'Oficina', 'ContactoOperador', 'Tramite'];

    function OperadorDetailController($scope, $rootScope, $stateParams, previousState, entity, Operador, User, Oficina, ContactoOperador, Tramite) {
        var vm = this;

        vm.operador = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('obrasPrivadas4App:operadorUpdate', function(event, result) {
            vm.operador = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
