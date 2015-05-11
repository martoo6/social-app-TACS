<div class="jumbotron" style="height:100%">
    
  <div id="mapa"></div>
  <script>
      
    //CREA EL MAPA CON LA CONFIGURACION INICIAL
    function inicializarMapa()
    {
        var vueloLatLng = [];
        
        var configuracionMapa = {   zoom: 5,
                                    center: new google.maps.LatLng(-34.6037232, -58.3815931)
                                };

        //CREA UN NUEVO MAPA
        var mapa = new google.maps.Map(document.getElementById('mapa'), configuracionMapa);
        
        //SE INSERTAN LAS LATIDUDES Y LONGITUDES DE BUENOS AIRES, MADRID Y ROMA
        vueloLatLng.push(new google.maps.LatLng(-34.6037232, -58.38159310000003));
        vueloLatLng.push(new google.maps.LatLng(40.4167754, -3.7037901999999576));
        vueloLatLng.push(new google.maps.LatLng(41.9027835, 12.496365500000024));
        
        var trazaVuelo = new google.maps.Polyline({ path: vueloLatLng,
                                                    geodesic: true,
                                                    strokeColor: '#FF0000',
                                                    strokeOpacity: 1.0,
                                                    strokeWeight: 2,
                                                    clickable: false
                                                });
        trazaVuelo.setMap(mapa);
        
        //AJUSTA EL ZOOM PARA QUE SE VEA EL RECORRIDO
        var bounds = new google.maps.LatLngBounds();
        trazaVuelo.getPath().forEach(function(bound) {bounds.extend(bound);})
        mapa.fitBounds(bounds);
    };
    
    //MUESTRA EL MAPA AL CARGAR LA PAGINA
    inicializarMapa();
  </script>
</div>