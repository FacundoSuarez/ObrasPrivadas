(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('ProfesionalDeleteController',ProfesionalDeleteController);

    ProfesionalDeleteController.$inject = ['$uibModalInstance', 'entity', 'Profesional'];

    function ProfesionalDeleteController($uibModalInstance, entity, Profesional) {
        var vm = this;

        vm.profesional = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Profesional.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
