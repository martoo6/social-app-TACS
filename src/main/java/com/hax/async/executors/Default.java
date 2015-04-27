package com.hax.async.executors;


import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Executors;

/**
 * Created by martin on 27/04/15.
 */

public class Default {
    public static ListeningExecutorService ex = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
}
