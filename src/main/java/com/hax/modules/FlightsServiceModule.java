package com.hax.modules;

import com.google.inject.AbstractModule;
import com.hax.services.FlightsService;
import com.hax.services.FlightsServiceInterface;

/**
 * Created by martin on 4/27/15.
 */
public class FlightsServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(FlightsServiceInterface.class).to(FlightsService.class);
    }
}
