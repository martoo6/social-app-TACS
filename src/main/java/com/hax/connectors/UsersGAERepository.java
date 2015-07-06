package com.hax.connectors;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.hax.async.executors.Default;
import com.hax.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by martin on 5/5/15.
 */
public class UsersGAERepository implements UsersRepositoryInterface {
    public User insert(final User user) {
        if (user == null) throw new RuntimeException("User is null");
        ObjectifyService.ofy().save().entity(user).now();
        return user;
    }

    public User update(final User user) {
        if (user == null) throw new RuntimeException("User is null");
        ObjectifyService.ofy().save().entity(user).now();
        return user;
    }

    public User get(final String id){
        return ObjectifyService.ofy().load().type(User.class).id(id).now();
    }


    public List<User> getAll(){
        return ObjectifyService.ofy().load().type(User.class).list();
    }
}
