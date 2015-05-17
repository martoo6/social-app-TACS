package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;

/**
 * Created by martin on 4/27/15.
 */

public interface AutocompleteConnectorInterface {
    ListenableFuture<String> getAirportsAsync(String autocomplete);
}
