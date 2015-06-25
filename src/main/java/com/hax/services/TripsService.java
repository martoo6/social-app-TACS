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
    public ListenableFuture<String> getFlights(String from, String to, String fromDate,String toDate){
        return despegarConnector.getFlightsAsync(from, to , fromDate, toDate);
    }

    /**
     * Crea un vuelo nuevo
     * @param trip Nuevo vuelo a ingresar en el sistema
     * @return ListenableFuture con el vuelo ingresado
     */
    public ListenableFuture<Trip> createTrip(final Trip trip, final String token) {

        ListenableFuture<FbVerify> fbVerify = fbConnector.verifyAccessToken(token);

        return Futures.transform(fbVerify, new AsyncFunction<FbVerify, Trip>() {
            @Override
            public ListenableFuture<Trip> apply(FbVerify fbVerify) throws Exception {
                return Futures.transform(userRepository.get(fbVerify.getId()), new Function<User, Trip>() {
                    @Override
                    public Trip apply(User user) {
                        //No me preocupo por si falla alguna en particular, igual no tengo transacciones
                        tripsRepository.insert(trip);
                        user.getTrips().add(trip.getId());
                        userRepository.update(user);
                        Futures.transform(airportsConnector.getAirportAsync(trip.getDestiny()), new AsyncFunction<AirportResponse, String>() {
                            @Override
                            public ListenableFuture<String> apply(AirportResponse destino) throws Exception {
                                return fbConnector.publishToWall(token, "Me voy a " + destino.getCity() + "!");
                            }
                        });
                        return trip;
                    }
                });
            }
        });
    }

    @Override
    public ListenableFuture<Trip> getTrip(Long tripId) {
        return tripsRepository.get(tripId);
    }

    /**
     * trae todos los vuelos
     *
     * @return ListenableFuture con todos los vuelos
     */
    public ListenableFuture<List<Trip>> getAllSavedTrips() {
//        System.out.println();
//        System.out.println(tripsRespository);
        return tripsRepository.getAll();
    }
}
