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
    static List<User> collection = new ArrayList<User>();

    public ListenableFuture<User> insert(final User user) {
        if (user == null) return Futures.immediateFailedFuture(new RuntimeException("User is null"));
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
        String ids = "Searching id: "+id+". ";
        for(User user:collection){
            ids+=user.getId()+" , Checking resolved: "+user.getId().equals(id);
            if (user.getId().equals(id)) return Futures.immediateFuture(user);
        }
        return Futures.immediateFailedFuture(new RuntimeException("User not found: "+ids));
    }

    public static void tearDown(){
        collection.clear();
    }

    public ListenableFuture<List<User>> getAll(){
        return Futures.immediateFuture(collection);
    }
}
