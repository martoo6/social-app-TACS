package com.hax.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.connectors.DespegarConnectorInterface;
import com.hax.connectors.FlightsRepositoryInterface;
import com.hax.connectors.RecommendationRepositoryInterface;
import com.hax.models.Flight;
import com.hax.models.Recommendation;
import com.hax.models.User;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;

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
    public ListenableFuture<Recommendation> recommendFlight(Flight flight,User fromUser, User toUser){
        return recommendationRepository.insert(new Recommendation(flight, fromUser, toUser));
    }
}
