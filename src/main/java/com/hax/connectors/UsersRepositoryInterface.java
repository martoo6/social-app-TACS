package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.User;

import java.util.List;

/**
 * Created by martin on 5/5/15.
 */
public interface UsersRepositoryInterface {
    User insert(final User user);
    User update(final User user);
    User get(final String id);
    List<User> getAll();
}
