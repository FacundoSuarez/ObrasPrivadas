(function() {
    'use strict';
    angular
        .module('obrasPrivadas4App')
        .factory('Plano', Plano);

    Plano.$inject = ['$resource', 'DateUtils'];

    function Plano ($resource, DateUtils) {
        var resourceUrl =  'api/planos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fecha = DateUtils.convertDateTimeFromServer(data.fecha);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
