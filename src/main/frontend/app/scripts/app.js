'use strict';

/**
 * @ngdoc overview
 * @name frontendApp
 * @description
 * # frontendApp
 *
 * Main module of the application.
 */
angular
  .module('frontendApp', [
    'ngAnimate',
    'ngCookies',
    'ngResource',
    'ngRoute',
    'ngSanitize',
    'ngTouch',
    'facebookUtils',
    "angucomplete-alt"
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/destinyMapPick', {
        templateUrl: 'views/destinymappick.html',
        controller: 'DestinymappickCtrl',
        needAuth: true
      })
      .when('/flightPick', {
        templateUrl: 'views/flightpick.html',
        controller: 'FlightpickCtrl',
        needAuth: true
      })
      .when('/misVuelos', {
        templateUrl: 'views/misvuelos.html',
        controller: 'MisvuelosCtrl',
        needAuth: true
      })
      .otherwise({
        redirectTo: '/'
      });
  })
  .directive('locationSelection',function(){
    return{
      scope: {
        selection: '=',
        id: '@id'
      },
      restrict: 'AE',
      templateUrl: 'views/locationSelection.html'
    };
  })
  .directive('dateSelection',function(){
    return{
      scope: {
        selection: '=',
        id: '@id'
      },
      restrict: 'AE',
      templateUrl: 'views/dateSelection.html'
    };
  })
  .constant('facebookConfigSettings', {
    'appID' : '565625596912348',
    'routingEnabled' : true,
    'permissions' : 'publish_actions'
  })
  .run(function ($rootScope, facebookUser, $http){
    $rootScope.$on('fbLoginSuccess', function(name, response) {
      facebookUser.then(function(user) {
        user.api('/me').then(function(response) {
          console.log(response);
          window.fbResponse = response;
          $rootScope.loggedInUser = response;
        });
      });
      window.fbUser = response;
      window.fbStatus = response;
      $rootScope.fbStatus = response;
      console.log(response);
      $http.defaults.headers.common['token'] = $rootScope.fbStatus.authResponse.accessToken;
      $.ajax({
            type: 'POST',
            url: 'api/v1/users/' + response.authResponse.accessToken,
            contentType: 'application/json',
            data: {id: response.id}
          });
    });

    $rootScope.$on('fbLogoutSuccess', function() {
      $rootScope.$apply(function() {
        $rootScope.loggedInUser = {};
        $rootScope.fbStatus = {};
        $http.defaults.headers.common['token'] = '';
      });
    });
  });
