(function () {
    'use strict';

    angular
        .module('obrasPrivadas4App')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
