'use strict';

/**
 * @ngdoc factory
 * @name frontendApp.geolocatorFactory
 * @description
 * # geolocatorFactory
 * Service in the frontendApp.
 */
angular.module('frontendApp')
  .factory('geolocatorFactory', function () {
      var destinyCoords;
      
      function setDestinyCoords(pos){
        destinyCoords = {
          latitude: pos.A,
          longitude: pos.F
        };
      }
      
      return { 
          
          setDestinyCoords: setDestinyCoords,
                    
          getDestinyCoords: function(){
            return destinyCoords;
          }
      };
  });