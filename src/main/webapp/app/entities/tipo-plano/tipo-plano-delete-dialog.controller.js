(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('TipoPlanoDeleteController',TipoPlanoDeleteController);

    TipoPlanoDeleteController.$inject = ['$uibModalInstance', 'entity', 'TipoPlano'];

    function TipoPlanoDeleteController($uibModalInstance, entity, TipoPlano) {
        var vm = this;

        vm.tipoPlano = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TipoPlano.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
