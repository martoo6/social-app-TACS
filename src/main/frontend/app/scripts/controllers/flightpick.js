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
    $scope.tripOptions = [];
    
      
    $scope.init = function(){
      $(document).on('blur', '.angucomplete', $scope.actOnChange);
      
      $(document).on('changeDate', '.datepicker', function(){
        $(this).datepicker('hide');
        $scope.actOnChange();
      });
    };

    $scope.flightClick = function(flight){
      $scope.selectedFlight = flight;
      $('#modalCrear').modal();
    };

    $scope.saveTrip = function(){

      //delete extra attributes, otherwise jersey wont save
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

    //acts on input change: fetches itineraries and maps them to backend
    //structure before presenting them to the user to choose one
    $scope.actOnChange = function(){  
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
        .success(function(tripOptions){
          $scope.tripOptions = tripOptions.items.map(function(item) {
            
            //we are only considering the first choices at the moment
            var outboundChoice = item.outbound_choices[0];
            var inboundChoice = item.inbound_choices[0];
            
            return {
              origin: $scope.originLocation.originalObject.code,
              destiny: $scope.destinyLocation.originalObject.code,
              price: item.price_detail.total,
              
              wayDuration: outboundChoice.duration + ' hs',
              returnDuration: inboundChoice.duration + ' hs',
              
              wayFlights: choiceToSegments(outboundChoice),
              returnFlights: choiceToSegments(inboundChoice),
            };
          });

          if(!$scope.tripOptions.length)
            $('#error').append('No se encontraron resultados<br>');
        })
        .error(function(error){
          $scope.tripOptions = [];
          $('#error').append('ERROR<br><br>');
          $('#error').append(error);
        })
        .finally(function(){
          $('#spinner').hide();
        });
      }
    };
    
    //////////////////////// HELPERS //////////////////////

    //maps choice(inboundChoice/outboundChoice)
    //segments into flights consumable by the backend
    function choiceToSegments(choice){
      return choice.segments.map(function(segment){
        return {
          origin: segment.from,
          destiny: segment.to,
          airline: segment.airline,
          flightNum: segment.flight_id,
          departureTime: moment(segment.departure_datetime, "YYYY-MM-DDTHH:mm:ss.SSSSZ").format('DD-MM-YYYY HH:mm'),
          duration: segment.duration + ' hs'
        };
      });
    }
});
    
