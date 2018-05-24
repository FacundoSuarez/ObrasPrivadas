(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('ContactoOperadorDialogController', ContactoOperadorDialogController);

    ContactoOperadorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ContactoOperador', 'TipoContacto', 'Operador'];

    function ContactoOperadorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ContactoOperador, TipoContacto, Operador) {
        var vm = this;

        vm.contactoOperador = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tipocontactos = TipoContacto.query();
        vm.operadors = Operador.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.contactoOperador.id !== null) {
                ContactoOperador.update(vm.contactoOperador, onSaveSuccess, onSaveError);
            } else {
                ContactoOperador.save(vm.contactoOperador, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('obrasPrivadas4App:contactoOperadorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
