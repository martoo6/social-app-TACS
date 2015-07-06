package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Created by martin on 4/27/15.
 */

public interface DespegarConnectorInterface {
    String getFlightsAsync(String from, String to, String fromDate,String toDate);
    String getAirportsAsync(String autocomplete);
}
