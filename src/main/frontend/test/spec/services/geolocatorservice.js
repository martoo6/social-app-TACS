'use strict';

describe('Service: geolocatorService', function () {

  // load the service's module
  beforeEach(module('frontendApp'));

  // instantiate service
  var geolocatorService;
  beforeEach(inject(function (_geolocatorService_) {
    geolocatorService = _geolocatorService_;
  }));

  it('should do something', function () {
    expect(!!geolocatorService).toBe(true);
  });

});
