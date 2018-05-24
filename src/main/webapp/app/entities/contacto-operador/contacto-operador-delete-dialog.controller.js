(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('ContactoOperadorDeleteController',ContactoOperadorDeleteController);

    ContactoOperadorDeleteController.$inject = ['$uibModalInstance', 'entity', 'ContactoOperador'];

    function ContactoOperadorDeleteController($uibModalInstance, entity, ContactoOperador) {
        var vm = this;

        vm.contactoOperador = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ContactoOperador.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
