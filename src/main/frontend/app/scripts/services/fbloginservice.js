'use strict';

/**
 * @ngdoc service
 * @name frontendApp.fbLoginService
 * @description
 * # fbLoginService
 * Factory in the frontendApp.
 */
angular.module('frontendApp')
  .factory('fbLoginService', function ($q, facebookUser, $rootScope, $http) {

    var deferer = $q.defer();
    var loginPromise = deferer.promise;
    
    function _setUserPromiseResponse(response){
        facebookUser.then(function(user) {
        
        user.api('/me').then(function(user) {
          $rootScope.loggedInUser = user;

          $rootScope.fbStatus = response;
          $http.defaults.headers.common['token'] = $rootScope.fbStatus.authResponse.accessToken;
          $.ajax({
            type: 'POST',
            url: 'api/v1/users/' + response.authResponse.accessToken,
            contentType: 'application/json',
            data: {id: response.id}
          }).success(function(){
            deferer.resolve();
          });
        });
      });
      }
      
    return {
      login: loginPromise,
      setResponse: _setUserPromiseResponse
    };
  });
