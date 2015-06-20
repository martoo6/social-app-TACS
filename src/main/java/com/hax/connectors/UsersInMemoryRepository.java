package com.hax.connectors;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 5/5/15.
 */
public class UsersInMemoryRepository implements UsersRepositoryInterface {
    List<User> collection = new ArrayList<User>();

    public ListenableFuture<User> insert(final User user) {
        System.out.println("El usuario es: "+(user == null));
        if (user == null) return Futures.immediateFuture(new User());
        System.out.println("Cree al usuario");
        collection.add(user);
        return Futures.immediateFuture(user);
    }

    //TODO: Este metodo en realidad va a se de hibernate. Lo que hace no tiene mucho sentido.
    public ListenableFuture<User> update(final User user) {
        User updatable=null;
        for(User tmpUser:collection) if(tmpUser.getId().equals(user.getId())) updatable = tmpUser;
        if(updatable== null) return Futures.immediateFailedFuture(new RuntimeException("User not found"));
        collection.remove(updatable);
        collection.add(user);
        return Futures.immediateFuture(user);
    }

    public ListenableFuture<User> get(final String id){
        for(User user:collection){
            if(user.getId().equals(id)) return Futures.immediateFuture(user);
        }
        return Futures.immediateFailedFuture(new RuntimeException("User not found"));
    }

    public ListenableFuture<List<User>> getAll(){
        return Futures.immediateFuture(collection);
    }
}
