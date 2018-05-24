(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('ContactoProfesionalDialogController', ContactoProfesionalDialogController);

    ContactoProfesionalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ContactoProfesional', 'TipoContacto', 'Profesional'];

    function ContactoProfesionalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ContactoProfesional, TipoContacto, Profesional) {
        var vm = this;

        vm.contactoProfesional = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tipocontactos = TipoContacto.query();
        vm.profesionals = Profesional.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.contactoProfesional.id !== null) {
                ContactoProfesional.update(vm.contactoProfesional, onSaveSuccess, onSaveError);
            } else {
                ContactoProfesional.save(vm.contactoProfesional, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('obrasPrivadas4App:contactoProfesionalUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
