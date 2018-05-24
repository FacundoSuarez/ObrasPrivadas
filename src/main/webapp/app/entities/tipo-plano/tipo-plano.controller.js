(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('TipoPlanoController', TipoPlanoController);

    TipoPlanoController.$inject = ['TipoPlano'];

    function TipoPlanoController(TipoPlano) {

        var vm = this;

        vm.tipoPlanos = [];

        loadAll();

        function loadAll() {
            TipoPlano.query(function(result) {
                vm.tipoPlanos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
