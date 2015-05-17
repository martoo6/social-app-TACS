package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import org.jvnet.hk2.annotations.Contract;

/**
 * Created by martin on 4/27/15.
 */

public interface FlightsConnectorInterface {
    ListenableFuture<String> getFlightsAsync(String from, String to, String fromDate,String toDate);
}
