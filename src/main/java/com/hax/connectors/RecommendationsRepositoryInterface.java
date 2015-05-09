package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Recommendation;

import java.util.ArrayList;

/**
 * Created by martin on 5/5/15.
 */
public interface RecommendationsRepositoryInterface {
    ListenableFuture<Recommendation> insert(Recommendation recommendation);
    ListenableFuture<Recommendation> get(final Integer id);
    ListenableFuture<ArrayList<Recommendation>> getAll();
}
