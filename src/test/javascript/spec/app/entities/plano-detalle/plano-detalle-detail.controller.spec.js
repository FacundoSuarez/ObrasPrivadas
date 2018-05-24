'use strict';

describe('Controller Tests', function() {

    describe('PlanoDetalle Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPlanoDetalle, MockTramite, MockPlano, MockTipoPlano;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPlanoDetalle = jasmine.createSpy('MockPlanoDetalle');
            MockTramite = jasmine.createSpy('MockTramite');
            MockPlano = jasmine.createSpy('MockPlano');
            MockTipoPlano = jasmine.createSpy('MockTipoPlano');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PlanoDetalle': MockPlanoDetalle,
                'Tramite': MockTramite,
                'Plano': MockPlano,
                'TipoPlano': MockTipoPlano
            };
            createController = function() {
                $injector.get('$controller')("PlanoDetalleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'obrasPrivadas4App:planoDetalleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
