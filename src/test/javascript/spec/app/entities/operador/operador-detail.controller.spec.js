'use strict';

describe('Controller Tests', function() {

    describe('Operador Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockOperador, MockUser, MockOficina, MockContactoOperador, MockTramite;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockOperador = jasmine.createSpy('MockOperador');
            MockUser = jasmine.createSpy('MockUser');
            MockOficina = jasmine.createSpy('MockOficina');
            MockContactoOperador = jasmine.createSpy('MockContactoOperador');
            MockTramite = jasmine.createSpy('MockTramite');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Operador': MockOperador,
                'User': MockUser,
                'Oficina': MockOficina,
                'ContactoOperador': MockContactoOperador,
                'Tramite': MockTramite
            };
            createController = function() {
                $injector.get('$controller')("OperadorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'obrasPrivadas4App:operadorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
