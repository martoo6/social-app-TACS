package com.hax.services;

import com.google.common.base.Function;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.utils.FutureHelper;
import com.hax.async.utils.Tuple2;
import com.hax.connectors.DespegarConnectorInterface;
import com.hax.connectors.FacebookConnectorInterface;
import com.hax.connectors.TripsRepositoryInterface;
import com.hax.connectors.UsersRepositoryInterface;
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
    public ListenableFuture<Trip> createTrip(final Trip trip, String token) {

        ListenableFuture<FbVerify> fbVerify = fbConnector.verifyAccessToken(token);

        ListenableFuture<Tuple2<Trip,User>> comp = Futures.transform(fbVerify, new AsyncFunction<FbVerify, Tuple2<Trip,User>>() {
            @Override
            public ListenableFuture<Tuple2<Trip,User>> apply(FbVerify fbVerify) throws Exception {
                ListenableFuture<Trip> flightF = tripsRepository.insert(trip);
                ListenableFuture<User> userF = userRepository.get(fbVerify.getId());
                return FutureHelper.compose(flightF, userF);
            }
        });

        return Futures.transform(comp, new Function<Tuple2<Trip, User>, Trip>() {
            public Trip apply(Tuple2<Trip, User> tuple) {
                Trip trip = tuple.getR1();
                User user = tuple.getR2();
                user.getTrips().add(trip.getId());
                userRepository.update(user);
                return trip;
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
