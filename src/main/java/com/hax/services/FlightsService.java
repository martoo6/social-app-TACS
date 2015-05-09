package com.hax.services;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.utils.FutureHelper;
import com.hax.async.utils.Tuple3;
import com.hax.connectors.DespegarConnectorInterface;
import com.hax.connectors.FlightsRepositoryInterface;
import com.hax.connectors.RecommendationsRepositoryInterface;
import com.hax.connectors.UsersRepositoryInterface;
import com.hax.models.Flight;
import com.hax.models.Recommendation;
import com.hax.models.User;

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
    public RecommendationsRepositoryInterface recommendationRepository;
    @Inject
    public UsersRepositoryInterface userRepository;

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
     * @param flightId
     * @param fromUserId
     * @param toUserId
     * @return
     */
    public ListenableFuture<Recommendation> recommendFlight(Integer flightId,Integer fromUserId, Integer toUserId){

        /**
        Hacemos 3 llamadas asincronicas a los repositorios y esperamos el resultado de las 3.
        El Future resultante es utilizado para insertar la recomendacion.
        Al no ir a los repositorios de manera secuencial se logra la maxima velocidad para obtener los resultados.
         */

        final ListenableFuture<Flight> flightF = flightsRepository.get(flightId);
        final ListenableFuture<User> fromUserF = userRepository.get(fromUserId);
        final ListenableFuture<User> toUserF = userRepository.get(toUserId);


        ListenableFuture<Tuple3<Flight,User,User>> compFuture = FutureHelper.compose(flightF, fromUserF, toUserF);

        return Futures.transform(compFuture, new AsyncFunction<Tuple3<Flight,User,User>, Recommendation>() {
            public ListenableFuture<Recommendation> apply(Tuple3<Flight, User, User> t) throws Exception {
                Recommendation recom = new Recommendation(t.getR1(), t.getR2(), t.getR3());
                return recommendationRepository.insert(recom);
            }
        });
    }
}
