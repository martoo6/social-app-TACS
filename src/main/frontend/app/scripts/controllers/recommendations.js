'use strict';

/**
 * @ngdoc function
 * @name frontendApp.controller:RecommendationsCtrl
 * @description
 * # RecommendationsCtrl
 * Controller of the frontendApp
 */
angular.module('frontendApp')
  .controller('RecommendationsCtrl', function ($scope, $http, $q, fbLoginService) {

    fbLoginService.login.then(function(){
      $scope.myRecommendations = [];
    
      $scope.reject = affectRecommendation.bind(null, 'delete');
      
      $scope.accept = function(recommendation){
        affectRecommendation('put', recommendation).then(function(){
          
          FB.ui({
                method: 'feed',
                caption: 'Recomendación de vuelo',
                link: window.location.origin + '/#/recommendations',
                description: 'Hola! Gracias por recomendarme el vuelo a ' + recommendation.trip.destinyDescription + '. Lo agregue entre mis vuelos! :D',
                picture:'https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Wv_logo_proposal_flying_plane_wo_text.png/120px-Wv_logo_proposal_flying_plane_wo_text.png',
                to: recommendation.fromUserFacebookId
            });
        });  
    };


      //////////////////////////////////////////
      ////////////// INIT FUNCTION /////////////
      //////////////////////////////////////////

      (function init(){

        //gets user recommendations
        $http.get('api/v1/recommendations')
          .success(function(recommendations){

            //filter pending recommendations
            var pendingRecommendations = recommendations.filter(function(rec){
              return rec.state === "PENDING";
            });

            pendingRecommendations.forEach(loadRecommendation);
          });
      }());

      //////////////////////////////////////////
      ////////////// HELPERS ///////////////////
      //////////////////////////////////////////


      //the recommendation brought from the back contains only id's
      //we have to load and fill a whole one by querying each entity id...
      function loadRecommendation(recommendation){

        var wholeRecommendation = { id: recommendation.id };

        var recommendationPromises = [

          $http.get('api/v1/users/' + recommendation.fromUserId).success(function(fromUser){
            wholeRecommendation.username = fromUser.username;
            wholeRecommendation.fromUserFacebookId = fromUser.facebookId;
          }),

          $http.get('api/v1/trips/' + recommendation.trip).success(function(trip){
            wholeRecommendation.trip = trip;
          })
        ];

        //when all requests finished, add the now whole 
        //recommendation to the $scope array to be displayed
        $q.all(recommendationPromises).then(function(){

          //myRecommendations will be shown as they are pushed, this is desired behaviour
          //an otherwise long recommendation list may wait too long since all are loaded
          $scope.myRecommendations.push(wholeRecommendation);
        });
      }


      //removes passed recommendation from displayed list
      function unlistRecommendation(recommendation){
        //filters list by passed recommendation id
        $scope.myRecommendations = $scope.myRecommendations.filter(function(recom){
          return recom.id != recommendation.id;
        });  
      }


      //partially applicable function for the desired http verb
      //shows spínner, perform verb request and unlist recommendation
      function affectRecommendation(verb, recommendation){
        $('#recommendationSpinner').show();
        
        return $http[verb]('api/v1/recommendations/' + recommendation.id).success(function(){
          unlistRecommendation(recommendation);
        });
      }
    });
});
