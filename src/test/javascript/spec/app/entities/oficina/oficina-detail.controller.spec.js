'use strict';

describe('Controller Tests', function() {

    describe('Oficina Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockOficina, MockTipoPlano, MockOperador;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockOficina = jasmine.createSpy('MockOficina');
            MockTipoPlano = jasmine.createSpy('MockTipoPlano');
            MockOperador = jasmine.createSpy('MockOperador');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Oficina': MockOficina,
                'TipoPlano': MockTipoPlano,
                'Operador': MockOperador
            };
            createController = function() {
                $injector.get('$controller')("OficinaDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'obrasPrivadas4App:oficinaUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
