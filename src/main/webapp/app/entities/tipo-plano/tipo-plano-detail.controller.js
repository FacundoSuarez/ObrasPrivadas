(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('TipoPlanoDetailController', TipoPlanoDetailController);

    TipoPlanoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TipoPlano', 'PlanoDetalle', 'Oficina'];

    function TipoPlanoDetailController($scope, $rootScope, $stateParams, previousState, entity, TipoPlano, PlanoDetalle, Oficina) {
        var vm = this;

        vm.tipoPlano = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('obrasPrivadas4App:tipoPlanoUpdate', function(event, result) {
            vm.tipoPlano = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
