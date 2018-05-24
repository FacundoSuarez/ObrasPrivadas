(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('PlanoDetalleDialogController', PlanoDetalleDialogController);

    PlanoDetalleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PlanoDetalle', 'Tramite', 'Plano', 'TipoPlano'];

    function PlanoDetalleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PlanoDetalle, Tramite, Plano, TipoPlano) {
        var vm = this;

        vm.planoDetalle = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tramites = Tramite.query();
        vm.planos = Plano.query();
        vm.tipoplanos = TipoPlano.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.planoDetalle.id !== null) {
                PlanoDetalle.update(vm.planoDetalle, onSaveSuccess, onSaveError);
            } else {
                PlanoDetalle.save(vm.planoDetalle, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('obrasPrivadas4App:planoDetalleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
