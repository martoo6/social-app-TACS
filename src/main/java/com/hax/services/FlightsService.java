package com.hax.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.executors.Default;
import com.hax.connectors.DespegarConnectorInterface;
import com.hax.connectors.FlightsRepositoryInterface;
import com.hax.connectors.RecommendationRepositoryInterface;
import com.hax.models.Flight;
import com.hax.models.Recommendation;
import com.hax.models.User;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import java.util.concurrent.Callable;

/**
 * Created by martin on 4/26/15.
 */
public class FlightsService implements FlightsServiceInterface{
    @Inject
    public DespegarConnectorInterface despegarConnector;
    @Inject
    public FlightsRepositoryInterface flightsRepository;
    @Inject
    public RecommendationRepositoryInterface recommendationRepository;

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
        System.out.println(flight);
        System.out.println(flightsRepository);
        return flightsRepository.insert(flight);
    }


    /**
     *
     * @param flight
     * @param fromUser
     * @param toUser
     * @return
     */
    //TODO: Hay q ver que datos se van a apsar con el loggeo real de la persona
    public ListenableFuture<Recommendation> recommendFlight(Integer flightId,Integer fromUserId, Integer toUserId){
        //TODO: llamar a los repostorios para poder hacer la insersion.
        final ListenableFuture<Flight> flight = flightsRepository.get(flightId);
        //TODO: Future que espera el return de los futures anteriores.
        return recommendationRepository.insert(new Recommendation(new Flight(), new User(), new User()));
    }
}
