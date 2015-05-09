package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.executors.Default;
import com.hax.models.RepositoryElement;
import com.hax.models.User;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by martin on 5/5/15.
 */
public class UserRepositoryInterface {
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

    public ListenableFuture<User> get(final Integer id){
        return Default.ex.submit(new Callable<User>() {
            public User call() throws Exception {
                //TODO: Esto va a ser con la base de datos, no es concurrente ni a ganchos
                for(User user:collection){
                    if(user.getId()==id) return user;
                }
                return new User();
            }
        });
    }

    public ListenableFuture<ArrayList<User>> getAll(Integer id){
        return Default.ex.submit(new Callable<ArrayList<User>>() {
            public ArrayList<User> call() throws Exception {
                return collection;
            }
        });
    }
}
