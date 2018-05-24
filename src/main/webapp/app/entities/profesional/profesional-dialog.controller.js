(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('ProfesionalDialogController', ProfesionalDialogController);

    ProfesionalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Profesional', 'User', 'Plano', 'ContactoProfesional', 'TipoContacto'];

    function ProfesionalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Profesional, User, Plano, ContactoProfesional, TipoContacto) {
        var vm = this;

        vm.profesional = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.planos = Plano.query();
        vm.contactoprofesionals = ContactoProfesional.query();
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
            if (vm.profesional.id !== null) {
                Profesional.update(vm.profesional, onSaveSuccess, onSaveError);
            } else {
                Profesional.save(vm.profesional, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('obrasPrivadas4App:profesionalUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
