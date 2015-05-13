'use strict';

/**
 * @ngdoc function
 * @name frontendApp.controller:FlightpickCtrl
 * @description
 * # FlightpickCtrl
 * Controller of the frontendApp
 */
angular.module('frontendApp')
  .controller('FlightpickCtrl', function ($scope, $http, geolocatorFactory) {
    
    $scope.flightOptions = [];
    
    $scope.flightClick = function(flight){
      $scope.selectedFlight = flight;
      $('#modalCrear').modal();
    };
    
    $scope.guardarVuelo = function(){
      
      //si no es exactamente como lo pide Jersey se rompe
      delete $scope.selectedFlight.$$hashKey;
      
      $.ajax({
          type: "POST",
          url: 'api/v1/flights',
          contentType: 'application/json',
          data: JSON.stringify($scope.selectedFlight)
      })
      .success(function(){
        $('#modalCrear').on('hidden.bs.modal', function(){
          window.location.href = '#/misVuelos';
        }).modal('hide');
      })
    };
    
    function actOnDateChange(){
      $(this).datepicker('hide');
      var returnDate = $('#returnDate').val();
      var leaveDate = $('#leaveDate').val();
      
      if( returnDate && leaveDate ){
        
        $('#spinner').show();
        $('#error').empty();
        
        $http
        .get('api/v1/flights', {
          params: {
            origin: $scope.originAirportCode,
            destiny: $scope.destinyAirportCode,
            arrival: moment(returnDate, 'DD/MM/YYYY').format('YYYY-MM-DD'),
            departure: moment(leaveDate,'DD/MM/YYYY').format('YYYY-MM-DD')
          }
        })
        .success(function(flightOptions){
          $scope.flightOptions = flightOptions.items.map(function(item) {
            var outboundChoice = item.outbound_choices[0];
            var inboundChoice = item.inbound_choices[0];
            var outboundFirstSeg = outboundChoice.segments[0];
            var inboundFirstSeg = inboundChoice.segments[0];
            
            return {
              wayTicket: {
                origin: $scope.originAirportCode,
                destiny: $scope.destinyAirportCode,
                company: outboundFirstSeg.airline,
                flightNum: outboundFirstSeg.flight_id,
                departureTime: moment(outboundFirstSeg.departure_datetime, "YYYY-MM-DDTHH:mm:ss.SSSSZ").format('DD-MM-YYYY HH:mm'),
                duration: outboundChoice.duration + ' hs',
                price:0,
              },
              returnTicket: {
                origin: $scope.destinyAirportCode,
                destiny: $scope.originAirportCode,
                company: inboundFirstSeg.airline,
                flightNum: inboundFirstSeg.flight_id,
                departureTime: moment(inboundFirstSeg.departure_datetime, "YYYY-MM-DDTHH:mm:ss.SSSSZ").format('DD-MM-YYYY HH:mm'),
                duration: inboundChoice.duration + ' hs',
                price:0,
              },
              totalPrice: item.price_detail.total
            };
          });
          
          if(!$scope.flightOptions.length)
            $('#error').append('No se encontraron resultados<br>' + 
                  '<a style="text-decoration:underline" href="#/destinyMapPick">Buscar otro destinto</a>');
        })
        .error(function(error){
          $scope.flightOptions = [];
          $('#error').append('ERROR<br><br><a style="text-decoration:underline" href="#/destinyMapPick">Volver</a> a intentar.<br><br>');
          $('#error').append(error);
        })
        .finally(function(){
          $('#spinner').hide();
        });
      }
    };       
    
    $('#leaveDate').datepicker().on('changeDate', actOnDateChange);
    $('#returnDate').datepicker().on('changeDate', actOnDateChange);
         
    navigator.geolocation.getCurrentPosition(function(pos){
      $http
      .get('api/v1/airports', {
        params: {
          latitude: pos.coords.latitude,
          longitude: pos.coords.longitude
        }
      })
      .success(function(airport){
        $scope.originAirportCode = airport.code;
      });
    });
    
    $http
    .get('api/v1/airports', {
      params: {
        latitude: geolocatorFactory.getDestinyCoords().latitude,
        longitude: geolocatorFactory.getDestinyCoords().longitude
      }
    })
    .success(function(airport){
      $scope.destinyAirportCode = airport.code;
    });

  });
