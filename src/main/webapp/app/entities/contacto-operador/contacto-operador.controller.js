(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('ContactoOperadorController', ContactoOperadorController);

    ContactoOperadorController.$inject = ['ContactoOperador'];

    function ContactoOperadorController(ContactoOperador) {

        var vm = this;

        vm.contactoOperadors = [];

        loadAll();

        function loadAll() {
            ContactoOperador.query(function(result) {
                vm.contactoOperadors = result;
                vm.searchQuery = null;
            });
        }
    }
})();
