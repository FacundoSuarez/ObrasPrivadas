(function() {
    'use strict';
    angular
        .module('obrasPrivadas4App')
        .factory('ContactoOperador', ContactoOperador);

    ContactoOperador.$inject = ['$resource'];

    function ContactoOperador ($resource) {
        var resourceUrl =  'api/contacto-operadors/:id';

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
