'use strict';

describe('Controller Tests', function() {

    describe('TipoContacto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTipoContacto, MockContactoOperador, MockContactoProfesional;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTipoContacto = jasmine.createSpy('MockTipoContacto');
            MockContactoOperador = jasmine.createSpy('MockContactoOperador');
            MockContactoProfesional = jasmine.createSpy('MockContactoProfesional');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TipoContacto': MockTipoContacto,
                'ContactoOperador': MockContactoOperador,
                'ContactoProfesional': MockContactoProfesional
            };
            createController = function() {
                $injector.get('$controller')("TipoContactoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'obrasPrivadas4App:tipoContactoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
