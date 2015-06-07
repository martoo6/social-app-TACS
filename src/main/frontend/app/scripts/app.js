'use strict';

/**
 * @ngdoc overview
 * @name frontendApp
 * @description
 * # frontendApp
 *
 * Main module of the application.
 */

(function() {
  var app = angular .module('frontendApp', [
      'ngAnimate',
      'ngCookies',
      'ngResource',
      'ngRoute',
      'ngSanitize',
      'ngTouch'
    ])

    app.config(function ($routeProvider) {
      $routeProvider
        .when('/', {
          templateUrl: 'views/main.html',
          controller: 'MainCtrl'
        })
        .when('/destinyMapPick', {
          templateUrl: 'views/destinymappick.html',
          controller: 'DestinymappickCtrl'
        })
        .when('/destinySelection', {
          templateUrl: 'views/destinySelection.html',
          controller: 'DestinySelectionCtrl'
        })
        .when('/flightPick', {
          templateUrl: 'views/flightpick.html',
          controller: 'FlightpickCtrl'
        })
        .when('/misVuelos', {
          templateUrl: 'views/misvuelos.html',
          controller: 'MisvuelosCtrl'
        })
        .otherwise({
          redirectTo: '/'
        });
    });

    app.directive('dateSelection',function(){
      return{
        restrict: 'E',
        templateUrl: 'views/dateSelection.html'
      }
    });

  app.directive('locationSelection',function(){
    return{
      restrict: 'E',
      templateUrl: 'views/locationSelection.html'
    }
  });

})();
