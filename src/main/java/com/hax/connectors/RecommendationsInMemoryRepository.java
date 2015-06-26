package com.hax.connectors;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Recommendation;
import com.hax.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 5/5/15.
 */
public class RecommendationsInMemoryRepository implements RecommendationsRepositoryInterface {
    static List<Recommendation> collection = new ArrayList<Recommendation>();

    public ListenableFuture<Recommendation> insert(final Recommendation recommendation) {
        if (recommendation == null) return Futures.immediateFailedFuture(new RuntimeException("User is null"));
        collection.add(recommendation);
        return Futures.immediateFuture(recommendation);
    }

    //TODO: Este metodo en realidad va a se de hibernate. Lo que hace no tiene mucho sentido.
    public ListenableFuture<Recommendation> update(final Recommendation recommendation) {
        Recommendation updatable=null;
        for(Recommendation tmpRecommendation:collection) if(tmpRecommendation.getId().equals(recommendation.getId())) updatable = tmpRecommendation;
        if(updatable== null) return Futures.immediateFailedFuture(new RuntimeException("User not found"));
        collection.remove(updatable);
        collection.add(recommendation);
        return Futures.immediateFuture(recommendation);
    }

    public ListenableFuture<Recommendation> get(final Long id){
        String ids = "Searching id: "+id+". ";
        for(Recommendation recommendation:collection){
            ids+=recommendation.getId()+" , Checking resolved: "+recommendation.getId().equals(id);
            if (recommendation.getId().equals(id)) return Futures.immediateFuture(recommendation);
        }
        return Futures.immediateFailedFuture(new RuntimeException("Recommendation not found: "+ids));
    }

    public static void tearDown(){
        collection.clear();
    }

    public ListenableFuture<List<Recommendation>> getAll(){
        return Futures.immediateFuture(collection);
    }
}
