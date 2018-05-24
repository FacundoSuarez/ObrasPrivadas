(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('OficinaDialogController', OficinaDialogController);

    OficinaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Oficina', 'TipoPlano', 'Operador'];

    function OficinaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Oficina, TipoPlano, Operador) {
        var vm = this;

        vm.oficina = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tipoplanos = TipoPlano.query({filter: 'oficina-is-null'});
        $q.all([vm.oficina.$promise, vm.tipoplanos.$promise]).then(function() {
            if (!vm.oficina.tipoPlano || !vm.oficina.tipoPlano.id) {
                return $q.reject();
            }
            return TipoPlano.get({id : vm.oficina.tipoPlano.id}).$promise;
        }).then(function(tipoPlano) {
            vm.tipoplanos.push(tipoPlano);
        });
        vm.operadors = Operador.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
                
        function save () {
            vm.isSaving = true;
            if (vm.oficina.id !== null) {
                Oficina.update(vm.oficina, onSaveSuccess, onSaveError);
            } else {
                Oficina.save(vm.oficina, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('obrasPrivadas4App:oficinaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
