(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('ContactoProfesionalDetailController', ContactoProfesionalDetailController);

    ContactoProfesionalDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ContactoProfesional', 'TipoContacto', 'Profesional'];

    function ContactoProfesionalDetailController($scope, $rootScope, $stateParams, previousState, entity, ContactoProfesional, TipoContacto, Profesional) {
        var vm = this;

        vm.contactoProfesional = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('obrasPrivadas4App:contactoProfesionalUpdate', function(event, result) {
            vm.contactoProfesional = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
