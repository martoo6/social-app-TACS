package connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.connectors.UsersInMemoryRepository;
import com.hax.connectors.UsersRepositoryInterface;
import com.hax.models.User;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.After;
import org.junit.Test;
import utils.GenericTest;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by martin on 5/3/15.
 */
public class UsersInMemoryRepositoryTest extends GenericTest {

    @Test
    public void insertUser(){
        UsersRepositoryInterface ur = new UsersInMemoryRepository();

        User user = new User();

        try {
            ur.insert(user);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }


    @Test
    public void updateUser() throws ExecutionException, InterruptedException {
        User user = new User();
        user.setFacebookId("1");
        UsersRepositoryInterface ur = new UsersInMemoryRepository();
        ur.insert(user);

        try {
            ur.update(user);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void updateUserMissing() throws ExecutionException, InterruptedException {
        User user = new User();
        user.setFacebookId("1234");
        UsersRepositoryInterface ur = new UsersInMemoryRepository();

        try {
            ur.update(user);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("User not found"));
        }
    }

    @Test
    public void getUser() throws ExecutionException, InterruptedException {
        User user = new User();
        user.setFacebookId("1");
        UsersRepositoryInterface ur = new UsersInMemoryRepository();
        ur.insert(user);

        assertTrue(ur.get("1") == user);
    }

    @Test
    public void getUserMissing(){
        UsersRepositoryInterface ur = new UsersInMemoryRepository();

        assertNull(ur.get("1"));
    }

    @Test
    public void getAll() throws ExecutionException, InterruptedException {
        User user = new User();
        UsersRepositoryInterface ur = new UsersInMemoryRepository();
        ur.insert(user);

        try {
            List<User> userLst = ur.getAll();
            assertTrue(userLst.contains(user));
            assertTrue(userLst.size()==1);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @After
    public void tearDown() {
        UsersInMemoryRepository.tearDown();
    }

    @Override
    protected AbstractBinder setBinder() {
        return null;
    }
}
