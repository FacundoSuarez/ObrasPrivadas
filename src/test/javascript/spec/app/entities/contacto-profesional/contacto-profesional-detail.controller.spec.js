'use strict';

describe('Controller Tests', function() {

    describe('ContactoProfesional Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockContactoProfesional, MockTipoContacto, MockProfesional;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockContactoProfesional = jasmine.createSpy('MockContactoProfesional');
            MockTipoContacto = jasmine.createSpy('MockTipoContacto');
            MockProfesional = jasmine.createSpy('MockProfesional');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ContactoProfesional': MockContactoProfesional,
                'TipoContacto': MockTipoContacto,
                'Profesional': MockProfesional
            };
            createController = function() {
                $injector.get('$controller')("ContactoProfesionalDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'obrasPrivadas4App:contactoProfesionalUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
