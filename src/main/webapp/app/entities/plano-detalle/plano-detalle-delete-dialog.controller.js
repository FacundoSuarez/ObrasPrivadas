(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('PlanoDetalleDeleteController',PlanoDetalleDeleteController);

    PlanoDetalleDeleteController.$inject = ['$uibModalInstance', 'entity', 'PlanoDetalle'];

    function PlanoDetalleDeleteController($uibModalInstance, entity, PlanoDetalle) {
        var vm = this;

        vm.planoDetalle = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PlanoDetalle.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
