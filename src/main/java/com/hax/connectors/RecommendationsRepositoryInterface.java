package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Recommendation;
import com.hax.models.User;

import java.util.List;

/**
 * Created by martin on 5/5/15.
 */
public interface RecommendationsRepositoryInterface {
    Recommendation insert(final Recommendation recommendation);
    Recommendation update(final Recommendation recommendation);
    Recommendation get(final Long id);
    List<Recommendation> getAll();
}
