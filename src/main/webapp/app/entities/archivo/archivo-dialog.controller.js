(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('ArchivoDialogController', ArchivoDialogController);

    ArchivoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Archivo', 'Tramite', 'PlanoDetalle'];

    function ArchivoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Archivo, Tramite, PlanoDetalle) {
        var vm = this;

        vm.archivo = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.tramites = Tramite.query();
        vm.planoDetalles = PlanoDetalle.query();
        
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.archivo.id !== null) {
                Archivo.update(vm.archivo, onSaveSuccess, onSaveError);
            } else {
                Archivo.save(vm.archivo, onSaveSuccess, onSaveError);
            }
       }

        function onSaveSuccess (result) {
            $scope.$emit('obrasPrivadas4App:archivoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setArchivo = function ($file, archivo) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        archivo.archivo = base64Data;
                        archivo.archivoContentType = $file.type;
                    });
                });
            }
        };

    }
})();
