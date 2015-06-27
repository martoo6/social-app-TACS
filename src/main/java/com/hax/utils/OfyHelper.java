package com.hax.utils;

/**
 * Created by martin on 6/27/15.
 */
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.hax.models.Recommendation;
import com.hax.models.Trip;
import com.hax.models.User;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;

/**
 * OfyHelper, a ServletContextListener, is setup in web.xml to run before a JSP is run.  This is
 * required to let JSP's access Ofy.
 **/
public class OfyHelper implements ServletContextListener {
    public void contextInitialized(ServletContextEvent event) {
        // This will be invoked as part of a warmup request, or the first user
        // request if no warmup request was invoked.
        ObjectifyService.register(Recommendation.class);
        ObjectifyService.register(Trip.class);
        ObjectifyService.register(User.class);
    }

    public void contextDestroyed(ServletContextEvent event) {
        // App Engine does not currently invoke this method.
    }
}
