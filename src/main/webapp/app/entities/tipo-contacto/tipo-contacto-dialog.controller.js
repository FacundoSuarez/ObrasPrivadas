(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('TipoContactoDialogController', TipoContactoDialogController);

    TipoContactoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TipoContacto', 'ContactoOperador', 'ContactoProfesional'];

    function TipoContactoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TipoContacto, ContactoOperador, ContactoProfesional) {
        var vm = this;

        vm.tipoContacto = entity;
        vm.clear = clear;
        vm.save = save;
        vm.contactooperadors = ContactoOperador.query();
        vm.contactoprofesionals = ContactoProfesional.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tipoContacto.id !== null) {
                TipoContacto.update(vm.tipoContacto, onSaveSuccess, onSaveError);
            } else {
                TipoContacto.save(vm.tipoContacto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('obrasPrivadas4App:tipoContactoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
