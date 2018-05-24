(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('OficinaDeleteController',OficinaDeleteController);

    OficinaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Oficina'];

    function OficinaDeleteController($uibModalInstance, entity, Oficina) {
        var vm = this;

        vm.oficina = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Oficina.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
