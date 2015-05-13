'use strict';

describe('Service: geolocatorFactory', function () {

  // load the service's module
  beforeEach(module('frontendApp'));

  // instantiate service
  var geolocatorFactory;
  beforeEach(inject(function (_geolocatorFactory_) {
    geolocatorFactory = _geolocatorFactory_;
  }));

  it('should do something', function () {
    expect(!!geolocatorFactory).toBe(true);
  });

});
