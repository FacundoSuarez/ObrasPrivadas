(function() {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .controller('ContactoProfesionalController', ContactoProfesionalController);

    ContactoProfesionalController.$inject = ['ContactoProfesional'];

    function ContactoProfesionalController(ContactoProfesional) {

        var vm = this;

        vm.contactoProfesionals = [];

        loadAll();

        function loadAll() {
            ContactoProfesional.query(function(result) {
                vm.contactoProfesionals = result;
                vm.searchQuery = null;
            });
        }
    }
})();
