(function() {
    'use strict';
    angular
        .module('obrasPrivadas4App')
        .factory('Operador', Operador);

    Operador.$inject = ['$resource'];

    function Operador ($resource) {
        var resourceUrl =  'api/operadors/:id';

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
