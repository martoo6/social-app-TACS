'use strict';

/**
 * @ngdoc function
 * @name frontendApp.controller:MisvuelosCtrl
 * @description
 * # MisvuelosCtrl
 * Controller of the frontendApp
 */
angular.module('frontendApp')
  .controller('MisvuelosCtrl', function ($scope, $http, $q) {

    $scope.wayStopPoints = []; 
    $scope.returnStopPoints = []; 
    $scope.myTrips = [];
    
    function drawMap(){
      var points = $scope.wayStopPoints
                      .sort(function(a, b){ return a.ord > b.ord })
                      .map(function(el){ return el.gPoint }); 
      var mapa = new google.maps.Map($('#mapa')[0]);

      var trazaVuelo = new google.maps.Polyline({ path: points,
                                                  geodesic: true,
                                                  strokeColor: '#FF0000',
                                                  strokeOpacity: 1.0,
                                                  strokeWeight: 2,
                                                  clickable: false
                                              });
      trazaVuelo.setMap(mapa);

      //AJUSTA EL ZOOM PARA QUE SE VEA EL RECORRIDO
      var bounds = new google.maps.LatLngBounds();
      trazaVuelo.getPath().forEach(function(bound) {bounds.extend(bound);});
      mapa.fitBounds(bounds);
    }

    $scope.showMap = function(trip){

      //stopPoints refer to when a plane stops at an airport 
      //but its not the final destination
      $scope.returnStopPoints = $scope.wayStopPoints = [];
      $('#modalMap').modal();
      
      var wayStopPromises = flightsStopsPromises(trip.wayFlights);
      //var returnStopPromises = flightsStopsPromises(trip.wayFlights);
      
      $q.all(wayStopPromises).then(drawMap);
    };
    
    
    //////////////////////////////////////////
    ///////////////// HELPERS ////////////////
    //////////////////////////////////////////

    
    function flightsStopsPromises(flights){
      
      var ord = 0;
      
      return flights.reduce(function(stops, flight){
        return stops.concat([
          makeAirportStopPromise(flight.origin, ord++),
          makeAirportStopPromise(flight.destiny, ord++)
        ]);
      }, []);
    }
    
    function makeAirportStopPromise(airportCode, ordNum){
      
      return $http.get('api/v1/airports/' + airportCode)
              .success(function recordAirportStop(airport){
                $scope.wayStopPoints.push({ //change here needed to implement the return polyline
                  ord: ordNum, 
                  gPoint: new google.maps.LatLng(airport.lat, airport.lon)
                });
              });
    }
    

    //////////////////////////////////////////
    ////////////// INIT FUNCTION /////////////
    //////////////////////////////////////////
    
    
    (function init(){
      
      $http.get('api/v1/trips/all')
        .success(function(trips){
          $scope.myTrips = trips;
        });
    }());
  });
