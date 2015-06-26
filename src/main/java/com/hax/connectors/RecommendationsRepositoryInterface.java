package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Recommendation;
import com.hax.models.User;

import java.util.List;

/**
 * Created by martin on 5/5/15.
 */
public interface RecommendationsRepositoryInterface {
    ListenableFuture<Recommendation> insert(final Recommendation recommendation);
    ListenableFuture<Recommendation> update(final Recommendation recommendation);
    ListenableFuture<Recommendation> get(final Long id);
    ListenableFuture<List<Recommendation>> getAll();
}
