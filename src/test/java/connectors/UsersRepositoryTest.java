package connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.connectors.UsersRepository;
import com.hax.models.User;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Test;
import utils.GenericTest;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

/**
 * Created by martin on 5/3/15.
 */
public class UsersRepositoryTest extends GenericTest {

    @Test
    public void insertUser(){
        UsersRepository ur = new UsersRepository();

        User user = new User();

        ListenableFuture<User> lf = ur.insert(user);

        try {
            lf.get();
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }


    @Test
    public void updateUser() throws ExecutionException, InterruptedException {
        User user = new User();
        user.setId(1);
        UsersRepository ur = new UsersRepository();
        ur.insert(user).get();


        ListenableFuture<User> lf = ur.update(user);

        try {
            lf.get();
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void updateUserMissing() throws ExecutionException, InterruptedException {
        User user = new User();
        UsersRepository ur = new UsersRepository();


        ListenableFuture<User> lf = ur.update(user);

        try {
            lf.get();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("User not found"));
        }
    }

    @Test
    public void getUser() throws ExecutionException, InterruptedException {
        User user = new User();
        UsersRepository ur = new UsersRepository();
        ur.insert(user).get();

        ListenableFuture<User> lf = ur.get(0);

        User userRes =lf.get();
        assertTrue(userRes == user);
    }

    @Test
    public void getUserMissing(){
        UsersRepository ur = new UsersRepository();

        ListenableFuture<User> lf = ur.get(1);

        try {
            lf.get();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("User not found"));
        }
    }

    @Test
    public void getAll() throws ExecutionException, InterruptedException {
        User user = new User();
        UsersRepository ur = new UsersRepository();
        ur.insert(user).get();

        ListenableFuture<List<User>> lf = ur.getAll();

        try {
            List<User> userLst = lf.get();
            assertTrue(userLst.contains(user));
            assertTrue(userLst.size()==1);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Override
    protected AbstractBinder setBinder() {
        return null;
    }
}
