'use strict';

describe('Controller Tests', function() {

    describe('College Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCollege, MockAddress, MockContact, MockProgram;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCollege = jasmine.createSpy('MockCollege');
            MockAddress = jasmine.createSpy('MockAddress');
            MockContact = jasmine.createSpy('MockContact');
            MockProgram = jasmine.createSpy('MockProgram');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'College': MockCollege,
                'Address': MockAddress,
                'Contact': MockContact,
                'Program': MockProgram
            };
            createController = function() {
                $injector.get('$controller')("CollegeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'smartWebApp:collegeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
