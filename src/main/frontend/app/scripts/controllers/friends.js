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
      
      $(document).on('click', '.collapseOpener', function(e){
        
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
          
          //by adding the data-target and removing the class that's delegated for the event
          //we ensure to use the already loaded trips and use the regular collapser for displaying them
          $(collapser).attr('data-target', friendSelector).removeClass('collapseOpener');
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
