package connectors;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import com.hax.config.App;
import com.hax.connectors.UsersGAERepository;
import com.hax.connectors.UsersRepositoryInterface;
import com.hax.models.User;
import com.hax.models.User;
import com.hax.utils.OfyHelper;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.GenericTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by martin on 5/3/15.
 */
public class UsersRepositoryGAETest extends GenericTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    private Closeable closeable;

    @Before
    public void setUp() {
        helper.setUp();
        closeable = ObjectifyService.begin();
    }

    @After
    public void tearDown() {
        closeable.close();
        helper.tearDown();
    }

    @Test
    public void insertUserGAE(){
        UsersRepositoryInterface usersRepo = new UsersGAERepository();

        User user = new User();
        user.setFacebookId("1");

        User u = usersRepo.insert(user);
        assertTrue(u.equals(user));
    }

    @Test
    public void getUser(){
        UsersRepositoryInterface dc = new UsersGAERepository();

        User user = new User();
        user.setFacebookId("1");

        dc.insert(user);

        assertTrue(dc.get("1") == user);
    }

    @Test
    public void getUserMissingUser(){
        UsersRepositoryInterface dc = new UsersGAERepository();
        assertNull(dc.get("1"));
    }

    @Test
    public void getAllUser(){
        UsersRepositoryInterface usersRepo = new UsersGAERepository();

        User user = new User();
        user.setFacebookId("1");

        usersRepo.insert(user);

        try {
            List<User> lstUsers = usersRepo.getAll();
            assertTrue(lstUsers.contains(user));
            assertTrue(lstUsers.size()==1);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Override
    protected AbstractBinder setBinder() {
        try {
            App.config = new PropertiesConfiguration("app.config");
            new OfyHelper().contextInitialized(null);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
