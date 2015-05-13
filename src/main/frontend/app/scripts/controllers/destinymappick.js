'use strict';

/**
 * @ngdoc function
 * @name frontendApp.controller:DestinymappickCtrl
 * @description
 * # DestinymappickCtrl
 * Controller of the frontendApp
 */
angular.module('frontendApp')
  .controller('DestinymappickCtrl', function ($scope, geolocatorFactory) {
    
    $scope.hasDestiny = false;

    navigator.geolocation.getCurrentPosition(function(pos){
      $scope.$apply(function(){
        (function(originCoords)
        {
            var originLatLng = new google.maps.LatLng(pos.coords.latitude, pos.coords.longitude)

            var configuracionMapa = {   
                                      zoom: 7,
                                      center: originLatLng
                                    };
            //CREA UN NUEVO MAPA
            var mapa = new google.maps.Map($('#mapa')[0], configuracionMapa);
            
            var destinyMarker;
            var originMarker = new google.maps.Marker({ 
              map: mapa, 
              position: originLatLng ,
              title: 'Origen',
            });
            
            google.maps.event.addListener(mapa, 'click', function(e){
              $scope.$apply(function(){
                geolocatorFactory.setDestinyCoords(e.latLng);

                if(destinyMarker){
                  destinyMarker.setMap(null);
                }

                destinyMarker = new google.maps.Marker({ 
                  map: mapa, 
                  position: e.latLng,
                  title: 'Destino',
                });
                $scope.hasDestiny = true;
              });
            });
        }());
      });
    });    
  });
