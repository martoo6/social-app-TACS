'use strict';

/**
 * @ngdoc function
 * @name
 * @description
 * # DestinySelectionCtrl
 * Controller of the frontendApp
 */
angular.module('frontendApp').controller('DestinySelectionCtrl', function ($scope, $http, geolocatorFactory) {

  var locations = new Bloodhound({
    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
    queryTokenizer: Bloodhound.tokenizers.whitespace,
    prefetch: '',
    remote: {
      url: 'autocomplete/airports/%QUERY',
      wildcard: '%QUERY'
    }
  });

  $scope('.typeahead').typeahead(null, {
    name: 'select-location',
    display: 'description',
    source: locations
  });


});
