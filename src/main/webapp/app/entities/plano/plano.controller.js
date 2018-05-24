(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('PlanoController', PlanoController);

    PlanoController.$inject = ['Plano'];

    function PlanoController(Plano) {

        var vm = this;

        vm.planos = [];

        loadAll();

        function loadAll() {
            Plano.query(function(result) {
                vm.planos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
