package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.executors.Default;
import com.hax.models.User;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by martin on 5/5/15.
 */
public interface UserRepositoryInterface {
    ListenableFuture<User> insert(final User user);
    ListenableFuture<User> get(final Integer id);
    ListenableFuture<ArrayList<User>> getAll(Integer id);
}
