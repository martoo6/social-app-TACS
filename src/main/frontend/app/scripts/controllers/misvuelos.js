'use strict';

/**
 * @ngdoc function
 * @name frontendApp.controller:MisvuelosCtrl
 * @description
 * # MisvuelosCtrl
 * Controller of the frontendApp
 */
angular.module('frontendApp')
  .controller('MisvuelosCtrl', function ($scope, fbLoginService, $http, $q) {

    fbLoginService.login.then(function(){
      var wayStopPoints = [];
      var returnStopPoints = [];

      $scope.myTrips = [];
      $scope.myFriends = [];
      $scope.selectedTrip;
      $scope.selectedFriend;


      function drawMap(){
        var points = wayStopPoints
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

        //adjusts zoom to the traced area
        var bounds = new google.maps.LatLngBounds();
        trazaVuelo.getPath().forEach(function(bound) {bounds.extend(bound);});
        mapa.fitBounds(bounds);
      }

      $scope.recomendFlightTo = function(selectedFriend){

        $('#modalSpinner').show();

        $http.post('api/v1/recommendations', {
          toUserId: selectedFriend.facebookId,
          flightId: $scope.selectedTrip.id
        })
        .success(function(){
          $('#modalSpinner').hide();
          $('#modalCheck').fadeIn();

          setTimeout(function(){
            $('#modalShare').fadeOut();
            $('#modalShare').modal('hide');
            $('#modalCheck').hide();
            $('#friendSearch_value').val('');
            
            FB.ui({
              method: 'feed',
              caption: 'Recomendación de vuelo',
              link: window.location.origin + '/#/recommendations',
              description: 'Hola! Que te parece viajar a ' + $scope.selectedTrip.destinyDescription,
              picture:'https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Wv_logo_proposal_flying_plane_wo_text.png/120px-Wv_logo_proposal_flying_plane_wo_text.png',
              to: selectedFriend.facebookId
            });
          }, 1000);
        });
      };

      $scope.openShareModal = function(trip){

        $scope.selectedTrip = trip;

        $('#modalShare').modal();

        return false;
      };

      $scope.showMap = function(trip){

        //stopPoints refer to when a plane stops at an airport
        //but its not the final destination
        returnStopPoints = wayStopPoints = [];
        $('#modalMap').modal();

        var wayStopPromises = flightsStopsPromises(trip.wayFlights);
  //      var returnStopPromises = flightsStopsPromises(trip.returnFlights);

        $q.all(wayStopPromises).then(drawMap);
      };


      //////////////////////////////////////////
      ///////////////// HELPERS ////////////////
      //////////////////////////////////////////


      function flightsStopsPromises(flights){

        var ord = 0;

        return flights.reduce(function(stops, flight){
          return stops.concat([
            makeAirportStopPromise(flight.origin, ord++, wayStopPoints),
            makeAirportStopPromise(flight.destiny, ord++, returnStopPoints)
          ]);
        }, []);
      }

      function makeAirportStopPromise(airportCode, ordNum, points){

        return $http.get('api/v1/airports/' + airportCode)
                .success(function recordAirportStop(airport){
                  points.push({ //change here needed to implement the return polyline
                    ord: ordNum,
                    gPoint: new google.maps.LatLng(airport.lat, airport.lon)
                  });
                });
      }

      //////////////////////////////////////////
      ////////////// INIT FUNCTION /////////////
      //////////////////////////////////////////


      (function init(){

        //gets user trips
        $http.get('api/v1/users/me/trips')
          .success(function(trips){
            $scope.myTrips = trips;
          });

        //gets user friends
        $http.get('api/v1/users/me/friends')
          .success(function(friends){
            $scope.myFriends = friends;
          });
      }());
    });
  });
