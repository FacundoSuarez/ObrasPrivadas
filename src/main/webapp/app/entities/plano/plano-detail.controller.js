(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('PlanoDetailController', PlanoDetailController);

    PlanoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Plano', 'PlanoDetalle', 'Profesional'];

    function PlanoDetailController($scope, $rootScope, $stateParams, previousState, entity , Plano, PlanoDetalle, Profesional) {
        var vm = this;

        vm.plano = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('obrasPrivadas4App:planoUpdate', function(event, result) {
            vm.plano = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
