(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('ProfesionalController', ProfesionalController);

    ProfesionalController.$inject = ['Profesional'];

    function ProfesionalController(Profesional) {

        var vm = this;

        vm.profesionals = [];

        loadAll();

        function loadAll() {
            Profesional.query(function(result) {
                vm.profesionals = result;
                vm.searchQuery = null;
            });
        }
    }
})();
