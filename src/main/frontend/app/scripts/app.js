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
    'ngTouch'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl'
      })
      .when('/destinyMapPick', {
        templateUrl: 'views/destinymappick.html',
        controller: 'DestinymappickCtrl'
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
