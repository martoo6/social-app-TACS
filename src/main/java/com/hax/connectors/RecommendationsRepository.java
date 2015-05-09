package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.executors.Default;
import com.hax.models.Recommendation;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by martin on 5/5/15.
 */
public class RecommendationsRepository implements RecommendationsRepositoryInterface {
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

    public ListenableFuture<Recommendation> get(final Integer id){
        return Default.ex.submit(new Callable<Recommendation>() {
            public Recommendation call() throws Exception {
                for(Recommendation recommendation :recommendations){
                    if(recommendation.getId()==id) return recommendation;
                }
                throw new RuntimeException("Recommendation Not Found");
            }
        });
    }

    public ListenableFuture<ArrayList<Recommendation>> getAll(){
        return Default.ex.submit(new Callable<ArrayList<Recommendation>>() {
            public ArrayList<Recommendation> call() throws Exception {
                return recommendations;
            }
        });
    }
}
