(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('ProfesionalDetailController', ProfesionalDetailController);

    ProfesionalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Profesional', 'User', 'Plano', 'ContactoProfesional'];

    function ProfesionalDetailController($scope, $rootScope, $stateParams, previousState, entity, Profesional, User, Plano, ContactoProfesional) {
        var vm = this;

        vm.profesional = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('obrasPrivadas4App:profesionalUpdate', function(event, result) {
            vm.profesional = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
