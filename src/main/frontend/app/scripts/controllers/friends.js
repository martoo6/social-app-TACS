'use strict';

/**
 * @ngdoc function
 * @name frontendApp.controller:FriendsCtrl
 * @description
 * # FriendsCtrl
 * Controller of the frontendApp
 */
angular.module('frontendApp')
  .controller('FriendsCtrl', function ($scope, $http, $q) {
    
//    $scope.myFriends = [
//      {"username":"Cacarlos Pispispispis", "id":"107826402891555", "trips":[1,2]},
//      {"username":"Julian Selser", "id":"384563141742033", "trips":[0]},
//      {"username":"Jhonny MelasLavo", "id":"225353", "trips":[]},
//      {"username":"Debora Meltrozo", "id":"222232", "trips":[]},
//      {"username":"Carlos ElQueTeCoje", "id":"252352", "trips":[]},
//      {"username":"Nombre Creativo", "id":"222343", "trips":[]},
//    ];
    
    //////////////////////////////////////////
    ////////////// INIT FUNCTION /////////////
    //////////////////////////////////////////

    (function init(){


      //init collapsable accordion events
      $(document).on('click', '.collapseOpener.open', function(e){
        $(this).removeClass('open').addClass('closed');
        $($(this).data('open')).collapse('toggle');
      });
      
      $(document).on('click', '.collapseOpener.closed', function(e){
        
        var collapser = this;
        var $spinner = $(this).find('.fa-spinner');
        var friendSelector = $(this).data('open');
        var friend = findFriendById(friendSelector.replace('#', ''));

        friend.tripsBrought = [];
        $spinner.show();
        
        //builds promise array to be deferred once all have completed
        var friendTripPromises = friend.trips.reduce(function(promises, tripId){
          return promises.concat([
             $http.get('api/v1/trips/' + tripId)
              .success(function(trip){
                friend.tripsBrought.push(trip); 
            })
          ]);
        }, []);
       
        //executes promises requests and acts once all finished
        $q.all(friendTripPromises).then(function(){
          
          console.log(friend);
          
          $spinner.hide();
          $(friendSelector).collapse('toggle');
          $(collapser).removeClass('closed').addClass('open');
        });
      });

      $http.get('api/v1/users/me/friends')
        .success(function(friends){
          $scope.myFriends = friends;
        });
    }());
    
    
    ////////////////////////////////
    /////////// Helpers ////////////
    ////////////////////////////////

    function findFriendById(id){
      return $.grep($scope.myFriends, function(friend){ 
        return friend.id == id; 
      })[0];
    }
  });
