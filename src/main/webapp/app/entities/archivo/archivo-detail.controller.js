(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('ArchivoDetailController', ArchivoDetailController);

    ArchivoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Archivo', 'Tramite'];

    function ArchivoDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Archivo, Tramite) {
        var vm = this;

        vm.archivo = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        
        var unsubscribe = $rootScope.$on('obrasPrivadas4App:archivoUpdate', function(event, result) {
            vm.archivo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
