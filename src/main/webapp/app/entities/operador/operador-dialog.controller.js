(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('OperadorDialogController', OperadorDialogController);

    OperadorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Operador', 'User', 'Oficina', 'ContactoOperador', 'Tramite'];

    function OperadorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Operador, User, Oficina, ContactoOperador, Tramite) {
        var vm = this;

        vm.operador = entity;
        vm.clear = clear;
        vm.save = save;
        vm.users = User.query();
        vm.oficinas = Oficina.query({filter: 'operador-is-null'});
        $q.all([vm.operador.$promise, vm.oficinas.$promise]).then(function() {
            if (!vm.operador.oficina || !vm.operador.oficina.id) {
                return $q.reject();
            }
            return Oficina.get({id : vm.operador.oficina.id}).$promise;
        }).then(function(oficina) {
            vm.oficinas.push(oficina);
        });
        vm.contactooperadors = ContactoOperador.query();
        vm.tramites = Tramite.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.operador.id !== null) {
                Operador.update(vm.operador, onSaveSuccess, onSaveError);
            } else {
                Operador.save(vm.operador, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('obrasPrivadas4App:operadorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
