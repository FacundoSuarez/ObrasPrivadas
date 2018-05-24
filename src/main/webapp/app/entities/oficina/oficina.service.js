(function() {
    'use strict';
    angular
        .module('obrasPrivadas4App')
        .factory('Oficina', Oficina);

    Oficina.$inject = ['$resource'];

    function Oficina ($resource) {
        var resourceUrl =  'api/oficinas/:id';

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
