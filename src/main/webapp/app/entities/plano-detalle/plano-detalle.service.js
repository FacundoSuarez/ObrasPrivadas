(function() {
    'use strict';
    angular
        .module('obrasPrivadas4App')
        .factory('PlanoDetalle', PlanoDetalle);

    PlanoDetalle.$inject = ['$resource'];

    function PlanoDetalle ($resource) {
        var resourceUrl =  'api/plano-detalles/:id';

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
