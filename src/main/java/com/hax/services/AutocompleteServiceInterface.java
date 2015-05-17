package com.hax.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Flight;

import java.util.List;

/**
 * Created by martin on 4/27/15.
 */

public interface AutocompleteServiceInterface {
    ListenableFuture<String> getAirports(String autocomplete);
}
