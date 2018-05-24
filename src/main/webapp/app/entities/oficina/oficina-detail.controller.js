(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('OficinaDetailController', OficinaDetailController);

    OficinaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Oficina', 'TipoPlano', 'Operador'];

    function OficinaDetailController($scope, $rootScope, $stateParams, previousState, entity, Oficina, TipoPlano, Operador) {
        var vm = this;

        vm.oficina = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('obrasPrivadas4App:oficinaUpdate', function(event, result) {
            vm.oficina = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
