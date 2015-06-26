'use strict';

/**
 * @ngdoc function
 * @name frontendApp.controller:FriendsCtrl
 * @description
 * # FriendsCtrl
 * Controller of the frontendApp
 */
angular.module('frontendApp')
  .controller('FriendsCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
