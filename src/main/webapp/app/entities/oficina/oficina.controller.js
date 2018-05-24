(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('OficinaController', OficinaController);

    OficinaController.$inject = ['Oficina'];

    function OficinaController(Oficina) {

        var vm = this;

        vm.oficinas = [];

        loadAll();

        function loadAll() {
            Oficina.query(function(result) {
                vm.oficinas = result;
                vm.searchQuery = null;
            });
        }
    }
})();
