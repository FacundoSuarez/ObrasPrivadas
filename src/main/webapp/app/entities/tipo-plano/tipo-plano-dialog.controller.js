(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('TipoPlanoDialogController', TipoPlanoDialogController);

    TipoPlanoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TipoPlano', 'PlanoDetalle', 'Oficina'];

    function TipoPlanoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TipoPlano, PlanoDetalle, Oficina) {
        var vm = this;

        vm.tipoPlano = entity;
        vm.clear = clear;
        vm.save = save;
        vm.planodetalles = PlanoDetalle.query();
        vm.oficinas = Oficina.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tipoPlano.id !== null) {
                TipoPlano.update(vm.tipoPlano, onSaveSuccess, onSaveError);
            } else {
                TipoPlano.save(vm.tipoPlano, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('obrasPrivadas4App:tipoPlanoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
