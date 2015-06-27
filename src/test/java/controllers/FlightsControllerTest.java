//package controllers;
//
//import com.google.common.util.concurrent.Futures;
//import com.hax.services.TripsServiceInterface;
//import com.hax.services.UsersServiceInterface;
//import org.glassfish.hk2.utilities.binding.AbstractBinder;
//import org.junit.Test;
//import utils.GenericTest;
//
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import static com.google.common.util.concurrent.Futures.immediateFuture;
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
///**
// * Created by martin on 20/04/15.
// */
//
//
//public class FlightsControllerTest extends GenericTest {
//    //Al estar compartido por los test tiene efecto de lado pero el biding se da una sola vez asique no queda otra.
//    //A menos que se encuentre forma de re-iniciar el mock dentro de cada instancia.
//    TripsServiceInterface fs = mock(TripsServiceInterface.class);
//    UsersServiceInterface us = mock(UsersServiceInterface.class);
//
//    @Override
//    protected AbstractBinder setBinder() {
//        return new AbstractBinder() {
//            @Override
//            protected void configure() {
//                bind(fs).to(TripsServiceInterface.class);
//                bind(us).to(UsersServiceInterface.class);
//            }
//        };
//    }
//
//    @Test
//    public void getFilteredFlightsResponse() {
//        when(fs.getFlights("EZE", "MIA", "2015-10-10", "2015-11-10")).thenReturn("1");
//
//
//        final Response response = target("flights")
//                .queryParam("origin", "EZE")
//                .queryParam("destiny", "MIA")
//                .queryParam("departure", "2015-10-10")
//                .queryParam("arrival","2015-11-10")
//                .request(MediaType.APPLICATION_JSON).get();
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//    }
//
//    @Test
//    public void getWrongDateFlights() {
//        when(fs.getFlights("EZE", "MIA", "2015-12-10", "2015-11-10")).thenReturn(Futures.<String>immediateFailedFuture(new RuntimeException("Error 400 !")));
//
//
//        final Response response = target("flights")
//                .queryParam("origin", "EZE")
//                .queryParam("destiny", "MIA")
//                .queryParam("departure", "2015-12-10")
//                .queryParam("arrival", "2015-11-10")
//                .request(MediaType.APPLICATION_JSON).get();
//
//        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
//    }
//}
