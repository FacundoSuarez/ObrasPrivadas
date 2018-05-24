(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('ContactoProfesionalDeleteController',ContactoProfesionalDeleteController);

    ContactoProfesionalDeleteController.$inject = ['$uibModalInstance', 'entity', 'ContactoProfesional'];

    function ContactoProfesionalDeleteController($uibModalInstance, entity, ContactoProfesional) {
        var vm = this;

        vm.contactoProfesional = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ContactoProfesional.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
