package connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.connectors.RecommendationsRepository;
import com.hax.connectors.UsersRepository;
import com.hax.models.Recommendation;
import com.hax.models.User;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Test;
import utils.GenericTest;

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

    @Override
    protected AbstractBinder setBinder() {
        return null;
    }
}
