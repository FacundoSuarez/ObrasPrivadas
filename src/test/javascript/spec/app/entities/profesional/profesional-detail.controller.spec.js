'use strict';

describe('Controller Tests', function() {

    describe('Profesional Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProfesional, MockUser, MockPlano, MockContactoProfesional;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProfesional = jasmine.createSpy('MockProfesional');
            MockUser = jasmine.createSpy('MockUser');
            MockPlano = jasmine.createSpy('MockPlano');
            MockContactoProfesional = jasmine.createSpy('MockContactoProfesional');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Profesional': MockProfesional,
                'User': MockUser,
                'Plano': MockPlano,
                'ContactoProfesional': MockContactoProfesional
            };
            createController = function() {
                $injector.get('$controller')("ProfesionalDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'obrasPrivadas4App:profesionalUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
