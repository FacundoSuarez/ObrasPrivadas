(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('TipoContactoController', TipoContactoController);

    TipoContactoController.$inject = ['TipoContacto'];

    function TipoContactoController(TipoContacto) {

        var vm = this;

        vm.tipoContactos = [];

        loadAll();

        function loadAll() {
            TipoContacto.query(function(result) {
                vm.tipoContactos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
