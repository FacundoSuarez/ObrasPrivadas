(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('TipoContactoDeleteController',TipoContactoDeleteController);

    TipoContactoDeleteController.$inject = ['$uibModalInstance', 'entity', 'TipoContacto'];

    function TipoContactoDeleteController($uibModalInstance, entity, TipoContacto) {
        var vm = this;

        vm.tipoContacto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TipoContacto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
