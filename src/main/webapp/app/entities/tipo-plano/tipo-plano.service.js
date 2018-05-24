(function() {
    'use strict';
    angular
        .module('obrasPrivadas4App')
        .factory('TipoPlano', TipoPlano);

    TipoPlano.$inject = ['$resource'];

    function TipoPlano ($resource) {
        var resourceUrl =  'api/tipo-planos/:id';

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
