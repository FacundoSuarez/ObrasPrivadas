(function() {
    'use strict';
    angular
        .module('obrasPrivadas4App')
        .factory('TipoContacto', TipoContacto);

    TipoContacto.$inject = ['$resource'];

    function TipoContacto ($resource) {
        var resourceUrl =  'api/tipo-contactos/:id';

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
