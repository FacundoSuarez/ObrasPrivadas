'use strict';

describe('Controller Tests', function() {

    describe('Plano Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPlano, MockPlanoDetalle, MockProfesional;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPlano = jasmine.createSpy('MockPlano');
            MockPlanoDetalle = jasmine.createSpy('MockPlanoDetalle');
            MockProfesional = jasmine.createSpy('MockProfesional');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Plano': MockPlano,
                'PlanoDetalle': MockPlanoDetalle,
                'Profesional': MockProfesional
            };
            createController = function() {
                $injector.get('$controller')("PlanoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'obrasPrivadas4App:planoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
