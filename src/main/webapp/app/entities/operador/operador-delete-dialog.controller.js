(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('OperadorDeleteController',OperadorDeleteController);

    OperadorDeleteController.$inject = ['$uibModalInstance', 'entity', 'Operador'];

    function OperadorDeleteController($uibModalInstance, entity, Operador) {
        var vm = this;

        vm.operador = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Operador.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
