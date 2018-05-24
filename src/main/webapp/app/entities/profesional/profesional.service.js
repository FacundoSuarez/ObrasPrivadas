(function() {
    'use strict';
    angular
        .module('obrasPrivadas4App')
        .factory('Profesional', Profesional);

    Profesional.$inject = ['$resource'];

    function Profesional ($resource) {
        var resourceUrl =  'api/profesionals/:id';

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
