'use strict';

/**
 * @ngdoc function
 * @name frontendApp.controller:FlightpickCtrl
 * @description
 * # FlightpickCtrl
 * Controller of the frontendApp
 */
angular.module('frontendApp')
  .controller('FlightpickCtrl', function ($scope, $http) {

    $scope.originLocation;
    $scope.destinyLocation;
    $scope.leaveDate;
    $scope.returnDate;
    $scope.flightOptions = [];
        
    window.scopeDebug = function(){
      return {
        a:$scope.originLocation,
        b:$scope.destinyLocation,
        c:$scope.leaveDate,
        d:$scope.returnDate,
        e: $scope.flightOptions,
      };
    };
      
    $scope.init = function(){
      
      $(document).on('blur', '.angucomplete', actOnChange);
      
      //evento changeDate para cerrar el datepicker
      $(document).on('changeDate', '.datepicker', function(){
        $(this).datepicker('hide');
        actOnChange();
      });
    };

    $scope.flightClick = function(flight){
      $scope.selectedFlight = flight;
      $('#modalCrear').modal();
    };

    $scope.guardarVuelo = function(){

      //si no es exactamente como lo pide Jersey se rompe
      delete $scope.selectedFlight.$$hashKey;

      $.ajax({
          type: "POST",
          url: 'api/v1/trips',
          headers:{'token':0},
          contentType: 'application/json',
          data: JSON.stringify($scope.selectedFlight)
      })
      .success(function(){
        $('#modalCrear').on('hidden.bs.modal', function(){
          window.location.href = '#/misVuelos';
        }).modal('hide');
      })
    };

    function actOnChange(){  
      if($scope.returnDate && $scope.leaveDate && $scope.originLocation && $scope.destinyLocation){
        $('#spinner').show();
        $('#error').empty();

        $http
        .get('api/v1/flights', {
          params: {
            origin: $scope.originLocation.originalObject.code,
            destiny: $scope.destinyLocation.originalObject.code,
            arrival: moment($scope.returnDate, 'DD/MM/YYYY').format('YYYY-MM-DD'),
            departure: moment($scope.leaveDate,'DD/MM/YYYY').format('YYYY-MM-DD')
          }
        })
        .success(function(flightOptions){
          console.log(flightOptions);
          $scope.flightOptions = flightOptions.items.map(function(item) {
            var outboundChoice = item.outbound_choices[0];
            var inboundChoice = item.inbound_choices[0];
            var outboundFirstSeg = outboundChoice.segments[0];
            var inboundFirstSeg = inboundChoice.segments[0];

            return {
              wayFlights: {
                origin: $scope.originLocation.originalObject.code,
                destiny: $scope.destinyLocation.originalObject.code,
                airline: outboundFirstSeg.airline,
                flightNum: outboundFirstSeg.flight_id,
                departureTime: moment(outboundFirstSeg.departure_datetime, "YYYY-MM-DDTHH:mm:ss.SSSSZ").format('DD-MM-YYYY HH:mm'),
                duration: outboundChoice.duration + ' hs'
              },
              returnFlights: {
                origin: $scope.destinyLocation.originalObject.code,
                destiny: $scope.originLocation.originalObject.code,
                airline: inboundFirstSeg.airline,
                flightNum: inboundFirstSeg.flight_id,
                departureTime: moment(inboundFirstSeg.departure_datetime, "YYYY-MM-DDTHH:mm:ss.SSSSZ").format('DD-MM-YYYY HH:mm'),
                duration: inboundChoice.duration + ' hs'
              },
              price: item.price_detail.total
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
  });
