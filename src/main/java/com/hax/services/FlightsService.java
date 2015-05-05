package com.hax.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.connectors.DespegarConnectorInterface;
import com.hax.connectors.FlightsRepositoryInterface;
import com.hax.models.Flight;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;

/**
 * Created by martin on 4/26/15.
 */
public class FlightsService implements FlightsServiceInterface{
    @Inject
    public DespegarConnectorInterface despegarConnector;
    public FlightsRepositoryInterface flightsRepository;

    /**
     * Obtiene todos los vuelos con los filtros corresondientes
     * @param from Origen
     * @param to Destino
     * @param fromDate Fecha de partida
     * @param toDate Fecha de retorno
     * @return Lista de vuelos //TODO: Lista de Flights
     */
    public ListenableFuture<String> getFlights(String from, String to, String fromDate,String toDate){
        return despegarConnector.getFlightsAsync(from, to , fromDate, toDate);
    }

    /**
     * Crea un vuelo nuevo
     * @param flight Nuevo vuelo a ingresar en el sistema
     * @return ListenableFuture con el vuelo ingresado
     */
    public ListenableFuture<Flight> createFlight(Flight flight) {
        return flightsRepository.insert(flight);
    }
}
