function obtenerLatLng(resultados, status)
{
	if(status == google.maps.GeocoderStatus.OK)
	{
		//AJUSTA EL ZOOM DEL MAPA DE ACUERDO AL RESULTADO
		mapa.fitBounds(resultados[0].geometry.viewport);
		
		//AGREGA UN MARCADOR
		var marcador = new google.maps.Marker({ map: mapa,
												position: resultados[0].geometry.location });
	}
	
	else
	{
		alert("Geocoding no tuvo éxito debido a: " + status);
	}

};

var decodificadorGeografico = new google.maps.Geocoder();
decodificadorGeografico.geocode({'address': 'buenos aires'}, obtenerLatLng);