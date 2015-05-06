package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Recommendation;

/**
 * Created by martin on 5/5/15.
 */
public interface RecommendationRepositoryInterface {
    ListenableFuture<Recommendation> insert(Recommendation recommendation);
}
