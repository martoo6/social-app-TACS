'use strict';

describe('Controller: MisvuelosCtrl', function () {

  // load the controller's module
  beforeEach(module('frontendApp'));

  var MisvuelosCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    MisvuelosCtrl = $controller('MisvuelosCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
