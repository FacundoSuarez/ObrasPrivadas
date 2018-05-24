(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('TramiteController', TramiteController);

    TramiteController.$inject = ['Tramite', 'DataUtils'];
    
    function TramiteController(tramite, DataUtils) {

        var vm = this;

        vm.tramites = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();
        
        function loadAll() {
            tramite.query(function(result) {
                vm.tramites = result;
                vm.searchQuery = null;
            });
        }
    }
})();
