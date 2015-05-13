package com.hax.services;

import com.google.common.base.Function;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.utils.FutureHelper;
import com.hax.async.utils.Tuple2;
import com.hax.async.utils.Tuple3;
import com.hax.connectors.DespegarConnectorInterface;
import com.hax.connectors.FlightsRepositoryInterface;
import com.hax.connectors.UsersRepositoryInterface;
import com.hax.models.Flight;
import com.hax.models.Recommendation;
import com.hax.models.User;
import java.util.ArrayList;
import java.util.List;

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
    public ListenableFuture<Flight> createFlight(Flight flight, Integer userId) {

        ListenableFuture<Flight> flightF = flightsRepository.insert(flight);
        ListenableFuture<User> userF = userRepository.get(userId);

        ListenableFuture<Tuple2<Flight,User>> comp = FutureHelper.compose(flightF, userF);

        return Futures.transform(comp, new Function<Tuple2<Flight, User>, Flight>() {
            public Flight apply(Tuple2<Flight, User> tuple) {
                Flight flight = tuple.getR1();
                User user = tuple.getR2();
                user.getFlights().add(flight);
                userRepository.update(user);
                return flight;
            }
        });
    }
    
    /**
     * trae todos los vuelos
     * 
     * @return ListenableFuture con todos los vuelos
     */
    public ListenableFuture<List<Flight>> getAllSavedFlights() {
//        System.out.println();
//        System.out.println(flightsRepository);
        return flightsRepository.getAll();
    }
}
