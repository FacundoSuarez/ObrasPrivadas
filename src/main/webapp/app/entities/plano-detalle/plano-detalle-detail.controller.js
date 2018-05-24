(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('PlanoDetalleDetailController', PlanoDetalleDetailController);

    PlanoDetalleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PlanoDetalle', 'Tramite', 'Plano', 'TipoPlano'];

    function PlanoDetalleDetailController($scope, $rootScope, $stateParams, previousState, entity, PlanoDetalle, Tramite, Plano, TipoPlano) {
        var vm = this;

        vm.planoDetalle = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('obrasPrivadas4App:planoDetalleUpdate', function(event, result) {
            vm.planoDetalle = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
