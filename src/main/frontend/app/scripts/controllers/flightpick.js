'use strict';

/**
 * @ngdoc function
 * @name frontendApp.controller:FlightpickCtrl
 * @description
 * # FlightpickCtrl
 * Controller of the frontendApp
 */
angular.module('frontendApp')
  .controller('FlightpickCtrl', function ($scope, $http, $rootScope) {

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

    $scope.tripClick = function(trip){
      $scope.selectedTrip = trip;
      console.log(trip)
      console.log($scope.destinyLocation)
      console.log($scope.leaveDate)
      $('#modalCrear').modal();
    };

    $scope.saveTrip = function(){

      //delete extra attributes, otherwise jersey wont save
      delete $scope.selectedTrip.$$hashKey;


      $.ajax({
          type: "POST",
          url: 'api/v1/trips',
          headers:{'token': $rootScope.fbStatus.authResponse.accessToken},
          contentType: 'application/json',
          data: JSON.stringify($scope.selectedTrip)
      })
      .success(function(){
        $('#modalCrear').on('hidden.bs.modal', function(){

          if($scope.destinyLocation && $scope.leaveDate){
            $.get('api/v1/airports/'+ $scope.destinyLocation.originalObject.code, function(airport){

              //more on api parameters at the end of the file
              FB.api('/me/feed', 'post', {
                message: 'Me voy a ' + airport.city + ' el ' + $scope.leaveDate + ' !'
              }, function(response) {
                alert(response);
                if (!response || response.error) {
                  //Error occured - handle it
                  alert("TODO MAL");
                } else {
                  alert("TODO BIEN");
                  window.location.href = '#/misVuelos';
                }
              });
            });
          }
        }).modal('hide');
      });
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

//
//  Reminder for facebook parameters to post to wall
//
//  fbParams = {
//    message: to display in post body
//    name: name of the link showing in post
//    link: link to content (url)
//    picture: picture that shows for link container
//    caption: little grey text at link container bottom
//  };
//  FB.api('/me/feed', 'post', fbParams, function(response) {
//    if (!response || response.error) {
//      //Error occured - handle it
//    } else {
//      //Success!
//    }
//  });
