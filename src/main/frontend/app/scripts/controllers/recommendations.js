'use strict';

/**
 * @ngdoc function
 * @name frontendApp.controller:RecommendationsCtrl
 * @description
 * # RecommendationsCtrl
 * Controller of the frontendApp
 */
angular.module('frontendApp')
  .controller('RecommendationsCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
