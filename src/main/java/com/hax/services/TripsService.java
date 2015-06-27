package com.hax.services;

import com.google.common.base.Function;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.utils.FutureHelper;
import com.hax.async.utils.Tuple2;
import com.hax.connectors.*;
import com.hax.models.AirportResponse;
import com.hax.models.Trip;
import com.hax.models.User;
import com.hax.models.fb.FbVerify;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by martin on 4/26/15.
 */
public class TripsService implements TripsServiceInterface {
    @Inject
    public DespegarConnectorInterface despegarConnector;
    @Inject
    public TripsRepositoryInterface tripsRepository;
    @Inject
    public UsersRepositoryInterface userRepository;
    @Inject
    public FacebookConnectorInterface fbConnector;
    @Inject
    public AirportsConnectorInterface airportsConnector;

    /**
     * Obtiene todos los vuelos con los filtros corresondientes
     * @param from Origen
     * @param to Destino
     * @param fromDate Fecha de partida
     * @param toDate Fecha de retorno
     * @return Lista de vuelos //TODO: Lista de Flights
     */
    public String getFlights(String from, String to, String fromDate,String toDate){
        return despegarConnector.getFlightsAsync(from, to , fromDate, toDate);
    }

    /**
     * Crea un vuelo nuevo
     * @param trip Nuevo vuelo a ingresar en el sistema
     * @return ListenableFuture con el vuelo ingresado
     */
    public Trip createTrip(final Trip trip, final String token) {
        FbVerify fbVerify = fbConnector.verifyAccessToken(token);
        User user = userRepository.get(fbVerify.getId());
        if(user!=null) {
            tripsRepository.insert(trip);
            user.getTrips().add(trip.getId());
            userRepository.update(user);
            AirportResponse destino = airportsConnector.getAirportAsync(trip.getDestiny());
            fbConnector.publishToWall(token, "Me voy a " + destino.getCity() + "!");
            return trip;
        }
        return null;
    }

    @Override
    public Trip getTrip(Long tripId) {
        return tripsRepository.get(tripId);
    }

    /**
     * trae todos los vuelos
     *
     * @return ListenableFuture con todos los vuelos
     */
    public List<Trip> getAllSavedTrips() {
//        System.out.println();
//        System.out.println(tripsRespository);
        return tripsRepository.getAll();
    }
}
