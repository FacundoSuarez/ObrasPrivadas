(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('TramiteDetailController', TramiteDetailController);

    TramiteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tramite', 'Archivo', 'PlanoDetalle', 'Operador'];

    function TramiteDetailController($scope, $rootScope, $stateParams, previousState, entity, Tramite, Archivo, PlanoDetalle, Operador) {
        var vm = this;

        vm.tramite = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('obrasPrivadas4App:tramiteUpdate', function(event, result) {
            vm.tramite = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
