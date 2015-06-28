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

    public User insert(final User user) {
        if (user == null) throw(new RuntimeException("User is null"));
        collection.add(user);
        return (user);
    }

    //TODO: Este metodo en realidad va a se de hibernate. Lo que hace no tiene mucho sentido.
    public User update(final User user) {
        User updatable=null;
        for(User tmpUser:collection) if(tmpUser.getFacebookId().equals(user.getFacebookId())) updatable = tmpUser;
        if(updatable== null) throw(new RuntimeException("User not found"));
        collection.remove(updatable);
        collection.add(user);
        return user;
    }

    public User get(final String id){
        for(User user:collection){
            if (user.getFacebookId().equals(id)) return user;
        }
        return null;
    }

    public static void tearDown(){
        collection.clear();
    }

    public List<User> getAll(){
        return (collection);
    }
}
