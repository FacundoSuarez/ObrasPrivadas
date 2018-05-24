'use strict';

describe('Controller Tests', function() {

    describe('ContactoOperador Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockContactoOperador, MockTipoContacto, MockOperador;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockContactoOperador = jasmine.createSpy('MockContactoOperador');
            MockTipoContacto = jasmine.createSpy('MockTipoContacto');
            MockOperador = jasmine.createSpy('MockOperador');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ContactoOperador': MockContactoOperador,
                'TipoContacto': MockTipoContacto,
                'Operador': MockOperador
            };
            createController = function() {
                $injector.get('$controller')("ContactoOperadorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'obrasPrivadas4App:contactoOperadorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
