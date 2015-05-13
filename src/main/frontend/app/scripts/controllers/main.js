'use strict';

/**
 * @ngdoc function
 * @name frontendApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the frontendApp
 */
angular.module('frontendApp')
  .controller('MainCtrl', function ($scope, geolocatorFactory) {
    
    navigator.geolocation.getCurrentPosition(function(pos){
      $scope.$apply(function(){
        $scope.userAllowed = true;
      });
    });
  });
