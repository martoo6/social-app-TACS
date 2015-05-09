package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.executors.Default;
import com.hax.models.User;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by martin on 5/5/15.
 */
public class UsersRepository implements UsersRepositoryInterface {
    ArrayList<User> collection = new ArrayList<User>();

    public ListenableFuture<User> insert(final User user) {
        return Default.ex.submit(new Callable<User>() {
            public User call() throws Exception {
                //TODO: Esto va a ser con la base de datos, no es concurrente ni a ganchos
                if (user == null) return new User();
                user.setId(collection.size());
                collection.add(user);
                return user;
            }
        });
    }

    //TODO: Este metodo en realidad va a se de hibernate. Lo que hace no tiene mucho sentido.
    public ListenableFuture<User> update(final User user) {
        return Default.ex.submit(new Callable<User>() {
            public User call() throws Exception {
                User updatable=null;
                for(User tmpUser:collection) if(tmpUser.getId()==user.getId()) updatable = tmpUser;
                if(updatable== null) throw new RuntimeException("User does not exist");
                collection.remove(updatable);
                collection.add(user);
                return user;
            }
        });
    }

    public ListenableFuture<User> get(final Integer id){
        return Default.ex.submit(new Callable<User>() {
            public User call() throws Exception {
                for(User user:collection){
                    if(user.getId()==id) return user;
                }
                throw new RuntimeException("User Not Found");
            }
        });
    }

    public ListenableFuture<ArrayList<User>> getAll(){
        return Default.ex.submit(new Callable<ArrayList<User>>() {
            public ArrayList<User> call() throws Exception {
                return collection;
            }
        });
    }
}
