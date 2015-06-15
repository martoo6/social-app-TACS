package com.hax.async.executors;


import com.google.appengine.api.ThreadManager;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by martin on 27/04/15.
 */

/**
 * Executor por default, probablemente sea uno solo pero la idea es que existan varios segun el uso que se le de.
 * Preferentemente deberia haber uno para cada modulo del sistema: controllers, services, connectors
 */
public class Default {
    public static ListeningExecutorService ex;

    static{
        try{
            ex = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool(ThreadManager.currentRequestThreadFactory()));
        }
        catch(Exception e){
            //Los tests no pueden inicializar el backgroundThreadFactory(), asique creamos el clasico.
            ex = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(4));
        }
    }
}
