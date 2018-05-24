(function () {
    'use strict';

    angular.module('obrasPrivadas4App').controller('PlanoDialogController',
            PlanoDialogController);

    PlanoDialogController.$inject = ['$timeout', '$scope', '$stateParams',
        '$uibModalInstance', 'entity', 'Plano', 'PlanoDetalle',
        'Profesional', 'DataUtils', 'Archivo', 'Tramite', 'entityDetalle', 'entityTramite', 'entityArchivo', 'TipoPlano', '$q', 'Operador', 'Principal'];

    function PlanoDialogController($timeout, $scope, $stateParams,
            $uibModalInstance, entity, Plano, PlanoDetalle, Profesional,
            DataUtils, Archivo, Tramite, entityDetalle, entityTramite, entityArchivo, TipoPlano, $q, Operador, Principal) {

        var vm = this;
        vm.plano = entity;
        inicializarDetalle();
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.save = save;
        vm.planodetalles = PlanoDetalle.query();
        vm.profesionals = Profesional.query();
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.tramites = Tramite.query();
        vm.isPlanoDetalleCollapsed = true;
        vm.plano.fecha = new Date();




        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });


        function clear() {
            $uibModalInstance.dismiss('cancel');
        }


        function save() {
            vm.isSaving = true;
            if (vm.plano.id !== null) {
                Plano.update(vm.plano, onSaveSuccess, onSaveError);
            } else {
                Plano.save(vm.plano, onSaveSuccess, onSaveError);
            }
        }


        function onSaveSuccess(result) {
            $scope.$emit('obrasPrivadas4App:planoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }


        function onSaveError() {
            vm.isSaving = false;
        }

        function validaNumericos(event) {
            if (event.charCode >= 48 && event.charCode <= 57) {
                return true;
            }
            return false;
        }

        ///////////////////////GUARDA ARCHIVOS//////////////////////
        vm.setArchivo = function ($file, archivo) {
            if ($file) {
                DataUtils.toBase64($file, function (base64Data) {
                    $scope.$apply(function () {
                        archivo.archivo = base64Data;
                        archivo.archivoContentType = $file.type;
                    });
                });
            }
        };


        ////////////////////////////////////////
        //////////MUESTRA TIPO PLANO////////////
        ////////////////////////////////////////
        vm.tipoplanos = TipoPlano.query({filter: 'oficina-is-null'});
        $q.all([vm.plano.$promise, vm.tipoplanos.$promise]).then(function () {
            if (!vm.plano.tipoPlano || !vm.plano.tipoPlano.id) {
                return $q.reject();
            }
            return TipoPlano.get({id: vm.plano.tipoPlano.id}).$promise;
        }).then(function (tipoPlano) {
            vm.tipoplanos.push(tipoPlano);
        });


        // /////////////////////////////////////////////////////////////////////////////////////////////////
        // /// Funciones de Planos
        // /////////////////////////////////////////////////////////////////
        //// inicializamos el objeto detalle
        function inicializarDetalle() {
            vm.planoDetalle = entityDetalle;
            vm.planoDetalle.tramite = entityTramite;
            vm.planoDetalle.tramite.archivo = entityArchivo;
            vm.plano.tipoPlano = entityDetalle;
        }
        ;


        // Hace visible la carga
        vm.togglePlanoDetalle = function () {
            vm.isPlanoDetalleCollapsed = !vm.isPlanoDetalleCollapsed;
            inicializarDetalle();
            $scope.editForm.$setPristine();
        };


        vm.addPlanoDetalle = function (planoDetalle) {
            if (typeof vm.plano.planoDetalles === 'undefined') {
                vm.plano.planoDetalles = [];
            }
            vm.plano.planoDetalles.push(angular.copy(planoDetalle));
            vm.isPlanoDetalleCollapsed = true;
            inicializarDetalle();
            $scope.editForm.$setPristine();
            clearCampos();
        };


        vm.removePlanoDetalle = function (index) {
            vm.plano.planoDetalles.splice(index, 1);
        };


        ////////////////////////////VACIA LOS CAMPOS DE LOS SEGUNDOS IMPUT//////////////////////// 
        function clearCampos() {
            vm.planoDetalle.tramite.observaciones = null;
//                    vm.planoDetalle.archivo.archivo =  ;
            vm.planoDetalle.tipoPlano = null;
        }
    }
    ;
})();
