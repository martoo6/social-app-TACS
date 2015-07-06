'use strict';

describe('Service: fbLoginService', function () {

  // load the service's module
  beforeEach(module('frontendApp'));

  // instantiate service
  var fbLoginService;
  beforeEach(inject(function (_fbLoginService_) {
    fbLoginService = _fbLoginService_;
  }));

  it('should do something', function () {
    expect(!!fbLoginService).toBe(true);
  });

});
