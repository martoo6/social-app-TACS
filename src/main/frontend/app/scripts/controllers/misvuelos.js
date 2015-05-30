'use strict';

/**
 * @ngdoc function
 * @name frontendApp.controller:MisvuelosCtrl
 * @description
 * # MisvuelosCtrl
 * Controller of the frontendApp
 */
angular.module('frontendApp')
  .controller('MisvuelosCtrl', function ($scope, $http) {

    function dibujarMapa(){
      if($('#modalMap').hasClass('in') && $scope.originLtLg && $scope.destinyLtLg){
        var mapa = new google.maps.Map($('#mapa')[0]);

        var trazaVuelo = new google.maps.Polyline({ path: [$scope.originLtLg, $scope.destinyLtLg],
                                                    geodesic: true,
                                                    strokeColor: '#FF0000',
                                                    strokeOpacity: 1.0,
                                                    strokeWeight: 2,
                                                    clickable: false
                                                });
        trazaVuelo.setMap(mapa);

        //AJUSTA EL ZOOM PARA QUE SE VEA EL RECORRIDO
        var bounds = new google.maps.LatLngBounds();
        trazaVuelo.getPath().forEach(function(bound) {bounds.extend(bound);})
        mapa.fitBounds(bounds);
      }
    }

    $scope.showMap = function(flight){
      $('#modalMap').modal();

      $.get('api/v1/airports/' + flight.wayFlights[0].origin, function(originAirport){
        $scope.originLtLg = new google.maps.LatLng(originAirport.lat, originAirport.lon);
        dibujarMapa();
      });

      $.get('api/v1/airports/' + flight.wayFlights[0].destiny, function(destinyAirport){
        $scope.destinyLtLg = new google.maps.LatLng(destinyAirport.lat, destinyAirport.lon);
        dibujarMapa()
      });
    };

    $http
      .get('api/v1/trips/all')
      .success(function(flights){
        $scope.myFlights = flights;
      });
  });
