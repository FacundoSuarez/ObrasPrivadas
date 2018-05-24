(function() {
    'use strict';
    angular
        .module('obrasPrivadas4App')
        .factory('Archivo', Archivo);

    Archivo.$inject = ['$resource'];

    function Archivo ($resource) {
        var resourceUrl =  'api/archivos/:id';

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
