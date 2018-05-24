(function() {
    'use strict';
    angular
        .module('obrasPrivadas4App')
        .factory('Tramite', Tramite);

    Tramite.$inject = ['$resource', 'DateUtils'];

    function Tramite ($resource, DateUtils) {
        var resourceUrl =  'api/tramites/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.fecha = DateUtils.convertDateTimeFromServer(data.fecha);
                        data.fechaFin = DateUtils.convertDateTimeFromServer(data.fechaFin);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
