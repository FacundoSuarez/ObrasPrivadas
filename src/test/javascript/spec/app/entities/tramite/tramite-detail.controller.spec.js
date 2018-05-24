'use strict';

describe('Controller Tests', function() {

    describe('Tramite Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTramite, MockArchivo, MockPlanoDetalle, MockOperador;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTramite = jasmine.createSpy('MockTramite');
            MockArchivo = jasmine.createSpy('MockArchivo');
            MockPlanoDetalle = jasmine.createSpy('MockPlanoDetalle');
            MockOperador = jasmine.createSpy('MockOperador');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Tramite': MockTramite,
                'Archivo': MockArchivo,
                'PlanoDetalle': MockPlanoDetalle,
                'Operador': MockOperador
            };
            createController = function() {
                $injector.get('$controller')("TramiteDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'obrasPrivadas4App:tramiteUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
