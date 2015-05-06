package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.executors.Default;
import com.hax.models.Recommendation;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by martin on 5/5/15.
 */
public class RecommendationRepository implements RecommendationRepositoryInterface {
    ArrayList<Recommendation> recommendations = new ArrayList<Recommendation>();

    public ListenableFuture<Recommendation> insert(final Recommendation recommendation) {
        return Default.ex.submit(new Callable<Recommendation>() {
            public Recommendation call() throws Exception {
                //TODO: Esto va a ser con la base de datos, no es concurrente ni a ganchos
                recommendation.setId(recommendations.size());
                recommendations.add(recommendation);
                return recommendation;
            }
        });
    }
}
