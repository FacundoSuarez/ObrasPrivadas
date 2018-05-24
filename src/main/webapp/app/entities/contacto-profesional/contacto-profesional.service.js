(function() {
    'use strict';
    angular
        .module('obrasPrivadas4App')
        .factory('ContactoProfesional', ContactoProfesional);

    ContactoProfesional.$inject = ['$resource'];

    function ContactoProfesional ($resource) {
        var resourceUrl =  'api/contacto-profesionals/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
